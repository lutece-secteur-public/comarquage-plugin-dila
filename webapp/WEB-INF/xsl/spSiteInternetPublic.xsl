<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template name="affSiteInternetPublic" mode="Publication">
        <xsl:if test="count(SiteInternetPublic) > 0">
            <div class="spPublicationSIP well" id="sp-site-internet-public">
                <div class="navbar navbar-inner">
					<a class="brand">Sites internet publics</a>
				</div>
                <xsl:apply-templates select="SiteInternetPublic" mode="Publication"/>
            </div>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="SiteInternetPublic" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:text>Site internet public</xsl:text>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="@complementLien"/>
        </xsl:variable>
        <ul class="spPublicationSIP">
            <li class="spPublicationSIP">
                <h5>
                    <xsl:call-template name="getSiteLink">
                        <xsl:with-param name="href"><xsl:value-of select="@URL"/></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text">
                            <xsl:value-of select="@complementLien"/>
                            <xsl:text> - </xsl:text>
                            <xsl:value-of select="Titre"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </h5>
                <xsl:text> - [</xsl:text>
                    <xsl:choose>
                        <xsl:when test="Source/text()">
                            <xsl:apply-templates select="Source"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="getPublicationLink">
                                <xsl:with-param name="href"><xsl:value-of select="Source/@ID"/></xsl:with-param>
                                <xsl:with-param name="title"><xsl:text>En savoir plus sur </xsl:text><xsl:value-of select="Titre"/></xsl:with-param>
                                <xsl:with-param name="text"><xsl:text>Pour en savoir plus sur </xsl:text><xsl:value-of select="Titre"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                <xsl:text>]</xsl:text>
            </li>
        </ul>
    </xsl:template>
    
</xsl:stylesheet>
