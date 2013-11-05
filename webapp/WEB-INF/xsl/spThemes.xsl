<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:import href="spCommon.xsl" />
    <xsl:import href="spFilDAriane.xsl" />

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <xsl:template match="/">
        <xsl:if test="$CATEGORIE = 'particuliers'">
            <div class="center">
                <h1>Vos droits et démarches en tant que particulier</h1>
            </div>
        </xsl:if>
        <xsl:if test="$CATEGORIE = 'associations'">
            <div class="center">
                <h1>Vos droits et démarches en tant qu'association</h1>
            </div>
        </xsl:if>
        <div id="noeudThemes">
            <xsl:call-template name="noeudThemes" />
        </div>
    </xsl:template>

    <!-- Rubriques principales -->
    <xsl:template name="noeudThemes">
        <div class="span3" style="margin-left:0px;">
            <xsl:if test="count(/Noeud/CommentFaire) > 0">
                <div class="well">
                    <h4>Comment faire si ...</h4>
                    <xsl:for-each select="/Noeud/CommentFaire">
                        <xsl:call-template name="commentFaire" />
                    </xsl:for-each>
                </div>
            </xsl:if>
            <xsl:if test="count(/Noeud/Fiche) &gt; 0">
                <div class="well">
                    <h4>Fiches locales</h4>
                    <xsl:for-each select="/Noeud/Fiche[position() &lt; 11]">
                        <xsl:call-template name="ficheLocale" />
                    </xsl:for-each>
                    <xsl:if test="count(/Noeud/Fiche) > 10">
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
        </div>
        <div class="span9">
            <div class="well">
                <xsl:for-each select="/Noeud/Descendance/Fils">
                    <xsl:call-template name="mainTheme" />
                </xsl:for-each>
            </div>
        </div>
    </xsl:template>

    <!-- Thème principal -->
    <xsl:template name="mainTheme">
        <xsl:variable name="href">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="@lien" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <xsl:variable name="titre">
            <xsl:apply-templates select="TitreContextuel" />
        </xsl:variable>
        <div class="noeudThemesFils">
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="pull-left">
                        <xsl:call-template name="imageOfATheme">
                            <xsl:with-param name="id" select="@lien" />
                        </xsl:call-template>
                    </div>
                    <a class="brand">
                        <xsl:attribute name="title">
                            <xsl:value-of select="normalize-space($titre)" />
                        </xsl:attribute>
                        <xsl:attribute name="href">
                            <xsl:value-of select="$REFERER" />
                            <xsl:value-of select="@lien" />
                        </xsl:attribute>
                        <xsl:copy-of select="$titre" />
                    </a>
                </div>
            </div>
            <xsl:call-template name="sousTheme">
                <xsl:with-param name="nameFile">
                    <xsl:value-of select="$href" />
                </xsl:with-param>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- Sous-thème d'un thème principal -->
    <xsl:template name="sousTheme">
        <xsl:param name="nameFile" />
        <xsl:variable name="Titre">
            <xsl:value-of select="TitreContextuel" />
        </xsl:variable>
        <xsl:for-each select="document($nameFile)/Publication/SousTheme">
            <xsl:call-template name="sousThemeLien">
                <xsl:with-param name="parentTitre">
                    <xsl:value-of select="$Titre" />
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
        <xsl:for-each select="document($nameFile)/Publication/Dossier">
            <xsl:call-template name="sousThemeLien">
                <xsl:with-param name="parentTitre">
                    <xsl:value-of select="$Titre" />
                </xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>

    <!-- Lien vers un sous-thème du thème principal -->
    <xsl:template name="sousThemeLien">
        <xsl:param name="parentTitre" />
        <xsl:variable name="titre">
            <xsl:apply-templates select="Titre" />
        </xsl:variable>
        <h4>
            <xsl:call-template name="getPublicationLink">
                <xsl:with-param name="href">
                    <xsl:value-of select="@ID" />
                </xsl:with-param>
                <xsl:with-param name="title">
                    <xsl:value-of select="$parentTitre" />
                    <xsl:value-of select="$sepFilDAriane" />
                    <xsl:value-of select="$titre" />
                </xsl:with-param>
                <xsl:with-param name="text">
                    <xsl:value-of select="$titre" />
                </xsl:with-param>
            </xsl:call-template>
        </h4>
    </xsl:template>

</xsl:stylesheet>
