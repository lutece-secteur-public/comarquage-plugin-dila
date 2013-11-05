<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
  	
	<xsl:template match="ServiceComplementaire" mode="Module-de-calcul">
		<xsl:call-template name="getBarre10Themes"/>
		<xsl:call-template name="getFilDArianeOfRessource"/>
		<xsl:call-template name="getTitreOfRessource"/>
		<xsl:apply-templates select="Texte"/>
		<xsl:call-template name="affServiceEnLigneOfRessource" />		
		<xsl:call-template name="affServiceNoticeOfRessource" />
	</xsl:template>
	
</xsl:stylesheet>
