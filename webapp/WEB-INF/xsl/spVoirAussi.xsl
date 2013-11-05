<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template name="affVoirAussiColonne" mode="Publication">
        <xsl:param name="titre"></xsl:param>
        <xsl:if test="count(VoirAussi) > 0">
            <div class="spPublicationVA well" id="sp-voir-aussi">
                <h4><xsl:text>Voir aussi dans les autres </xsl:text><xsl:value-of select="$titre"/></h4>
                <xsl:apply-templates select="VoirAussi" mode="Publication"/>
            </div>
        </xsl:if>
    </xsl:template>
    
    <xsl:template name="affVoirAussi" mode="Publication">
        <xsl:param name="titre"></xsl:param>
        <xsl:if test="count(VoirAussi) > 0">
            <div class="spPublicationVA well" id="sp-voir-aussi">
                <div class="navbar">
                <div class="navbar-inner">
                <a class="brand">Voir aussi dans les autres <xsl:value-of select="$titre"/></a>
                </div>
                </div>
                <xsl:apply-templates select="VoirAussi" mode="Publication"/>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="VoirAussi" mode="Publication">
        <ul class="spPublicationVA">
            <xsl:for-each select="Dossier">
                <xsl:variable name="title">
                    <xsl:value-of select="Theme"/>
                    <xsl:value-of select="$sepFilDAriane"/>
                    <xsl:value-of select="Titre"/>
                </xsl:variable>
                <li class="spPublicationVA">
                    <h5>
                        <xsl:call-template name="getPublicationLink">
                            <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                            <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                            <xsl:with-param name="text"><xsl:value-of select="Titre"/></xsl:with-param>
                        </xsl:call-template>
                    </h5>
                    <xsl:text> - [</xsl:text>
                    <xsl:call-template name="getPublicationLink">
                        <xsl:with-param name="href"><xsl:value-of select="Theme/@ID"/></xsl:with-param>
                        <xsl:with-param name="title"><xsl:value-of select="Theme"/></xsl:with-param>
                        <xsl:with-param name="text"><xsl:value-of select="Theme"/></xsl:with-param>
                    </xsl:call-template>
                    <xsl:text>] </xsl:text>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>

</xsl:stylesheet>
