<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <!-- Noeud de type Thème -->
    <xsl:template match="Publication" mode="Theme">
        <xsl:call-template name="getBarre10Themes" />
        <div class="span12 spFilDAriane" style="margin-top:10px;margin-left:0px;">
            <ul class="breadcrumb">
                <xsl:variable name="title">
                    <xsl:text>Vos droits et vos démarches en tant que particuliers : Liste des thèmes</xsl:text>
                </xsl:variable>
                <xsl:call-template name="imageOfATheme">
                    <xsl:with-param name="id" select="@ID" />
                </xsl:call-template>
                <li>
                    <xsl:call-template name="getPublicationLink">
                        <xsl:with-param name="href">
                            <xsl:text>Theme</xsl:text>
                        </xsl:with-param>
                        <xsl:with-param name="title">
                            <xsl:value-of select="$title" />
                        </xsl:with-param>
                        <xsl:with-param name="text">
                            <xsl:text>Liste des thèmes</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                </li>
                <span class="divider">/</span>
                <xsl:value-of select="dc:title" />
            </ul>
        </div>
        <xsl:call-template name="getTitre" />
        <div class="span3 spPublicationMenuGaucheForTheme" style="margin-left:0px;">

            <!-- <xsl:call-template name="createSommaireTheme"/> -->
            <xsl:choose>
                <xsl:when test="@ID='N20' and $CATEGORIE = 'associations'">
                    <xsl:if test="count(CommentFaireSi) > 0">
                        <div class="well">
                            <xsl:call-template name="affCommentFaireSi" />
                        </div>
                    </xsl:if>
                    <xsl:if test="count(Fiche) > 0">
                        <div class="well">
                            <h4>Fiches locales</h4>
                            <ul>
                                <xsl:for-each select="Fiche[position() &lt; 10]">
                                    <xsl:call-template name="ficheLocale" />
                                </xsl:for-each>
                            </ul>
                            <xsl:if test="count(Fiche) > 10">
                                <xsl:element name="a">
                                    <xsl:attribute name="href">
                                        <xsl:text>jsp/site/Portal.jsp?page=dilaLocal&amp;categorie=</xsl:text>
                                        <xsl:value-of
                                        select="$CATEGORIE"></xsl:value-of>
                                    </xsl:attribute>
                                    <xsl:attribute name="style">
                                        <xsl:text>float:right;</xsl:text>
                                    </xsl:attribute>
                                    Voir toutes les fiches locales
                                </xsl:element>
                            </xsl:if>
                        </div>
                    </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="displayHowToAndLocalCardsLinks" />
                    <div class="well jsonly">
                        <!-- <xsl:call-template name="createSommaireFiche" /> -->
                        <xsl:call-template name="createArborescenceTheme">
                            <xsl:with-param name="ID">
                                <xsl:value-of select="dc:identifier" />
                            </xsl:with-param>
                            <xsl:with-param name="SCRIPT">
                                <xsl:text>true</xsl:text>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                    <noscript>
                        <style> .jsonly {display:none}</style>
                        <div class="well">
                            <xsl:call-template name="createArborescenceTheme">
                                <xsl:with-param name="ID">
                                    <xsl:value-of select="dc:identifier" />
                                </xsl:with-param>
                                <xsl:with-param name="SCRIPT">
                                    <xsl:text>false</xsl:text>
                                </xsl:with-param>
                            </xsl:call-template>
                        </div>
                    </noscript>
                </xsl:otherwise>
            </xsl:choose>
        </div>
        <div class="span6">
            <xsl:choose>
                <xsl:when test="count(SousTheme) > 0">
                    <div class="well">
                        <xsl:apply-templates select="SousTheme" mode="Theme" />
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="count(Dossier) > 0">
                        <div class="well">
                            <xsl:apply-templates select="Dossier" mode="Sans-sous-theme" />
                        </div>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:call-template name="affVoirAussi">
                <xsl:with-param name="titre">
                    <xsl:text>thèmes</xsl:text>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="affQuestionReponse" />
            <xsl:call-template name="affCentreDeContact" />
            <xsl:call-template name="affSiteInternetPublic" />
            <xsl:call-template name="ancreTop" />
        </div>
        <div class="span3 spPublicationMenuDroiteForTheme">
            <xsl:call-template name="affActualite" />
            <xsl:call-template name="affServiceEnLigneDroite" />
        </div>
    </xsl:template>

</xsl:stylesheet>
