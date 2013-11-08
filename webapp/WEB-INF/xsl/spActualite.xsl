<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <xsl:template name="affActualite" mode="Pulication">
        <xsl:if test="count(Actualite) > 0">
            <div class="spPublicationActualite well" id="sp-actualite">
                <h4>
                    <xsl:text>Actualités</xsl:text>
                </h4>
                <xsl:apply-templates select="Actualite" mode="Publication" />
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="Actualite" mode="Publication">
        <xsl:choose>
            <xsl:when test="contains(@type,'Fil')">
                <h5>
                    <xsl:call-template name="getSiteLink">
                        <xsl:with-param name="href">
                            <xsl:value-of select="@URL" />
                        </xsl:with-param>
                        <xsl:with-param name="title">
                            <xsl:text>Lien vers le flux RSS d'actualités</xsl:text>
                        </xsl:with-param>
                        <xsl:with-param name="text">
                            <xsl:value-of select="Titre" />
                        </xsl:with-param>
                    </xsl:call-template>
                </h5>
                <xsl:call-template name="filActualite" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="articleActualite" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="articleActualite" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title" />
            <xsl:value-of select="$sepFilDAriane" />
            <xsl:value-of select="Titre" />
        </xsl:variable>
        <ul class="spPublicationActualite">
            <li class="spPublicationActualite">
                <h5>
                    <xsl:call-template name="getSiteLink">
                        <xsl:with-param name="href">
                            <xsl:value-of select="@URL" />
                        </xsl:with-param>
                        <xsl:with-param name="title">
                            <xsl:value-of select="$title" />
                        </xsl:with-param>
                        <xsl:with-param name="text">
                            <xsl:value-of select="Titre" />
                        </xsl:with-param>
                    </xsl:call-template>
                </h5>
                <xsl:text> - </xsl:text>
                <xsl:value-of select="@type" />
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="filActualite" mode="Publication">
        <ul class="spPublicationActualite">
            <xsl:choose>
                <xsl:when test="item">
                    <xsl:for-each select="item">
                        <li class="spPublicationActualite">
                            <h5>
                                <xsl:call-template name="getSiteLink">
                                    <xsl:with-param name="href">
                                        <xsl:value-of select="link" />
                                    </xsl:with-param>
                                    <xsl:with-param name="title">
                                        <xsl:value-of select="description" />
                                    </xsl:with-param>
                                    <xsl:with-param name="text">
                                        <xsl:value-of select="title" />
                                    </xsl:with-param>
                                </xsl:call-template>
                            </h5>
                            <xsl:text> - </xsl:text>
                            <xsl:call-template name="transformRssDate">
                                <xsl:with-param name="date">
                                    <xsl:choose>
                                        <xsl:when test="dc:date">
                                            <xsl:value-of select="dc:date" />
                                        </xsl:when>
                                        <xsl:when test="pubDate">
                                            <xsl:value-of select="pubDate" />
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:with-param>
                            </xsl:call-template>
                        </li>
                    </xsl:for-each>
                </xsl:when>
                <xsl:when test="Error">
                    <li class="spPublicationActualite">
                        <xsl:value-of select="Error"/>
                    </li>
                </xsl:when>
            </xsl:choose>
        </ul>
    </xsl:template>
</xsl:stylesheet>
