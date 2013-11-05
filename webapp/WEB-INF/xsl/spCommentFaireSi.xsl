<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
        
    <xsl:template name="affCommentFaireSi" mode="Publication">
        <xsl:if test="count(CommentFaireSi) > 0">
            <div class="spPublicationCFS" id="sp-comment-faire-si">
                <h4><xsl:text>Comment faire si</xsl:text></h4>
                <ul class="spPublicationCFS">
                    <xsl:for-each select="CommentFaireSi">
                        <xsl:apply-templates select="." mode="Publication"/>
                    </xsl:for-each>
                    <xsl:call-template name="lienVersAccueilCommentFaireSi"/>
                </ul>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="CommentFaireSi" mode="Publication">
        <xsl:variable name="title">
            <xsl:value-of select="../dc:title"/>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:text>Comment faire si</xsl:text>
            <xsl:value-of select="$sepFilDAriane"/>
            <xsl:value-of select="text()"/>
        </xsl:variable>
        <li class="spPublicationCFS">
            <h5>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="@ID"/></xsl:with-param>
                    <xsl:with-param name="title"><xsl:value-of select="$title"/></xsl:with-param>
                    <xsl:with-param name="text"><xsl:value-of select="text()"/></xsl:with-param>
                </xsl:call-template>
            </h5>
        </li>
    </xsl:template>
    
    <xsl:template name="lienVersAccueilCommentFaireSi">
        <xsl:variable name="idAccueil">
            <xsl:choose>
                <xsl:when test="$CATEGORIE = 'particuliers'">
                    <xsl:text>N13042</xsl:text>
                </xsl:when>
                <xsl:when test="$CATEGORIE = 'associations'">
                    <xsl:text>N31000</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>N23971</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL"/>
            <xsl:value-of select="$idAccueil" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <li class="spPublicationCFS">
            <h5>
                <xsl:call-template name="getPublicationLink">
                    <xsl:with-param name="href"><xsl:value-of select="$idAccueil"/></xsl:with-param>
                    <xsl:with-param name="title">
                        <xsl:call-template name="getDescription">
                            <xsl:with-param name="id"><xsl:value-of select="$idAccueil"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:with-param>
                    <xsl:with-param name="text"><xsl:text>Accueil comment faire si...</xsl:text></xsl:with-param>
                </xsl:call-template>
            </h5>
        </li>
    </xsl:template>

</xsl:stylesheet>
