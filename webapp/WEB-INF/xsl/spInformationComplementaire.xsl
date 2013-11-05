<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
  	
	<xsl:template name="affInformationComplementaire" mode="Publication">
		<xsl:if test="count(InformationComplementaire) > 0">
			<div class="well">
				<div class="spPublicationIC" id="sp-information-complementaire">
					<xsl:apply-templates select="InformationComplementaire" mode="Publication"/>
				</div>
			</div>
		</xsl:if>
	</xsl:template>

	<xsl:template match="InformationComplementaire" mode="Publication">
		<h4>
			<xsl:value-of select="Titre"/>
			<xsl:text> - </xsl:text>
			<xsl:call-template name="transformRssDate">
				<xsl:with-param name="date">
					<xsl:value-of select="@date"/>
					<xsl:text>TZ</xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</h4>
		<xsl:apply-templates select="Texte"/>
	</xsl:template>
	
</xsl:stylesheet>
