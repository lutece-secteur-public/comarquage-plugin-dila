<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:import href="spPivotLocal.xsl" />

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <xsl:template name="affOuSAdresser" mode="Publication">
        <xsl:if test="count(OuSAdresser) > 0">
            <div class="spPublicationOSA well" id="sp-ou-sadresser">
                <div class="navbar">
                <div class="navbar-inner">
                <a class="brand">Où s'adresser ?</a>
                </div>
                </div>
                <xsl:apply-templates select="OuSAdresser" mode="Publication" />
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="OuSAdresser" mode="Publication">
        <div class="spPublicationPivotOSA">
            <xsl:variable name="exceptions">
                <xsl:text>,amb_fran,</xsl:text>
                <xsl:text>,ppp,</xsl:text>
                <xsl:text>,ppp_antenne,</xsl:text>
            </xsl:variable>
            <xsl:choose>
                <xsl:when test="PivotLocal">
                    <xsl:call-template name="affPivotLocal">
                        <xsl:with-param name="title">
                            <xsl:value-of select="PivotLocal/Organisme/Nom" />
                        </xsl:with-param>
                        <xsl:with-param name="address1">
                            <xsl:value-of select="PivotLocal/Organisme/Adresse/Ligne"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="address2">
                            <xsl:value-of select="Organisme/Adresse/CodePostal"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="address3">
                            <xsl:value-of select="Organisme/Adresse/NomCommune"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="telephone">
                            <xsl:value-of select="Organisme/CoordonnéesNum/Téléphone"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="telecopie">
                            <xsl:value-of select="Organisme/CoordonnéesNum/Télécopie"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="email">
                            <xsl:value-of select="Organisme/CoordonnéesNum/Email"></xsl:value-of>
                        </xsl:with-param>
                        <xsl:with-param name="url">
                            <xsl:value-of select="Organisme/CoordonnéesNum/Url"></xsl:value-of>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <div class="spPublicationPivotOSATitle">
                        <xsl:choose>
                            <xsl:when test="RessourceWeb">
                                <h4>
                                    <xsl:call-template name="getSiteLink">
                                        <xsl:with-param name="href">
                                            <xsl:value-of select="RessourceWeb/@URL" />
                                        </xsl:with-param>
                                        <xsl:with-param name="title">
                                            <xsl:value-of select="RessourceWeb/@URL" />
                                        </xsl:with-param>
                                        <xsl:with-param name="text">
                                            <xsl:value-of select="Titre" />
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </h4>
                            </xsl:when>
                            <xsl:otherwise>
                                <h4>
                                    <xsl:value-of select="Titre" />
                                </h4>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:if test="Complement">
                            <xsl:text> - </xsl:text>
                            <xsl:value-of select="Complement" />
                        </xsl:if>
                    </div>
                    <xsl:if test="Texte">
                        <xsl:apply-templates select="Texte" mode="OuSAdresser" />
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            <br class="clearall" />
        </div>
    </xsl:template>

    <xsl:template name="affOuSAdresserChapitre">
        <xsl:apply-templates mode="OuSAdresser" />
    </xsl:template>

    <xsl:template match="Code" mode="PivotLocal" />
    <xsl:template match="Coproducteur" mode="PivotLocal" />
    <xsl:template match="ZoneCompetenceGeographique" mode="PivotLocal" />
    <xsl:template match="Titre" mode="PivotLocal" />

    <xsl:template match="Adresse" mode="PivotLocal">
        <xsl:param name="cssWidth" />
        <div class="spPublicationPivotLocalOSA">
            <span class="bold">Adresse :</span>
            <br />
            <xsl:for-each select="AdresseLigne">
                <xsl:apply-templates mode="PivotLocal" />
                <br />
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template match="AdresseLigne" mode="PivotLocal">
        <xsl:apply-templates mode="PivotLocal" />
    </xsl:template>

    <xsl:template match="Communication" mode="PivotLocal">
        <xsl:param name="cssWidth" />
        <div class="spPublicationPivotLocalOSA">
            <span class="bold">Contacts :</span>
            <br />
            <xsl:for-each select="child::*">
                <xsl:apply-templates select="." mode="PivotLocal" />
                <br />
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template match="Tel" mode="PivotLocal">
        <xsl:choose>
            <xsl:when
                test="(substring(text(),0,2) = '06') or (substring(text(),0,5) = '+33 6')">
                <img src="{$PICTOS}mobile.jpg" width="11" height="20"
                    alt="Téléphone mobile - portable" />
            </xsl:when>
            <xsl:when
                test="(substring(text(),0,2) = '07') or (substring(text(),0,5) = '+33 7')">
                <img src="{$PICTOS}mobile.jpg" width="11" height="20"
                    alt="Téléphone mobile - portable" />
            </xsl:when>
            <xsl:otherwise>
                <img src="{$PICTOS}telephone.jpg" width="14" height="20" alt="Téléphone" />
            </xsl:otherwise>
        </xsl:choose>
        <xsl:text> </xsl:text>
        <xsl:value-of select="text()" />
    </xsl:template>

    <xsl:template match="Fax" mode="PivotLocal">
        <img src="{$PICTOS}fax.jpg" width="20" height="20" alt="Télécopie - fax" />
        <xsl:text> </xsl:text>
        <xsl:value-of select="text()" />
    </xsl:template>

    <xsl:template match="SiteInternet" mode="PivotLocal">
        <img src="{$PICTOS}www.jpg" width="32" height="20" alt="Site internet www" />
        <xsl:call-template name="getSiteLink">
            <xsl:with-param name="href">
                <xsl:value-of select="text()" />
            </xsl:with-param>
            <xsl:with-param name="title">
                <xsl:value-of select="text()" />
            </xsl:with-param>
            <xsl:with-param name="text">
                <xsl:text>Site internet</xsl:text>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="Courriel" mode="PivotLocal">
        <img src="{$PICTOS}courriel.jpg" width="24" height="20" alt="Courriel" />
        <xsl:variable name="text">
            <xsl:value-of select="normalize-space(text())" />
        </xsl:variable>
        <a href="mailto:{$text}" title="{$text}">
            <xsl:text>Courriel</xsl:text>
        </a>
    </xsl:template>

    <xsl:template match="ContactEnLigne" mode="PivotLocal">
        <img src="{$PICTOS}www.jpg" width="32" height="20"
            alt="Site internet - www" />
        <xsl:call-template name="getSiteLink">
            <xsl:with-param name="href">
                <xsl:value-of select="text()" />
            </xsl:with-param>
            <xsl:with-param name="title">
                <xsl:value-of select="text()" />
            </xsl:with-param>
            <xsl:with-param name="text">
                <xsl:text>Formulaire de contact en ligne</xsl:text>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="Horaires" mode="PivotLocal">
        <xsl:param name="cssWidth" />
        <div class="spPublicationPivotLocalOSA">
            <span class="bold">Horaires d'ouverture :</span>
            <br />
            <xsl:for-each select="Horaire">
                <xsl:apply-templates mode="PivotLocal" />
                <br />
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template match="Horaire" mode="PivotLocal">
        <xsl:apply-templates mode="PivotLocal" />
    </xsl:template>

    <xsl:template match="Gps" mode="PivotLocal">
        <xsl:variable name="code">
            <xsl:text>google_maps_</xsl:text>
            <xsl:value-of select="../Code" />
        </xsl:variable>
        <div class="spGoogleMaps" id="{$code}">
            <span id="{$code}_nom">
                <xsl:value-of select="../Titre" />
            </span>
            <br />
            <span id="{$code}_latitude">
                <xsl:value-of select="@latitude" />
            </span>
            <br />
            <span id="{$code}_longitude">
                <xsl:value-of select="@longitude" />
            </span>
            <br />
        </div>
    </xsl:template>

</xsl:stylesheet>
