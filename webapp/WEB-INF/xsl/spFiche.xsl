<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template match="Fiche" mode="Sous-theme">
        <xsl:variable name="title">
            <xsl:value-of select="/Publication/dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="../Titre"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="text()"/>
        </xsl:variable>
        <li class="spSousThemeDossierFiche">
            <h3>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                </xsl:call-template>
            </h3>
            <xsl:call-template name="getDescription">
                <xsl:with-param name="id" select="@ID"/>
            </xsl:call-template>
        </li>
    </xsl:template>
    
    <xsl:template match="Fiche" mode="Noeud-dossier">
        <xsl:variable name="title">
            <xsl:value-of select="/Publication/dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="text()"/>
        </xsl:variable>
        <li class="spNoeudDossierFiche">
            <xsl:choose>
                <xsl:when test="name(..) = 'SousDossier'">
                    <h3>
                        <xsl:call-template name="getPublicationLink">
                            <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                            <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                            <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                        </xsl:call-template>
                    </h3>
                </xsl:when>
                <xsl:otherwise>
                    <h2>
                        <xsl:call-template name="getPublicationLink">
                            <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                            <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                            <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                        </xsl:call-template>
                    </h2>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:call-template name="getDescription">
                <xsl:with-param name="id" select="@ID"/>
            </xsl:call-template>
        </li>
    </xsl:template>

    <xsl:template match="Fiche" mode="Accueil-comment-faire-si">
        <div class="spAccueilComentFaireSiFiche">
            <xsl:variable name="title">
                <xsl:value-of select="../dc:title"/>
                <xsl:value-of select="$sepFilDAriane"/>
                <xsl:value-of select="text()"/>
            </xsl:variable>
            <h2>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                </xsl:call-template>
            </h2>
            <xsl:call-template name="getDescription">
                <xsl:with-param name="id"><xsl:value-of select="@ID"/></xsl:with-param>
            </xsl:call-template>
        </div>
    </xsl:template>

    <xsl:template match="Fiche" mode="QuestionReponse">
        <div class="spQuestionReponse">
            <xsl:variable name="title">
                <xsl:value-of select="../dc:title"/>
                <xsl:value-of select="$sepFilDAriane"/>
                <xsl:value-of select="text()"/>
            </xsl:variable>
            <h2>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                </xsl:call-template>
            </h2>
            <div class="entiteObjectFloatRight">            
                <xsl:call-template name="imageOfATheme">
                    <xsl:with-param name="id" select="@ID"/>
                </xsl:call-template>
            </div>
            <xsl:call-template name="getDescription">
                <xsl:with-param name="id"><xsl:value-of select="@ID"/></xsl:with-param>
            </xsl:call-template>
        </div>
    </xsl:template>
    
</xsl:stylesheet>
