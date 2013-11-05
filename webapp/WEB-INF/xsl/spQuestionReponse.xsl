<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15"
		cdata-section-elements="script" indent="yes" />

	<xsl:template name="affQuestionReponse" mode="Publication">
		<xsl:if test="count(QuestionReponse) > 0">
			<div class="spPublicationQR well" id="sp-question-reponse">
				<div class="navbar">
					<div class="navbar-inner">
						<a class="brand">Questions - Réponses ?</a>
					</div>
				</div>
				<xsl:apply-templates select="QuestionReponse"
					mode="Publication" />
			</div>
		</xsl:if>
	</xsl:template>

	<xsl:template match="QuestionReponse" mode="Publication">
		<xsl:variable name="title">
			<xsl:value-of select="../dc:title" />
			<xsl:value-of select="$sepFilDAriane" />
			<xsl:value-of select="text()" />
		</xsl:variable>
		<ul class="spPublicationQR">
			<li class="spPublicationQR">
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
				<xsl:call-template name="getDescription">
					<xsl:with-param name="id" select="@ID" />
				</xsl:call-template>
			</li>
		</ul>
	</xsl:template>

	<xsl:template mode="QuestionReponse" match="Publication">
		<xsl:variable name="title">
			<xsl:value-of select="../dc:title" />
			<xsl:value-of select="$sepFilDAriane" />
			<xsl:value-of select="text()" />
		</xsl:variable>
		<xsl:call-template name="getBarre10Themes" />
		<xsl:apply-templates select="FilDAriane" />
		<xsl:call-template name="getTitre" />
		<div class="span3 spPublicationMenuGauche" style="margin-left: 0px;">
			<xsl:call-template name="displayHowToAndLocalCardsLinks" />
			<!-- <xsl:call-template name="createSommaireFiche" /> -->
			<div class="well jsonly">
				<!-- <xsl:call-template name="createSommaireFiche" /> -->
				<xsl:call-template name="createArborescenceTheme">
					<xsl:with-param name="ID">
						<xsl:value-of select="Theme/@ID" />
					</xsl:with-param>
					<xsl:with-param name="SCRIPT">
						<xsl:text>true</xsl:text>
					</xsl:with-param>
				</xsl:call-template>
			</div>
			<noscript>
				<style> .jsonly {display:none}</style>
				<div class="well">
					<xsl:call-template name="createArborescenceTheme">
						<xsl:with-param name="ID">
							<xsl:value-of select="Theme/@ID" />
						</xsl:with-param>
						<xsl:with-param name="SCRIPT">
							<xsl:text>false</xsl:text>
						</xsl:with-param>
					</xsl:call-template>
				</div>
			</noscript>
		</div>

		<div class="span6">
			<div class="well">
				<xsl:apply-templates select="Introduction" />
				<xsl:apply-templates select="Texte" />
			</div>
			<xsl:call-template name="affOuSAdresser" />
			<xsl:call-template name="affReference" />
			<xsl:call-template name="affSiteInternetPublic" />
		</div>
		<xsl:if
			test="(count(Actualite)+ count(InformationComplementaire) + count(ServiceEnLigne)) > 0">
			<div class="span3">
				<div class="well">
					<div class="spPublicationMenuDroite">
						<xsl:call-template name="affActualite" />
						<xsl:call-template name="affInformationComplementaire" />
						<xsl:call-template name="affServiceEnLigneDroite" />
					</div>
				</div>
			</div>
		</xsl:if>

	</xsl:template>

</xsl:stylesheet>
