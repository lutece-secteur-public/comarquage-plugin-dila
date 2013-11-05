<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template name="affCentreDeContact" mode="Publication">
        <xsl:if test="count(CentreDeContact) > 0">
            <div class="spPublicationCDC well" id="sp-centre-de-contact">
                <div class="navbar">
                <div class="navbar-inner">
                <a class="brand">Centres d'appel et  de contact</a>
                </div>
                </div>
                <xsl:apply-templates select="CentreDeContact" mode="Publication"/>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="CentreDeContact" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:text>Centres d'appel et de contact</xsl:text>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="text()"/>
        </xsl:variable>
        <ul class="spPublicationCDC">
            <li class="spPublicationCDC">
                <h5>
                    <xsl:call-template name="getPublicationLink">
                        <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                    </xsl:call-template>
                </h5>
            </li>
        </ul>
    </xsl:template>

    <xsl:template match="ServiceComplementaire" mode="Centre-de-contact">
        <xsl:call-template name="getBarre10Themes"/>
        <xsl:call-template name="getFilDArianeOfRessource"/>
        <xsl:call-template name="getTitreOfRessource"/>
        <div class="span12" style="margin-left:0px;">
            <div class="well">
                <xsl:apply-templates select="Texte" mode="OuSAdresser"/>
            </div>
        </div>   
     </xsl:template>
    
</xsl:stylesheet>
