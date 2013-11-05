<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template match="Titre" mode="Sous-theme">
        <h2><xsl:value-of select="text()"/></h2>
    </xsl:template>

    <xsl:template match="Titre" mode="Theme">
        <a class="brand"><xsl:value-of select="text()"/></a>
    </xsl:template>

    <xsl:template match="Titre" mode="Definition">
        <h2><xsl:value-of select="text()"/></h2>
    </xsl:template>

    <xsl:template match="Titre" mode="OuSAdresser">
        <h4><xsl:apply-templates/></h4>
    </xsl:template>

    <xsl:template match="Titre" mode="Noeud-dossier">
        <div class="navbar">
            <div class="navbar-inner">
            <div class="pull-left">
                <xsl:call-template name="imageOfATheme">
                    <xsl:with-param name="id" select="//Publication/FilDAriane/Niveau/@ID"/>
                </xsl:call-template>
            </div>
            <a class="brand">
             <xsl:value-of select="text()"/>
            </a>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="getTitre">
        <div class="spCenter"><h1><xsl:value-of select="//Publication/dc:title"/></h1></div>
    </xsl:template>
    
    <xsl:template name="getTitreOfRessource">
        <div class="spCenter">
            <h1>
                <xsl:value-of select="//ServiceComplementaire/dc:title"/>
            </h1>
            <xsl:value-of select="//ServiceComplementaire/dc:type"/>
            <xsl:if test="//ServiceComplementaire/NumeroCerfa">
                <xsl:text> - Cerfa n°</xsl:text>
                <xsl:value-of select="//ServiceComplementaire/NumeroCerfa"/>
            </xsl:if>
            <xsl:if test="//ServiceComplementaire/AutreNumero">
                <xsl:text> - Autre n°</xsl:text>
                <xsl:value-of select="//ServiceComplementaire/AutreNumero"/>
            </xsl:if>
            <xsl:text> - </xsl:text>
            <xsl:call-template name="getMAJDateContributor"/><br/><br/>
        </div>
    </xsl:template>

    <xsl:template match="Titre">
        <xsl:choose>
            <xsl:when test="name(..) = 'Chapitre'">
                <div class="navbar">
                <div class="navbar-inner">
                    <div class="pull-left">
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
                    </div>
                <a class="brand"><xsl:apply-templates/></a>
                </div>
                </div>
            </xsl:when>
            <xsl:when test="name(..) = 'SousChapitre'">
                <h4><xsl:apply-templates/></h4>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
