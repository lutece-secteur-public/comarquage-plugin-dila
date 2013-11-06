/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.enums.ResourceTypeEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaValidationService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.web.constants.Messages;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implementation of {@link IDilaValidationService}
 */
public class DilaValidationService implements IDilaValidationService, Serializable
{
    /** The serial ID */
    private static final long serialVersionUID = 1670192896500470936L;
    @Inject
    @Named( "dilaXmlService" )
    private IDilaXmlService _dilaXmlService;
    @Inject
    @Named( "dilaLocalService" )
    private IDilaLocalService _dilaLocalService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateLocalFolder( LocalFolderDTO folderBean, List<LocalFolderLinkDTO> listLinks )
    {
        String errorKeyTheme = this.validateRootTheme( folderBean );
        String errorKeyLinkedFolder = this.validateLinkedFolder( folderBean );
        String errorKeyLinks = this.validateLinks( folderBean, listLinks );

        if ( StringUtils.isNotBlank( errorKeyTheme ) )
        {
            return errorKeyTheme;
        }
        else if ( StringUtils.isNotBlank( errorKeyLinkedFolder ) )
        {
            return errorKeyLinkedFolder;
        }
        else if ( StringUtils.isNotBlank( errorKeyLinks ) )
        {
            return errorKeyLinks;
        }
        else if ( StringUtils.isBlank( folderBean.getLocalDTO(  ).getAuthor(  ) ) ||
                StringUtils.isBlank( folderBean.getLocalDTO(  ).getTitle(  ) ) ||
                StringUtils.isBlank( folderBean.getPresentation(  ) ) )
        {
            return Messages.MANDATORY_FIELDS;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateLocalCard( LocalCardDTO cardBean, List<LocalCardChapterDTO> listChapters )
    {
        String errorKeyRootFolder = this.validateRootFolder( cardBean, cardBean.getParentFolderId(  ) );
        String errorKeyLinkedCard = this.validateLinkedCard( cardBean, cardBean.getSiblingCardId(  ) );
        String errorKeyChapters = this.validateChapters( listChapters );

        if ( StringUtils.isNotBlank( errorKeyRootFolder ) )
        {
            return errorKeyRootFolder;
        }
        else if ( StringUtils.isNotBlank( errorKeyLinkedCard ) )
        {
            return errorKeyLinkedCard;
        }
        else if ( StringUtils.isNotBlank( errorKeyChapters ) )
        {
            return errorKeyChapters;
        }
        else if ( StringUtils.isBlank( cardBean.getLocalDTO(  ).getAuthor(  ) ) ||
                StringUtils.isBlank( cardBean.getLocalDTO(  ).getTitle(  ) ) )
        {
            return Messages.MANDATORY_FIELDS;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateRootTheme( LocalFolderDTO folderBean )
    {
        String errorKey = null;

        folderBean.getLocalDTO(  ).setBreadCrumb( null );
        folderBean.setParentThemeTitle( null );

        if ( StringUtils.isBlank( folderBean.getParentThemeId(  ) ) ||
                ( folderBean.getParentThemeId(  ).length(  ) > 255 ) )
        {
            return "dila.create_fichelocale.error.themeParentVide";
        }
        else if ( folderBean.getParentThemeId(  ).startsWith( "N" ) )
        {
            // Search in XML folder
            List<String> availableTypes = new ArrayList<String>(  );

            availableTypes.add( ResourceTypeEnum.THEME.getLabel(  ) );
            availableTypes.add( ResourceTypeEnum.SUBTHEME.getLabel(  ) );

            XmlDTO xml = _dilaXmlService.findByIdAndTypesAndAudience( folderBean.getParentThemeId(  ), availableTypes,
                    folderBean.getLocalDTO(  ).getIdAudience(  ) );

            if ( xml != null )
            {
                folderBean.getLocalDTO(  ).setBreadCrumb( xml.getBreadcrumb(  ) );
                folderBean.setParentThemeTitle( xml.getTitle(  ) );
            }
        }

        if ( folderBean.getLocalDTO(  ).getBreadCrumb(  ) == null )
        {
            errorKey = "dila.create_fichelocale.error.themeIntrouvable";
        }

        return errorKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateLinkedFolder( LocalFolderDTO folderBean )
    {
        String errorKey = null;
        String linkedFolderTitle = null;

        if ( StringUtils.isNotBlank( folderBean.getSiblingFolderId(  ) ) )
        {
            if ( StringUtils.isNumeric( folderBean.getSiblingFolderId(  ) ) )
            {
                // Search in local folder
                linkedFolderTitle = _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( 
                            folderBean.getSiblingFolderId(  ) ), DilaLocalTypeEnum.FOLDER.getId(  ),
                        folderBean.getLocalDTO(  ).getIdAudience(  ) );
            }
            else if ( !folderBean.getSiblingFolderId(  ).startsWith( "F" ) )
            {
                // Search in XML folder
                List<String> availableTypes = new ArrayList<String>(  );
                availableTypes.add( ResourceTypeEnum.FOLDER.getLabel(  ) );

                linkedFolderTitle = _dilaXmlService.findTitleByIdAndTypesAndAudience( folderBean.getSiblingFolderId(  ),
                        availableTypes, folderBean.getLocalDTO(  ).getIdAudience(  ) );
            }

            if ( StringUtils.isBlank( linkedFolderTitle ) )
            {
                errorKey = "dila.create_fichelocale.error.dossierIntrouvable";
            }
            else
            {
                folderBean.setSiblingFolderTitle( linkedFolderTitle );
            }
        }

        return errorKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateRootFolder( LocalCardDTO cardBean, String strId )
    {
        String errorKey = null;
        String strTitle = null;

        if ( StringUtils.isBlank( cardBean.getParentFolderId(  ) ) ||
                ( cardBean.getParentFolderId(  ).length(  ) > 255 ) )
        {
            return "dila.create_fichelocale.error.dossierParentVide";
        }

        if ( StringUtils.isNumeric( cardBean.getParentFolderId(  ) ) )
        {
            // Search in local folder
            LocalDTO rootFolder = _dilaLocalService.findLocalByIdAndTypeAndAudience( Long.valueOf( 
                        cardBean.getParentFolderId(  ) ), DilaLocalTypeEnum.FOLDER.getId(  ),
                    cardBean.getLocalDTO(  ).getIdAudience(  ) );

            if ( rootFolder != null )
            {
                cardBean.getLocalParentFolder(  ).setLocalDTO( rootFolder );
                strTitle = rootFolder.getTitle(  );
            }
        }
        else if ( !cardBean.getParentFolderId(  ).startsWith( "F" ) )
        {
            // Search in XML folder
            List<String> availableTypes = new ArrayList<String>(  );
            availableTypes.add( ResourceTypeEnum.FOLDER.getLabel(  ) );

            XmlDTO rootXML = _dilaXmlService.findByIdAndTypesAndAudience( cardBean.getParentFolderId(  ),
                    availableTypes, cardBean.getLocalDTO(  ).getIdAudience(  ) );

            if ( rootXML != null )
            {
                cardBean.setNationalParentFolder( rootXML );
                strTitle = rootXML.getTitle(  );
            }
            else
            {
                errorKey = "dila.create_fichelocale.error.dossierIntrouvable";
            }
        }

        if ( ( cardBean.getLocalParentFolder(  ).getLocalDTO(  ).getId(  ) == null ) &&
                ( cardBean.getNationalParentFolder(  ).getIdXml(  ) == null ) )
        {
            errorKey = "dila.create_fichelocale.error.dossierIntrouvable";
        }

        cardBean.setParentFolderTitle( strTitle );

        return errorKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateLinkCard( LocalFolderDTO folderBean, String strId, LocalFolderLinkDTO link )
    {
        String errorKey = null;

        if ( StringUtils.isEmpty( strId ) || ( strId.length(  ) > 255 ) )
        {
            errorKey = Messages.MANDATORY_FIELDS;
        }
        else
        {
            String linkCardTitle = null;

            if ( StringUtils.isNumeric( strId ) )
            {
                // Search in local folder
                linkCardTitle = _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( strId ),
                        DilaLocalTypeEnum.CARD.getId(  ), folderBean.getLocalDTO(  ).getIdAudience(  ) );
            }
            else if ( strId.startsWith( "F" ) )
            {
                // Search in XML folder
                List<String> availableTypes = new ArrayList<String>(  );
                availableTypes.add( ResourceTypeEnum.CARD.getLabel(  ) );

                linkCardTitle = _dilaXmlService.findTitleByIdAndTypesAndAudience( strId, availableTypes,
                        folderBean.getLocalDTO(  ).getIdAudience(  ) );
            }

            if ( StringUtils.isBlank( linkCardTitle ) )
            {
                errorKey = "dila.create_fichelocale.error.ficheSoeurIntrouvable";
            }

            link.setCardTitle( linkCardTitle );
        }

        return errorKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateLinkedCard( LocalCardDTO cardBean, String strId )
    {
        String errorKey = null;
        String linkedCardTitle = null;

        if ( !StringUtils.isBlank( cardBean.getSiblingCardId(  ) ) )
        {
            if ( StringUtils.isNumeric( cardBean.getSiblingCardId(  ) ) )
            {
                // Search in local folder
                linkedCardTitle = _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( 
                            cardBean.getSiblingCardId(  ) ), DilaLocalTypeEnum.CARD.getId(  ),
                        cardBean.getLocalDTO(  ).getIdAudience(  ) );
            }
            else if ( cardBean.getSiblingCardId(  ).startsWith( "F" ) )
            {
                // Search in XML folder
                List<String> availableTypes = new ArrayList<String>(  );
                availableTypes.add( ResourceTypeEnum.CARD.getLabel(  ) );

                linkedCardTitle = _dilaXmlService.findTitleByIdAndTypesAndAudience( cardBean.getSiblingCardId(  ),
                        availableTypes, cardBean.getLocalDTO(  ).getIdAudience(  ) );
            }

            if ( StringUtils.isBlank( linkedCardTitle ) )
            {
                // The linked card doesn't exist
                errorKey = "dila.create_fichelocale.error.ficheSoeurIntrouvable";
            }

            cardBean.setSiblingCardTitle( linkedCardTitle );
        }

        return errorKey;
    }

    /**
     * Check if links are valid
     * @param folderBean the fodler to validate
     * @param listLinks list to validate
     * @return error key or null
     */
    private String validateLinks( LocalFolderDTO folderBean, List<LocalFolderLinkDTO> listLinks )
    {
        for ( LocalFolderLinkDTO link : listLinks )
        {
            if ( StringUtils.isEmpty( link.getTitle(  ) ) || StringUtils.isEmpty( link.getCardId(  ) ) )
            {
                // All fields are not filled
                return Messages.MANDATORY_FIELDS;
            }

            if ( StringUtils.isNotBlank( this.validateLinkCard( folderBean, link.getCardId(  ), link ) ) )
            {
                // The linked card doesn't exist
                return "dila.create_fichelocale.error.ficheSoeurIntrouvable";
            }
        }

        return null;
    }

    /**
     * Check if chapters are valid
     * @param listChapters list of chapters
     * @return error key or null
     */
    private String validateChapters( List<LocalCardChapterDTO> listChapters )
    {
        for ( LocalCardChapterDTO chapter : listChapters )
        {
            if ( StringUtils.isEmpty( chapter.getTitle(  ) ) || StringUtils.isEmpty( chapter.getContent(  ) ) )
            {
                // All fields are not filled
                return Messages.MANDATORY_FIELDS;
            }
        }

        return null;
    }
}
