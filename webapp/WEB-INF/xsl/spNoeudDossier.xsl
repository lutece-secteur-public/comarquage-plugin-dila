<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />

    <xsl:template match="Publication" mode="Noeud-dossier">
        <xsl:call-template name="getBarre10Themes" />
        <xsl:choose>
            <xsl:when test="$CATEGORIE = 'professionnels'">
                <xsl:call-template name="getFilDArianeOfPublicationEntreprise" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="FilDAriane" />
            </xsl:otherwise>
        </xsl:choose>
        <xsl:call-template name="getTitre" />
        <xsl:call-template name="affAvertissement" />
        <div class="span3 spPublicationMenuGauche" style="margin-left:0px;">
            <xsl:call-template name="displayHowToAndLocalCardsLinks" />
            <xsl:if test="count(VoirAussi) > 0">
                <div class="well">
                    <xsl:call-template name="affVoirAussiColonne">
                        <xsl:with-param name="titre">
                            <xsl:text>dossiers</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                </div>
            </xsl:if>
            <!-- <xsl:call-template name="createSommaireNoeud" /> -->
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
            <xsl:if test="$CATEGORIE != 'professionnels'">
                <xsl:apply-templates select="Introduction" />
            </xsl:if>
            <xsl:choose>
                <xsl:when test="count(SousDossier) > 0">
                    <div class="spNoeudDossierSousDossierMain">
                        <xsl:apply-templates select="SousDossier"
                            mode="Noeud-dossier" />
                    </div>
                </xsl:when>
                <xsl:when test="count(Fiche) > 0">
                    <div class="spNoeudDossierFiche well">
                        <ul class="spNoeudDossierFiche">
                            <xsl:apply-templates select="Fiche" mode="Noeud-dossier" />
                        </ul>
                    </div>
                </xsl:when>
            </xsl:choose>
            <xsl:if
                test="(count(ServiceEnLigne) + count(Partenaire) + count(OuSAdresser) + count(Reference) + count(QuestionReponse)+ count(PourEnSavoirPlus) + count(SiteInternetPublic) ) > 0">
                <div class="clearall">
                    <xsl:call-template name="affQuestionReponse" />
                    <xsl:call-template name="affServiceEnLigne" />
                    <xsl:call-template name="affPourEnSavoirPlus" />
                    <xsl:call-template name="affOuSAdresser" />
                    <xsl:call-template name="affReference" />
                    <xsl:call-template name="affPartenaire" />
                    <xsl:call-template name="affSiteInternetPublic" />
                </div>
            </xsl:if>
            <xsl:call-template name="ancreTop" />
        </div>

        <xsl:if
            test="(count(Actualite)+ count(InformationComplementaire) + count(Montant)) > 0">
            <div class="span3 spPublicationMenuDroite">
                <xsl:call-template name="affActualite" />
                <xsl:call-template name="affInformationComplementaire" />
                <xsl:call-template name="affMontant" />
            </div>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
