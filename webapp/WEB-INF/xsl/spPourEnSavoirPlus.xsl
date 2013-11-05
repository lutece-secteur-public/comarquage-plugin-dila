<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template name="affPourEnSavoirPlus" mode="Publication">
        <xsl:if test="count(PourEnSavoirPlus) > 0">
            <div class="spPublicationPESP well" id="sp-pour-en-savoir-plus">
                <div class="navbar">
                <div class="navbar-inner">
                <a class="brand">Pour en savoir plus ?</a>
                </div>
                </div>
                <xsl:apply-templates select="PourEnSavoirPlus" mode="Publication" />
            </div>
        </xsl:if>   
    </xsl:template>

    <xsl:template match="PourEnSavoirPlus" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="Titre"/>
        </xsl:variable>
        <ul class="spPublicationPESP">
            <li class="spPublicationPESP">
                <h5>
                    <xsl:call-template name="getSiteLink">
                        <xsl:with-param name="href"><xsl:value-of select="@URL"/></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text"><xsl:value-of select="Titre"/></xsl:with-param>
                    </xsl:call-template>
                </h5>
                <xsl:text> - </xsl:text>
                <xsl:value-of select="@type"/>
                <xsl:if test="Source">
                    <xsl:variable name="file">
                        <xsl:value-of select="$XMLURL"/>
                        <xsl:value-of select="Source/@ID"/>
                        <xsl:text>.xml</xsl:text>
                    </xsl:variable>
                    <xsl:text> - </xsl:text>
                    <xsl:value-of select="Source/text()"/>
                </xsl:if>
            </li>
        </ul>
    </xsl:template>
    
</xsl:stylesheet>
