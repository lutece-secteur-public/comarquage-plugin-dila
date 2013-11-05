<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
    
    <xsl:template match="ServiceComplementaire" mode="Glossaire">
        <xsl:call-template name="getBarre10Themes"/>
        <xsl:call-template name="getFilDArianeOfRessource"/>
        <div class="span12" style="margin-left:0px;">
            <div class="well">
                <xsl:call-template name="getTitreOfRessource"/>
                <div class="alert alert-success">
                    <xsl:apply-templates select="Texte" mode="Definition"/>
                    <xsl:call-template name="affReference" />       
                    <xsl:call-template name="affServiceEnLigne" />      
                    <xsl:call-template name="affCentreDeContact" />     
                    <xsl:call-template name="affPourEnSavoirPlus" />        
                    <xsl:call-template name="affSiteInternetPublic" />      
                </div>
                <xsl:call-template name="ancreTop"/>
            </div>
        </div>
    </xsl:template>
    
</xsl:stylesheet>
