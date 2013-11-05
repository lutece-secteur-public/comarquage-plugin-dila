<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15" cdata-section-elements="script" indent="yes"/> 
  	
	<xsl:template name="affMontant" mode="Publication">
		<xsl:if test="count(Montant) > 0">
			<div class="spPublicationMontant" id="sp-montant">
				<xsl:apply-templates select="Montant" mode="Publication"/>
			</div>
		</xsl:if>
	</xsl:template>

	<xsl:template match="Montant" mode="Publication">
		<h2>
			<xsl:value-of select="Titre"/>
			<xsl:text> - </xsl:text>
			<xsl:call-template name="transformRssDate">
				<xsl:with-param name="date">
					<xsl:value-of select="@date"/>
					<xsl:text>TZ</xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</h2>
		<xsl:apply-templates select="Texte"/>
	</xsl:template>
	
</xsl:stylesheet>
