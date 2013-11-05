<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template name="affReference" mode="Publication">
        <xsl:if test="count(Reference) > 0">
            <div class="spPublicationReference well" id="sp-reference">
                 <div class="navbar">
                    <div class="navbar-inner">
                        <a class="brand">Références</a>
                     </div>
                 </div>
                <xsl:apply-templates select="Reference" mode="Publication"/>
            </div>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="Reference" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:text>Référence</xsl:text>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="Titre"/>
        </xsl:variable>
        <ul class="spPublicationReference">
            <li class="spPublicationReference">
                <h5>
                    <xsl:call-template name="getSiteLink">
                        <xsl:with-param name="href"><xsl:value-of select="@URL"/></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                        <xsl:with-param name="text">
                            <xsl:value-of select="Titre"/>
                            <xsl:if test="@commentaireLien">
                                <xsl:text> - </xsl:text>
                                <xsl:value-of select="@commentaireLien"/>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </h5>
                <xsl:if test="Complement">
                    <xsl:text> - </xsl:text>
                    <xsl:value-of select="Complement"/>
                </xsl:if>
            </li>
        </ul>
    </xsl:template>
    
</xsl:stylesheet>
