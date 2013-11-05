<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15"
		cdata-section-elements="script" indent="yes" />

	<!-- Dossier -->
	<xsl:template match="Dossier" mode="Sous-theme">
		<h4>
			<xsl:attribute name="id">
                    <xsl:call-template name="createThemeDossierId" />
                </xsl:attribute>
			<xsl:variable name="title">
				<xsl:value-of select="../dc:title" />
				<xsl:value-of select="$sepFilDAriane" />
				<xsl:value-of select="Titre" />
			</xsl:variable>
			<xsl:call-template name="getPublicationLinkBrand">
				<xsl:with-param name="href">
					<xsl:value-of select="@ID" />
				</xsl:with-param>
				<xsl:with-param name="title">
					<xsl:value-of select="$title" />
				</xsl:with-param>
				<xsl:with-param name="text">
					<xsl:value-of select="Titre" />
				</xsl:with-param>
			</xsl:call-template>
			<xsl:if test="count(Fiche) > 0">
				<ul class="spSousThemeDossierFiche">
					<xsl:apply-templates select="Fiche" mode="Sous-theme" />
				</ul>
			</xsl:if>
		</h4>
	</xsl:template>

	<xsl:template match="Dossier" mode="Sans-sous-theme">
		<div class="spSousThemeDossier clearall">
			<xsl:attribute name="id">
                    <xsl:call-template name="createThemeDossierId" />
                </xsl:attribute>
             <xsl:if test="$CATEGORIE = 'Particulier'">
			<div class="entiteImageFloatLeft">
				<xsl:call-template name="imageOfATheme">
					<xsl:with-param name="id">
						<xsl:choose>
							<xsl:when test="//Publication/@type = 'Theme'">
								<xsl:value-of select="//Publication/@ID" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="//Publication/FilDAriane/Niveau/@ID" />
							</xsl:otherwise>
						</xsl:choose>
					</xsl:with-param>
				</xsl:call-template>
			</div>
			</xsl:if>
			<xsl:variable name="title">
				<xsl:value-of select="../dc:title" />
				<xsl:value-of select="$sepFilDAriane" />
				<xsl:value-of select="Titre" />
			</xsl:variable>
			<h2>
				<xsl:call-template name="getPublicationLink">
					<xsl:with-param name="href">
						<xsl:value-of select="@ID" />
					</xsl:with-param>
					<xsl:with-param name="title">
						<xsl:value-of select="$title" />
					</xsl:with-param>
					<xsl:with-param name="text">
						<xsl:value-of select="Titre" />
					</xsl:with-param>
				</xsl:call-template>
			</h2>
		</div>
	</xsl:template>

	<xsl:template match="Dossier" mode="Theme">
		<xsl:variable name="title">
			<xsl:value-of select="/Publication/dc:title" />
			<xsl:value-of select="$sepFilDAriane" />
			<xsl:value-of select="../Titre" />
			<xsl:value-of select="$sepFilDAriane" />
			<xsl:value-of select="text()" />
		</xsl:variable>
		<li class="spThemeSousThemeDossier">
			<h5>
				<xsl:call-template name="getPublicationLink">
					<xsl:with-param name="href">
						<xsl:value-of select="@ID" />
					</xsl:with-param>
					<xsl:with-param name="title">
						<xsl:value-of select="$title" />
					</xsl:with-param>
					<xsl:with-param name="text">
						<xsl:value-of select="text()" />
					</xsl:with-param>
				</xsl:call-template>
			</h5>
		</li>
	</xsl:template>

</xsl:stylesheet>
