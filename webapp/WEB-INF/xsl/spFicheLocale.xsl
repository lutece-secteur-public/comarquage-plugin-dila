<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:import href="spFichePrincipale.xsl" />
    <xsl:import href="spNoeud.xsl" />

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />
    <xsl:template match="/">
        <xsl:variable name="type">
            <xsl:value-of select="/Publication/@type" />
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$type = 'Dossier'">
                <xsl:apply-templates mode="Noeud-dossier"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="Fiche" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
