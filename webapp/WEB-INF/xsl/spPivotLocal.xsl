<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <xsl:template name="affPivotLocal">
        <div class="spPublicationPivotOSATitle">
            <h4>
                <xsl:value-of select="PivotLocal/Organisme/Nom" />
            </h4>
        </div>

        <div class="spTexteChapitre">

            <p class="spTexteParagraphe">
                <xsl:value-of select="PivotLocal/Organisme/Adresse/Ligne"></xsl:value-of>
            </p>
            <p class="spTexteParagraphe">
                <xsl:value-of select="PivotLocal/Organisme/Adresse/CodePostal"></xsl:value-of>
                <xsl:text> </xsl:text>
                <xsl:value-of select="PivotLocal/Organisme/Adresse/NomCommune"></xsl:value-of>
            </p>
            <p class="spTexteParagraphe">
                <xsl:text>Téléphone : </xsl:text>
                <xsl:value-of select="PivotLocal/Organisme/CoordonnéesNum/Téléphone"></xsl:value-of>
            </p>
            <p class="spTexteParagraphe">
                <xsl:text>Télécopie : </xsl:text>
                <xsl:value-of select="PivotLocal/Organisme/CoordonnéesNum/Télécopie"></xsl:value-of>
            </p>
            <p class="spTexteParagraphe">
                <xsl:text>Courriel : </xsl:text>
                <xsl:value-of select="PivotLocal/Organisme/CoordonnéesNum/Email"></xsl:value-of>
            </p>
            <p class="spTexteParagraphe">
                <xsl:text>Site internet : </xsl:text>
                <xsl:value-of select="PivotLocal/Organisme/CoordonnéesNum/Url"></xsl:value-of>
            </p>
            <xsl:for-each select="PivotLocal/Organisme/Ouverture/PlageJ">
                <p class="spTexteParagraphe">
                    <xsl:text>Ouverture du </xsl:text>
                    <xsl:value-of select="@début" />
                    <xsl:text> au </xsl:text>
                    <xsl:value-of select="@fin" />
                    <xsl:text> de : <br /></xsl:text>
                    <xsl:value-of select="PlageH/@début" />
                    <xsl:text> à </xsl:text>
                    <xsl:value-of select="PlageH/@fin" />
                </p>
            </xsl:for-each>
        </div>
    </xsl:template>

</xsl:stylesheet>
