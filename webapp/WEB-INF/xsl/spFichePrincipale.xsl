<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:import href="spCommon.xsl" />
    <xsl:import href="spFilDAriane.xsl" />
    <xsl:import href="spTitre.xsl" />
    <xsl:import href="spTexte.xsl" />
    <xsl:import href="spVoirAussi.xsl" />
    <xsl:import href="spAvertissement.xsl" />
    <xsl:import href="spIntroduction.xsl" />
    <xsl:import href="spOuSAdresser.xsl" />
    <xsl:import href="spReference.xsl" />
    <xsl:import href="spActualite.xsl" />
    <xsl:import href="spInformationComplementaire.xsl" />
    <xsl:import href="spMontant.xsl" />
    <xsl:import href="spServiceEnLigne.xsl" />
    <xsl:import href="spQuestionReponse.xsl" />
    <xsl:import href="spPourEnSavoirPlus.xsl" />
    <xsl:import href="spSiteInternetPublic.xsl" />


    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <!-- Publication -->
    <xsl:template match="/Publication">
        <xsl:call-template name="getBarre10Themes" />
        <xsl:apply-templates select="FilDAriane" />
        <xsl:call-template name="getTitre" />
        <xsl:call-template name="getDate" />
        <xsl:call-template name="affAvertissement" />
        <xsl:if
            test="(dc:type ='Fiche') or (dc:type ='Comment faire si') or (dc:type ='Question-réponse')">
            <xsl:choose>
                <xsl:when test="$CATEGORIE = 'professionnels'">
                    <div class="span3 spPublicationMenuGauche" style="margin-left: 0px;">
                        <xsl:call-template name="displayHowToAndLocalCardsLinks" />
                        <xsl:if
                            test="(count(Texte/Chapitre/Titre)+count(OuSAdresser)+count(Reference)+count(Actualite)+count(InformationComplementaire)+count(Montant)+count(ServiceEnLigne)+count(QuestionReponse)+count(EnSavoirPlus)+count(SiteInternetPublic)) > 0">
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
                        </xsl:if>
                    </div>
                </xsl:when>
                <xsl:otherwise>
                    <div class="span3 spPublicationMenuGauche" style="margin-left: 0px;">
                        <xsl:call-template name="displayHowToAndLocalCardsLinks" />
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
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
        <div class="span6">
            <div class="well">
                <xsl:apply-templates select="Introduction" />
                <xsl:apply-templates select="Texte" />
			</div>
                <xsl:if
                    test="(count(OuSAdresser)+ count(Reference) + count(Montant) + count(QuestionReponse)+ count(PourEnSavoirPlus) + count(SiteInternetPublic) + count(BlocBas)) > 0">
					<xsl:call-template name="affOuSAdresser" />
					<xsl:call-template name="affReference" />
					<xsl:call-template name="affMontant" />
					<xsl:call-template name="affQuestionReponse" />
					<xsl:call-template name="affPourEnSavoirPlus" />
					<xsl:call-template name="affSiteInternetPublic" />
					<xsl:call-template name="affBlocBas" />
                </xsl:if>
                <xsl:call-template name="ancreTop" />
        </div>
        <xsl:if
            test="(count(Actualite)+ count(InformationComplementaire) + count(ServiceEnLigne) + count(BlocColonne)) > 0">
            <div class="span3">
				<div class="spPublicationMenuDroite">
				    <xsl:call-template name="affActualite" />
				    <xsl:call-template name="affInformationComplementaire" />
				    <xsl:call-template name="affServiceEnLigneDroite" />
				    <xsl:call-template name="affBlocColonne" />
				</div>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="affBlocBas" mode="Publication">
        <xsl:if test="count(BlocBas) > 0">
            <div class="spPublicationBB" id="sp-bloc-bas">
                <xsl:value-of select="BlocBas" disable-output-escaping="yes" />
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="affBlocColonne" mode="Publication">
        <xsl:if test="count(BlocColonne) > 0">
            <div class="well" id="sp-bloc-colonne">
                <xsl:value-of select="BlocColonne"
                    disable-output-escaping="yes" />
            </div>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
