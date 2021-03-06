<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">
    
    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <!-- Noeud de type Accueil Coment faire si -->
    <xsl:template match="Publication" mode="Accueil-comment-faire-si">
        <xsl:call-template name="getBarre10Themes"/>
        <xsl:apply-templates select="FilDAriane"/>
        <xsl:call-template name="getTitre"/>
        <div class="span12" style="margin-left:0px;">
          <div class="well">
        <!-- <xsl:call-template name="createSommaireNoeud" />-->
        <xsl:apply-templates select="Introduction"/>
        <div class="spAccueilCommentFaireSiMain">
            <xsl:for-each select="Fiche">
                <xsl:if test="((position() mod 2) = 1) and (position() > 1)">
                    <br class="clearall"/>
                </xsl:if>
                <xsl:apply-templates select="." mode="Accueil-comment-faire-si"/>
            </xsl:for-each>
            <br class="clearall"/>
        </div>
        </div>
        <xsl:call-template name="affActualite" />       
        <xsl:call-template name="affServiceEnLigne" />      
        <xsl:call-template name="affCentreDeContact" /> 
        </div>
    </xsl:template>
    
</xsl:stylesheet>
