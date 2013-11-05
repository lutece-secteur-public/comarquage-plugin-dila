<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
        
    <xsl:variable name="sepFilDAriane">
        <xsl:text><span class="divider">/</span></xsl:text> 
    </xsl:variable>
        
    <!-- Fild'ariane -->
    <xsl:template match="FilDAriane">
        <div class="span12 spFilDAriane" style="margin-top:10px;margin-left:0px;">
            <xsl:choose>
                <xsl:when test="$CATEGORIE = 'particuliers'">
                    <xsl:variable name="title">
                        <xsl:text>Vos droits et vos démarches en tant que particulier : Liste des thèmes</xsl:text>
                    </xsl:variable>
                    <ul class="breadcrumb">
                        <xsl:call-template name="imageOfATheme">
                            <xsl:with-param name="id">
                                <xsl:choose>
                                    <xsl:when test="//Publication/dc:type = 'Comment faire si'">
                                        <xsl:value-of select="//Publication/@ID"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="//Publication/FilDAriane/Niveau/@ID"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    <li>
                    <xsl:call-template name="getPublicationLink">
                        <xsl:with-param name="href"><xsl:text>Theme</xsl:text></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text"><xsl:text>Liste des thèmes</xsl:text></xsl:with-param>
                    </xsl:call-template>
                    </li>
                    <xsl:for-each select="Niveau">
                       <li>
                            <xsl:variable name="titleNiveau">
                                <xsl:text>Vos droits et vos démarches en tant que particulier : </xsl:text>
                                <xsl:value-of select="text()"/>
                            </xsl:variable>
                            <span class="divider">/</span>
                            <xsl:call-template name="getPublicationLink">
                                <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                                <xsl:with-param name="title"><xsl:value-of select="$titleNiveau"/></xsl:with-param>
                                <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                            </xsl:call-template>
                        </li>
                    </xsl:for-each>
                    <span class="divider">/</span>
                    <xsl:value-of select="//Publication/dc:title"/>
                    </ul>
                </xsl:when>
                <xsl:when test="$CATEGORIE = 'associations'">
                    <xsl:variable name="title">
                        <xsl:text>Vos droits et vos démarches en tant qu'associations : Liste des thèmes</xsl:text>
                    </xsl:variable>
                    <ul class="breadcrumb">
                        <xsl:call-template name="imageOfATheme">
                            <xsl:with-param name="id">
                                <xsl:choose>
                                    <xsl:when test="//Publication/dc:type = 'Comment faire si'">
                                        <xsl:value-of select="//Publication/@ID"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="//Publication/FilDAriane/Niveau/@ID"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    <li>
                    <xsl:call-template name="getPublicationLink">
                        <xsl:with-param name="href"><xsl:text>Theme</xsl:text></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text"><xsl:text>Liste des thèmes</xsl:text></xsl:with-param>
                    </xsl:call-template>
                    </li>
                    <xsl:for-each select="Niveau">
                       <li>
                            <xsl:variable name="titleNiveau">
                                <xsl:text>Vos droits et vos démarches en tant qu'associations : </xsl:text>
                                <xsl:value-of select="text()"/>
                            </xsl:variable>
                            <span class="divider">/</span>
                            <xsl:call-template name="getPublicationLink">
                                <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                                <xsl:with-param name="title"><xsl:value-of select="$titleNiveau"/></xsl:with-param>
                                <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                            </xsl:call-template>
                        </li>
                    </xsl:for-each>
                    <span class="divider">/</span>
                    <xsl:value-of select="//Publication/dc:title"/>
                    </ul>
                </xsl:when>
                <xsl:when test="$CATEGORIE = 'professionnels'">
                    <xsl:call-template name="getFilDArianeOfPublicationEntreprise"/>
                </xsl:when>
            </xsl:choose>
        </div>
    </xsl:template>
    
    <xsl:template name="getFilDArianeOfPublicationEntreprise">
        <ul class="breadcrumb">
            <xsl:variable name="title">
                <xsl:text>Vos droits et vos démarches en tant qu'entreprise : Liste des thèmes</xsl:text>
            </xsl:variable>
            <li>
            <xsl:call-template name="getPublicationLink">
                <xsl:with-param name="href"><xsl:text>Theme</xsl:text></xsl:with-param>
                <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                <xsl:with-param name="text"><xsl:text>Liste des thèmes</xsl:text></xsl:with-param>
            </xsl:call-template>
            </li>
            <xsl:for-each select="//Publication/FilDAriane/Niveau">
                <li>
                <xsl:variable name="titleNiveau">
                    <xsl:text>Vos droits et vos démarches en tant qu'entreprise : </xsl:text>
                    <xsl:value-of select="text()"/>
                </xsl:variable>
                    <span class="divider">/</span>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$titleNiveau"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                </xsl:call-template>
                </li>
            </xsl:for-each>
                    <span class="divider">/</span>
            <li><xsl:value-of select="//Publication/dc:title"/></li>
        </ul>
    </xsl:template>
    
    <xsl:template name="getFilDArianeOfRessource">
        <div class="span12 spFilDAriane" style="margin-top:10px;margin-left:0px;">
                <xsl:variable name="title">
                    <xsl:text>Vos droits et vos démarches en tant que particulier : Liste des thèmes</xsl:text>
                </xsl:variable>
            <ul class="breadcrumb">
            <li>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:text>Theme</xsl:text></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:text>Liste des thèmes</xsl:text></xsl:with-param>
                </xsl:call-template>
                </li>
                <span class="divider">/</span>
                <li><xsl:value-of select="//ServiceComplementaire/dc:title"/>
                </li>
            </ul>
        </div>
    </xsl:template>

    <xsl:template name="getFilDArianeOfPivotLocal">
        <div class="spFilDAriane">
            <xsl:call-template name="getPublicationLink">
                <xsl:with-param name="href"><xsl:text>Administrations</xsl:text></xsl:with-param>
                <xsl:with-param name="title"><xsl:text>Guide des administrations, des lieux publics, des artisans et des partenaires de A à Z</xsl:text></xsl:with-param>
                <xsl:with-param name="text"><xsl:text>Annuaire</xsl:text></xsl:with-param>
            </xsl:call-template>
            <span class="divider">/</span>
            <xsl:value-of select="//PivotLocal/Titre"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
