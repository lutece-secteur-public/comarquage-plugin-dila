<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
	exclude-result-prefixes="xsl dc">

	<xsl:output method="xml" encoding="ISO-8859-15"
		cdata-section-elements="script" indent="yes" />

	<xsl:template match="Publication" mode="Sous-theme">
		<xsl:call-template name="getBarre10Themes" />
		<xsl:apply-templates select="FilDAriane" />
		<xsl:call-template name="getTitre" />
		<div class="span3 spPublicationMenuGaucheForTheme" style="margin-left:0px;">
			<xsl:call-template name="displayHowToAndLocalCardsLinks" />
			<!-- <xsl:call-template name="createSommaireTheme" /> -->
			<xsl:if test="count(CommentFaireSi) > 0">
				<div class="well">
					<xsl:call-template name="affCommentFaireSi" />
				</div>
			</xsl:if>
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

		<xsl:if
			test="(count(Dossier) + count(QuestionReponse)+ count(CentreDeContact) + count(VoirAussi)) > 0">
			<div class="span6 spSousThemeDossier">
				<div class="well">
					<xsl:apply-templates select="Dossier" mode="Sous-theme" />
				</div>
				<xsl:call-template name="affVoirAussi">
					<xsl:with-param name="titre">
						<xsl:text>dossiers</xsl:text>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="affQuestionReponse" />
				<xsl:call-template name="affCentreDeContact" />
			</div>

			<div class="span3 spPublicationMenuDroiteForTheme">
				<xsl:call-template name="affActualite" />
				<xsl:call-template name="affServiceEnLigneDroite" />
				<xsl:call-template name="affSiteInternetPublic" />
			</div>
		</xsl:if>
		<xsl:if
			test="(count(Dossier) + count(QuestionReponse)+ count(CentreDeContact) + count(VoirAussi)) = 0">
			<div class="span6 spPublicationMenuForTheme">
				<xsl:call-template name="affActualite" />
				<xsl:call-template name="affServiceEnLigne" />
				<xsl:call-template name="affSiteInternetPublic" />
			</div>
		</xsl:if>
	</xsl:template>

	<xsl:template match="SousTheme" mode="Theme">
		<xsl:if test="count(Dossier) > 0">
			<div class="spThemeSousTheme">
				<div class="navbar">
					<div class="navbar-inner">
						<div class="pull-left">
							<xsl:attribute name="id">
                            <xsl:call-template name="createThemeSousThemeId" />
                        </xsl:attribute>
							<xsl:call-template name="imageOfATheme">
								<xsl:with-param name="id" select="//Publication/@ID" />
							</xsl:call-template>
						</div>
						<xsl:apply-templates select="Titre" mode="Theme" />
					</div>
				</div>
				<ul class="spThemeSousThemeDossier">
					<xsl:apply-templates select="Dossier" mode="Theme" />
				</ul>
			</div>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
