<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-result-prefixes="xsl dc">

    <xsl:output method="xml" encoding="ISO-8859-15"
        cdata-section-elements="script" indent="yes" />
    <xsl:param name="CATEGORIE" />
    <xsl:param name="PICTOS" />
    <xsl:param name="XMLURL" />
    <xsl:param name="REFERER" />
    <xsl:param name="IMAGES" />
    <xsl:param name="HOW_TO_ID" />
    <xsl:param name="HOW_TO_TITLE" />
    <xsl:param name="FICHEID" />
    <!-- Affiche la barre des 10 thèmes principaux -->
    <xsl:template name="getBarre10Themes">
        <xsl:if test="$CATEGORIE = 'particuliers'">
            <xsl:variable name="file">
                <xsl:value-of select="$XMLURL" />
                <xsl:text>Themes.xml</xsl:text>
            </xsl:variable>
            <div class="span12 spBarre10Themes">
                <xsl:for-each select="document($file)/Noeud/Descendance/Fils">
                    <xsl:variable name="titre">
                        <xsl:value-of select="TitreContextuel" />
                    </xsl:variable>
                    <div class="span1 spBarre10ThemesFils">
                        <xsl:call-template name="getPublicationLink">
                            <xsl:with-param name="href">
                                <xsl:value-of select="@lien" />
                            </xsl:with-param>
                            <xsl:with-param name="title">
                                <xsl:value-of select="$titre" />
                            </xsl:with-param>
                            <xsl:with-param name="text">
                                <xsl:call-template name="imageOfATheme">
                                    <xsl:with-param name="id" select="@lien" />
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </xsl:for-each>
            </div>
        </xsl:if>
    </xsl:template>

    <!-- Création du lien vers une autre publication -->
    <xsl:template name="getPublicationLink">
        <xsl:param name="href" />
        <xsl:param name="title" />
        <xsl:param name="text" />
        <xsl:choose>
            <xsl:when test="$href = 'Theme'">
                <a href="{$REFERER}">
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:copy-of select="$text" />
                </a>
            </xsl:when>
            <xsl:when test="contains($href,'LieuLocal-')">
                <a href="{$REFERER}-{$href}">
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:value-of select="$text" />
                </a>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="linkTitle">
                    <xsl:call-template name="getTitle">
                        <xsl:with-param name="id" select="$href" />
                    </xsl:call-template>
                </xsl:variable>
                <a>
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:attribute name="href">
                        <xsl:value-of select="$REFERER" />
                        <xsl:value-of select="$href" />
                    </xsl:attribute>
                    <xsl:copy-of select="$text" />
                </a>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="getPublicationLinkBrand">
        <xsl:param name="href" />
        <xsl:param name="title" />
        <xsl:param name="text" />
        <xsl:choose>
            <xsl:when test="$href = 'Theme'">
                <a href="{$REFERER}" class="brand">
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:copy-of select="$text" />
                </a>
            </xsl:when>
            <xsl:when test="contains($href,'LieuLocal-')">
                <a href="{$REFERER}-{$href}" class="brand">
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:value-of select="$text" />
                </a>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="linkTitle">
                    <xsl:call-template name="getTitle">
                        <xsl:with-param name="id" select="$href" />
                    </xsl:call-template>
                </xsl:variable>
                <a>
                    <xsl:attribute name="title">
                        <xsl:value-of select="normalize-space($title)" />
                    </xsl:attribute>
                    <xsl:attribute name="href">
                        <xsl:value-of select="$REFERER" />
                        <xsl:value-of select="$href" />
                    </xsl:attribute>
                    <xsl:attribute name="class">
                        <xsl:text>brand</xsl:text>
                    </xsl:attribute>
                    <xsl:copy-of select="$text" />
                </a>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Création du lien vers un autre site -->
    <xsl:template name="getSiteLink">
        <xsl:param name="href" />
        <xsl:param name="title" />
        <xsl:param name="text" />
        <a rel="nofollow" class="spTexteLienExterne">
            <xsl:attribute name="title">
                <xsl:value-of select="normalize-space($title)" />
            </xsl:attribute>
            <xsl:attribute name="href">
                <xsl:value-of select="normalize-space($href)" />
            </xsl:attribute>
            <xsl:value-of select="normalize-space($text)" />
        </a>
    </xsl:template>

    <!-- Renvoie la description d'une publication -->
    <xsl:template name="getDescription">
        <xsl:param name="id" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$id" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <xsl:variable name="desc">
            <xsl:if test="file:exists(file:new($file))" xmlns:file="java.io.File">
                <xsl:value-of select="normalize-space(document($file)/*/dc:description)" />
            </xsl:if>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$desc = ''">
            </xsl:when>
            <xsl:when test="substring($desc,string-length($desc)-1) = '.'">
                <xsl:value-of select="$desc" />
                <xsl:text>..</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$desc" />
                <xsl:text>...</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Renvoie le titre d'une publication -->
    <xsl:template name="getTitle">
        <xsl:param name="id" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$id" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <xsl:if test="file:exists(file:new($file))" xmlns:file="java.io.File">
            <xsl:value-of select="document($file)/*/dc:title" />
        </xsl:if>
    </xsl:template>

    <!-- Renvoie la date d'une publication -->
    <xsl:template name="getDate">
        <xsl:variable name="date">
            <xsl:value-of select="substring(/Publication/dc:date,10,10)" />
        </xsl:variable>
        <xsl:variable name="year">
            <xsl:value-of select="substring($date,0,5)" />
        </xsl:variable>
        <xsl:variable name="monthInt">
            <xsl:value-of select="substring($date,6,2)" />
        </xsl:variable>
        <xsl:variable name="dayInt">
            <xsl:value-of select="substring($date,9,2)" />
        </xsl:variable>
        <div class="span12">
            <span style="font-style:italic;margin-bottom:5px;">
                <xsl:text>Mis à jour le </xsl:text>
                <xsl:choose>
                    <xsl:when test="contains($dayInt,'0')">
                        <xsl:value-of select="substring($dayInt,2)" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$dayInt" />
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:call-template name="frenchMonth">
                    <xsl:with-param name="month" select="$monthInt" />
                </xsl:call-template>
                <xsl:value-of select="$year" />
                <xsl:text> par </xsl:text>
                <xsl:call-template name="lowerCase">
                    <xsl:with-param name="string">
                        <xsl:choose>
                            <xsl:when test="/Publication/dc:contributor">
                                <xsl:value-of select="/Publication/dc:contributor" />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="/Publication/dc:creator" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>
            </span>
        </div>
    </xsl:template>

    <!-- Transforme une date au format RSS sous forme d'une chaîne de caractères -->
    <xsl:template name="transformRssDate">
        <xsl:param name="date" />
        <xsl:choose>
            <xsl:when test="contains($date,'Z')">
                <xsl:variable name="onlyDate">
                    <xsl:value-of select="substring-before($date,'T')" />
                </xsl:variable>
                <xsl:variable name="year">
                    <xsl:value-of select="substring-before($onlyDate,'-')" />
                </xsl:variable>
                <xsl:variable name="month">
                    <xsl:value-of
                        select="substring-before(substring-after($onlyDate,'-'),'-')" />
                </xsl:variable>
                <xsl:variable name="day">
                    <xsl:value-of select="substring-after(substring-after($onlyDate,'-'),'-')" />
                </xsl:variable>
                <xsl:value-of select="$day" />
                <xsl:choose>
                    <xsl:when test="$month ='01'">
                        <xsl:text> janvier </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='02'">
                        <xsl:text> février </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='03'">
                        <xsl:text> mars </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='04'">
                        <xsl:text> avril </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='05'">
                        <xsl:text> mai </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='06'">
                        <xsl:text> juin </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='07'">
                        <xsl:text> juillet </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='08'">
                        <xsl:text> août </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='09'">
                        <xsl:text> septembre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='10'">
                        <xsl:text> octobre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='11'">
                        <xsl:text> novembre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='12'">
                        <xsl:text> décembre </xsl:text>
                    </xsl:when>
                </xsl:choose>
                <xsl:value-of select="$year" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="onlyDate">
                    <xsl:value-of select="substring-after($date,', ')" />
                </xsl:variable>
                <xsl:variable name="day">
                    <xsl:value-of select="substring-before($onlyDate,' ')" />
                </xsl:variable>
                <xsl:variable name="month">
                    <xsl:value-of
                        select="substring-before(substring-after($onlyDate,' '),' ')" />
                </xsl:variable>
                <xsl:variable name="year">
                    <xsl:value-of
                        select="substring-before(substring-after(substring-after($onlyDate,' '),' '),' ')" />
                </xsl:variable>
                <xsl:value-of select="$day" />
                <xsl:choose>
                    <xsl:when test="$month ='Jan'">
                        <xsl:text> janvier </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Feb'">
                        <xsl:text> février </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Mar'">
                        <xsl:text> mars </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Apr'">
                        <xsl:text> avril </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='May'">
                        <xsl:text> mai </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Jun'">
                        <xsl:text> juin </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Jul'">
                        <xsl:text> juillet </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Aug'">
                        <xsl:text> août </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Sep'">
                        <xsl:text> septembre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Oct'">
                        <xsl:text> octobre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Nov'">
                        <xsl:text> novembre </xsl:text>
                    </xsl:when>
                    <xsl:when test="$month ='Dec'">
                        <xsl:text> décembre </xsl:text>
                    </xsl:when>
                </xsl:choose>
                <xsl:value-of select="$year" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Renvoie l'image associée à un thème principal -->
    <xsl:template name="imageOfATheme">
        <xsl:param name="id" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$id" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <img width="40" height="40">
            <xsl:attribute name="alt">
                 <xsl:if test="file:exists(file:new($file))"
                xmlns:file="java.io.File">
                     <xsl:value-of
                select="document($file)/Publication/dc:title" />
                 </xsl:if>
             </xsl:attribute>
            <xsl:attribute name="src">
                 <xsl:value-of select="$IMAGES" />
                 <xsl:choose>
                     <!-- Comment faire si -->
                     <xsl:when test="$id = 'F14128'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F14128.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F16225'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F16225.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F17556'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F17556.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F14485'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F14485.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F16507'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F16507.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F1700'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F1700.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F15913'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F15913.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F17904'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F17904.png</xsl:text></xsl:when>
                     <xsl:when test="$id = 'F17649'"><xsl:text>images/local/skin/plugins/dila/vdd/comment-faire-si/F17649.png</xsl:text></xsl:when>
                     <xsl:otherwise>
                         <!-- Thèmes principaux --> 
                         <xsl:text>images/local/skin/plugins/dila/vdd/</xsl:text><xsl:value-of
                select="$id" /><xsl:text>.png</xsl:text>
                     </xsl:otherwise>
                 </xsl:choose>
             </xsl:attribute>
        </img>
    </xsl:template>

    <!-- Renvoie l'image associée à une partie de la publication -->
    <xsl:template name="imageOfAPartie">
        <xsl:param name="nom" />
        <img width="40" height="40" class="entiteImageFloatLeft">
            <xsl:attribute name="alt">
                <xsl:value-of select="$nom" />
            </xsl:attribute>
            <xsl:attribute name="src">
                <xsl:value-of select="$IMAGES" />
                <xsl:value-of select="$nom" />
                <xsl:text>.jpg</xsl:text>
            </xsl:attribute>
        </img>
    </xsl:template>

    <xsl:template name="createSommaireTheme" mode="Theme">
        <div class="spPublicationSommaire">
            <h4>Sommaire</h4>
            <ul class="spPublicationSommaire">
                <xsl:if test="count(SousTheme) > 0">
                    <xsl:for-each select="SousTheme">
                        <xsl:variable name="title">
                            <xsl:value-of select="../dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:value-of select="Titre" />
                        </xsl:variable>
                        <xsl:variable name="nbDossiers">
                            <xsl:value-of select="count(Dossier)" />
                        </xsl:variable>
                        <xsl:if test="$nbDossiers > 0">
                            <li class="spPublicationSommaire">
                                <h5>
                                    <a title="{$title}">
                                        <xsl:attribute name="href">
                                            <xsl:value-of
                                            select="$REFERER"></xsl:value-of>
                                            <xsl:value-of
                                            select="/Publication/@ID"></xsl:value-of>
                                            <xsl:text>#</xsl:text>
                                            <xsl:call-template
                                            name="createThemeSousThemeId" />
                                        </xsl:attribute>
                                        <xsl:value-of select="Titre" />
                                    </a>
                                    <xsl:text> (</xsl:text>
                                    <xsl:value-of select="$nbDossiers" />
                                    <xsl:text>)</xsl:text>
                                </h5>
                            </li>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(Dossier) > 0">
                    <xsl:for-each select="Dossier">
                        <xsl:variable name="title">
                            <xsl:value-of select="../dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:value-of select="Titre" />
                        </xsl:variable>
                        <xsl:variable name="nbFiches">
                            <xsl:value-of select="count(Fiche)" />
                        </xsl:variable>
                        <xsl:if test="$nbFiches > 0">
                            <li class="spPublicationSommaire">
                                <h5>
                                    <a title="{$title}">
                                        <xsl:attribute name="href">
                                            <xsl:value-of
                                            select="$REFERER"></xsl:value-of>
                                            <xsl:value-of
                                            select="/Publication/@ID"></xsl:value-of>
                                            <xsl:text>#</xsl:text>
                                            <xsl:call-template
                                            name="createThemeDossierId" />
                                        </xsl:attribute>
                                        <xsl:value-of select="Titre" />
                                    </a>
                                    <xsl:text> (</xsl:text>
                                    <xsl:value-of select="$nbFiches" />
                                    <xsl:text>)</xsl:text>
                                </h5>
                            </li>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(Actualite)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Actualités</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-actualite</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Actualités</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(Actualite)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(ServiceEnLigne)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Services en ligne</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-service-en-ligne</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Services en ligne</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(ServiceEnLigne)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(QuestionReponse)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Questions - réponses</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-question-reponse</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Questions - réponses</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(QuestionReponse)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(CentreDeContact)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Centres d'appel et de contact</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-centre-de-contact</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Centres d'appel et de contact</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(CentreDeContact)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(VoirAussi)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Voir aussi</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-voir-aussi</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Voir aussi</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(VoirAussi/Dossier)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(SiteInternetPublic)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Sites internet publics</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-site-internet-public</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Sites internet publics</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(SiteInternetPublic)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
            </ul>
        </div>
    </xsl:template>

    <xsl:template name="createArborescenceTheme">
        <xsl:param name="ID" />
        <xsl:param name="SCRIPT" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$ID" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <div class="spPublicationArborescence">
            <h4>
                <xsl:choose>
                    <xsl:when test="$SCRIPT = 'true'">
                        <xsl:element name="span">
                            <xsl:attribute name="id">
                                <xsl:value-of select="$ID" />
                                <xsl:text>span</xsl:text>
                             </xsl:attribute>
                            <xsl:attribute name="onclick">
                                <xsl:text>toggleList('</xsl:text>
                                <xsl:value-of select="$ID" />
                                <xsl:text>');</xsl:text>
                            </xsl:attribute>
                            <xsl:attribute name="style"><xsl:text>cursor:pointer;</xsl:text></xsl:attribute>
                            <xsl:text>+</xsl:text>
                        </xsl:element>
                    </xsl:when>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="$ID = $FICHEID">
                        <xsl:value-of select="document($file)/Publication/dc:title" />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:element name="a">
                            <xsl:attribute name="href"><xsl:value-of
                                select="$REFERER" /><xsl:value-of select="$ID" /></xsl:attribute>
                            <xsl:attribute name="title"><xsl:value-of
                                select="document($file)/Publication/dc:title" /></xsl:attribute>
                            <xsl:value-of select="document($file)/Publication/dc:title" />
                        </xsl:element>
                    </xsl:otherwise>
                </xsl:choose>
            </h4>
            <xsl:variable name="hidden">
                <xsl:choose>
                    <xsl:when test="$SCRIPT = 'true'">
                        <xsl:text>display:none;list-style:none;cursor:pointer;</xsl:text>
                    </xsl:when>
                </xsl:choose>
            </xsl:variable>
            <ul class="spPublicationArborescence" id="{$ID}">
                <xsl:attribute name="style">
                    <xsl:value-of select="$hidden" />
                </xsl:attribute>
                <xsl:if test="count(document($file)/Publication/SousTheme) > 0">
                    <xsl:for-each select="document($file)/Publication/SousTheme">
                        <li class="spPublicationArborescence">
                            <xsl:call-template name="createArborescenceSousTheme">
                                <xsl:with-param name="ID">
                                    <xsl:value-of select="@ID" />
                                </xsl:with-param>
                                <xsl:with-param name="SCRIPT">
                                    <xsl:value-of select="$SCRIPT" />
                                </xsl:with-param>
                            </xsl:call-template>
                        </li>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(document($file)/Publication/Dossier) > 0">
                    <xsl:for-each select="document($file)/Publication/Dossier">
                        <li class="spPublicationArborescence">
                            <xsl:call-template name="createArborescenceDossier">
                                <xsl:with-param name="ID">
                                    <xsl:value-of select="@ID" />
                                </xsl:with-param>
                                <xsl:with-param name="SCRIPT">
                                    <xsl:value-of select="$SCRIPT" />
                                </xsl:with-param>
                            </xsl:call-template>
                        </li>
                    </xsl:for-each>
                </xsl:if>
            </ul>
        </div>
    </xsl:template>

    <xsl:template name="createArborescenceSousTheme">
        <xsl:param name="ID" />
        <xsl:param name="SCRIPT" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$ID" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <h5>
            <xsl:choose>
                <xsl:when test="$SCRIPT = 'true'">
                    <xsl:element name="span">
                        <xsl:attribute name="id">
                            <xsl:value-of select="$ID" />
                            <xsl:text>span</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="onclick">
                                <xsl:text>toggleList('</xsl:text>
                                <xsl:value-of select="$ID" />
                                <xsl:text>');</xsl:text>
                            </xsl:attribute>
                        <xsl:text>+</xsl:text>
                    </xsl:element>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="$ID = $FICHEID">
                    <xsl:value-of select="document($file)/Publication/dc:title" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="a">
                        <xsl:attribute name="href"><xsl:value-of
                            select="$REFERER" /><xsl:value-of select="$ID" /></xsl:attribute>
                        <xsl:attribute name="title"><xsl:value-of
                            select="document($file)/Publication/dc:title" /></xsl:attribute>
                        <xsl:value-of select="document($file)/Publication/dc:title" />
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>
        </h5>
        <xsl:if test="count(document($file)/Publication/Dossier) > 0">
            <xsl:variable name="hidden">
                <xsl:choose>
                    <xsl:when test="$SCRIPT = 'true'">
                        <xsl:text>display:none;list-style:none;cursor:pointer;</xsl:text>
                    </xsl:when>
                </xsl:choose>
            </xsl:variable>
            <ul class="spPublicationArborescence" id="{$ID}">
                <xsl:attribute name="style">
                    <xsl:value-of select="$hidden" />
                </xsl:attribute>
                <xsl:for-each select="document($file)/Publication/Dossier">
                    <xsl:choose>
                        <xsl:when test="number(@ID)">
                            <li class="spPublicationArborescence">
                                <xsl:choose>
                                    <xsl:when test="$ID = $FICHEID">
                                        <xsl:value-of select="Titre" />
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:element name="a">
                                            <xsl:attribute name="href"><xsl:value-of
                                                select="$REFERER" /><xsl:value-of select="@ID" /></xsl:attribute>
                                            <xsl:attribute name="title"><xsl:value-of
                                                select="Titre" /></xsl:attribute>
                                            <xsl:value-of select="Titre" />
                                        </xsl:element>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </li>
                        </xsl:when>
                        <xsl:otherwise>
                            <li class="spPublicationArborescence">
                                <xsl:call-template name="createArborescenceDossier">
                                    <xsl:with-param name="ID">
                                        <xsl:value-of select="@ID" />
                                    </xsl:with-param>
                                    <xsl:with-param name="SCRIPT">
                                        <xsl:value-of select="$SCRIPT" />
                                    </xsl:with-param>
                                </xsl:call-template>
                            </li>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </ul>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createArborescenceDossier">
        <xsl:param name="ID" />
        <xsl:param name="SCRIPT" />
        <xsl:variable name="file">
            <xsl:value-of select="$XMLURL" />
            <xsl:value-of select="$ID" />
            <xsl:text>.xml</xsl:text>
        </xsl:variable>
        <h6>
            <xsl:choose>
                <xsl:when test="$SCRIPT = 'true'">
                    <xsl:element name="span">
                        <xsl:attribute name="id">
                         <xsl:value-of select="$ID" />
                         <xsl:text>span</xsl:text>
                     </xsl:attribute>
                        <xsl:attribute name="onclick">
                                <xsl:text>toggleList('</xsl:text>
                                <xsl:value-of select="$ID" />
                                <xsl:text>');</xsl:text>
                            </xsl:attribute>
                        <xsl:text>+</xsl:text>
                    </xsl:element>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="$ID = $FICHEID">
                    <xsl:value-of select="document($file)/Publication/dc:title" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="a">
                        <xsl:attribute name="href"><xsl:value-of
                            select="$REFERER" /><xsl:value-of select="$ID" /></xsl:attribute>
                        <xsl:attribute name="title"><xsl:value-of
                            select="document($file)/Publication/dc:title" /></xsl:attribute>
                        <xsl:value-of select="document($file)/Publication/dc:title" />
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>
        </h6>
        <xsl:if
            test="count(document($file)/Publication/SousDossier) > 0 or count(document($file)/Publication/Fiche) > 0">
            <xsl:variable name="hidden">
                <xsl:choose>
                    <xsl:when test="$SCRIPT = 'true'">
                        <xsl:text>display:none;list-style:none;cursor:pointer;</xsl:text>
                    </xsl:when>
                </xsl:choose>
            </xsl:variable>
            <xsl:element name="ul">
                <xsl:attribute name="class"><xsl:text>spPublicationArborescence</xsl:text></xsl:attribute>
                <xsl:attribute name="id"><xsl:value-of select="$ID" /></xsl:attribute>
                <xsl:attribute name="style"><xsl:value-of select="$hidden" /></xsl:attribute>
                <xsl:if test="count(document($file)/Publication/SousDossier) > 0">
                    <xsl:for-each select="document($file)/Publication/SousDossier">
                        <li class="spPublicationArborescence">
                            <h7>
                                <xsl:value-of select="Titre" />
                            </h7>
                            <xsl:if test="count(Fiche) > 0">
                                <ul class="spPublicationArborescence">
                                    <xsl:for-each select="Fiche">
                                        <li class="spPublicationArborescence">
                                            <xsl:choose>
                                                <xsl:when test="@ID = $FICHEID">
                                                    <strong>
                                                        <xsl:value-of select="current()" />
                                                    </strong>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <xsl:element name="a">
                                                        <xsl:attribute name="href"><xsl:value-of
                                                            select="$REFERER" /><xsl:value-of select="@ID" /></xsl:attribute>
                                                        <xsl:attribute name="title"><xsl:value-of
                                                            select="current()" /></xsl:attribute>
                                                        <xsl:value-of select="current()" />
                                                    </xsl:element>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </li>
                                    </xsl:for-each>
                                </ul>
                            </xsl:if>
                        </li>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(document($file)/Publication/Fiche) > 0">
                    <xsl:for-each select="document($file)/Publication/Fiche">
                        <li class="spPublicationArborescence">
                            <xsl:choose>
                                <xsl:when test="$ID = $FICHEID">
                                    <xsl:value-of select="current()" />
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:element name="a">
                                        <xsl:attribute name="href"><xsl:value-of
                                            select="$REFERER" /><xsl:value-of select="@ID" /></xsl:attribute>
                                        <xsl:attribute name="title"><xsl:value-of
                                            select="current()" /></xsl:attribute>
                                        <xsl:value-of select="current()" />
                                    </xsl:element>
                                </xsl:otherwise>
                            </xsl:choose>
                        </li>
                    </xsl:for-each>
                </xsl:if>
            </xsl:element>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createSommaireNoeud" mode="Noeud-dossier">
        <div class="spPublicationSommaire">
            <h4>Sommaire</h4>
            <ul class="spPublicationSommaire">
                <xsl:if test="Texte">
                    <xsl:for-each select="Texte/Chapitre">
                        <xsl:if test="Titre">
                            <xsl:variable name="title">
                                <xsl:value-of select="../../dc:title" />
                                <xsl:value-of select="$sepFilDAriane" />
                                <xsl:value-of select="normalize-space(Titre)" />
                            </xsl:variable>
                            <li class="spPublicationSommaire">
                                <h5>
                                    <a title="{$title}">
                                        <xsl:attribute name="href">
                                            <xsl:value-of
                                            select="$REFERER"></xsl:value-of>
                                            <xsl:value-of
                                            select="/Publication/@ID"></xsl:value-of>
                                            <xsl:text>#</xsl:text>
                                            <xsl:call-template
                                            name="createChapitreId" />
                                        </xsl:attribute>
                                        <xsl:value-of select="Titre" />
                                    </a>
                                </h5>
                            </li>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(SousDossier) > 0">
                    <xsl:for-each select="SousDossier">
                        <xsl:variable name="title">
                            <xsl:value-of select="../dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:value-of select="Titre" />
                        </xsl:variable>
                        <xsl:variable name="nbFiches">
                            <xsl:value-of select="count(Fiche)" />
                        </xsl:variable>
                        <xsl:if test="$nbFiches > 0">
                            <li class="spPublicationSommaire">
                                <h5>
                                    <a title="{$title}">
                                        <xsl:attribute name="href">
                                            <xsl:value-of
                                            select="$REFERER"></xsl:value-of>
                                            <xsl:value-of
                                            select="/Publication/@ID"></xsl:value-of>
                                            <xsl:text>#</xsl:text>
                                            <xsl:call-template
                                            name="createSousDossierId" />
                                        </xsl:attribute>
                                        <xsl:value-of select="Titre" />
                                    </a>
                                    <xsl:text> (</xsl:text>
                                    <xsl:value-of select="$nbFiches" />
                                    <xsl:text>)</xsl:text>
                                </h5>
                            </li>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(Fiche) > 0">
                    <xsl:for-each select="Fiche">
                        <xsl:variable name="title">
                            <xsl:value-of select="../dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:value-of select="normalize-space(text())" />
                        </xsl:variable>
                        <li class="spPublicationSommaire">
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
                    </xsl:for-each>
                </xsl:if>
                <xsl:if test="count(OuSAdresser)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Ou s'adresser</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-ou-sadresser</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Ou s'adresser</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(OuSAdresser)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(Reference)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Références</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-reference</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Références</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(Reference)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(Partenaire)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Partenaires</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-partenaire</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Partenaires</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(Partenaire)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(Actualite)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Actualités</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-actualite</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Actualités</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(Actualite)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(InformationComplementaire)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Informations complémentaires</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-information-complementaire</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Informations complémentaires</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(InformationComplementaire)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(Montant)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Montants</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-montant</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Montants</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(Montant)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(ServiceEnLigne)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Services en ligne</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-service-en-ligne</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Services en ligne</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(ServiceEnLigne)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(QuestionReponse)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Questions - réponses</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-question-reponse</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Questions - réponses</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(QuestionReponse)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(PourEnSavoirPlus)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Pour en savoir plus</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-pour-en-savoir-plus</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Pour en savoir plus</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(PourEnSavoirPlus)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
                <xsl:if test="count(SiteInternetPublic)">
                    <xsl:variable name="title">
                        <xsl:value-of select="dc:title" />
                        <xsl:value-of select="$sepFilDAriane" />
                        <xsl:text>Sites internet publics</xsl:text>
                    </xsl:variable>
                    <li class="spPublicationSommaire">
                        <h5>
                            <a title="{$title}">
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                                    select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-site-internet-public</xsl:text>
                                </xsl:attribute>
                                <xsl:text>Sites internet publics</xsl:text>
                            </a>
                            <xsl:text> (</xsl:text>
                            <xsl:value-of select="count(SiteInternetPublic)" />
                            <xsl:text>)</xsl:text>
                        </h5>
                    </li>
                </xsl:if>
            </ul>
        </div>
    </xsl:template>

    <xsl:template name="createSommaireFiche" mode="Fiche">
        <xsl:if
            test="(count(Texte/Chapitre/Titre)+count(OuSAdresser)+count(Reference)+count(Actualite)+count(InformationComplementaire)+count(Montant)+count(ServiceEnLigne)+count(QuestionReponse)+count(EnSavoirPlus)+count(SiteInternetPublic)) > 0">
            <div class="spPublicationSommaire">
                <h4>Sommaire</h4>
                <ul class="spPublicationSommaire">
                    <xsl:for-each select="Texte/Chapitre">
                        <xsl:if test="Titre">
                            <xsl:variable name="title">
                                <xsl:value-of select="../../dc:title" />
                                <xsl:value-of select="$sepFilDAriane" />
                                <xsl:value-of select="normalize-space(Titre)" />
                            </xsl:variable>
                            <li class="spPublicationSommaire">
                                <h5>
                                    <a title="{$title}">
                                        <xsl:attribute name="href">
                                            <xsl:value-of
                                            select="$REFERER"></xsl:value-of>
                                            <xsl:value-of
                                            select="/Publication/@ID"></xsl:value-of>
                                            <xsl:text>#</xsl:text>
                                            <xsl:call-template
                                            name="createChapitreId" />
                                        </xsl:attribute>
                                        <xsl:value-of select="Titre" />
                                    </a>
                                </h5>
                            </li>
                        </xsl:if>
                    </xsl:for-each>
                    <xsl:if test="count(OuSAdresser)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Ou s'adresser</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-ou-sadresser</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Ou s'adresser</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(OuSAdresser)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(Reference)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Références</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-reference</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Références</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(Reference)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(Actualite)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Actualités</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-actualite</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Actualités</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(Actualite)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(InformationComplementaire)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Informations complémentaires</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-information-complementaire</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Informations complémentaires</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(InformationComplementaire)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(Montant)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Montants</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-montant</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Montants</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(Montant)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(ServiceEnLigne)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Services en ligne</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-service-en-ligne</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Services en ligne</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(ServiceEnLigne)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(QuestionReponse)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Questions - réponses</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-question-reponse</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Questions - réponses</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(QuestionReponse)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(PourEnSavoirPlus)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Pour en savoir plus</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-pour-en-savoir-plus</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Pour en savoir plus</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(PourEnSavoirPlus)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                    <xsl:if test="count(SiteInternetPublic)">
                        <xsl:variable name="title">
                            <xsl:value-of select="dc:title" />
                            <xsl:value-of select="$sepFilDAriane" />
                            <xsl:text>Sites internet publics</xsl:text>
                        </xsl:variable>
                        <li class="spPublicationSommaire">
                            <h5>
                                <a title="{$title}">
                                    <xsl:attribute name="href">
                                       <xsl:value-of
                                        select="$REFERER"></xsl:value-of><xsl:value-of
                                        select="/Publication/@ID"></xsl:value-of><xsl:text>#sp-site-internet-public</xsl:text>
                                    </xsl:attribute>
                                    <xsl:text>Sites internet publics</xsl:text>
                                </a>
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="count(SiteInternetPublic)" />
                                <xsl:text>)</xsl:text>
                            </h5>
                        </li>
                    </xsl:if>
                </ul>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="createThemeSousThemeId">
        <xsl:text>sp-theme-sous-theme-</xsl:text>
        <xsl:call-template name="lowerCase">
            <xsl:with-param name="string">
                <xsl:call-template name="textWithoutAccent">
                    <xsl:with-param name="string">
                        <xsl:value-of select="Titre" />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="createThemeDossierId">
        <xsl:text>sp-theme-dossier-</xsl:text>
        <xsl:call-template name="lowerCase">
            <xsl:with-param name="string">
                <xsl:call-template name="textWithoutAccent">
                    <xsl:with-param name="string">
                        <xsl:value-of select="Titre" />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="createSousDossierId">
        <xsl:text>sp-noeud-dossier-</xsl:text>
        <xsl:call-template name="lowerCase">
            <xsl:with-param name="string">
                <xsl:call-template name="textWithoutAccent">
                    <xsl:with-param name="string">
                        <xsl:value-of select="Titre" />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="createChapitreId">
        <xsl:text>sp-chapitre-</xsl:text>
        <xsl:call-template name="lowerCase">
            <xsl:with-param name="string">
                <xsl:call-template name="textWithoutAccent">
                    <xsl:with-param name="string">
                        <xsl:value-of select="Titre/Paragraphe" />
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="textWithoutAccent">
        <xsl:param name="string" />
        <xsl:variable name="stringFrom">
            ÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ
        </xsl:variable>
        <xsl:variable name="stringTo">
            -aaaaaaaaaaaaooooooooooooeeeeeeeecciiiiiiiiuuuuuuuuynn
        </xsl:variable>
        <xsl:variable name="stringDel">
            "?',:%&#8217;&#8211;
        </xsl:variable>
        <xsl:variable name="twastring">
            <xsl:value-of
                select="translate(translate($string,$stringDel,''),$stringFrom,$stringTo)" />
        </xsl:variable>
        <xsl:call-template name="string-replace">
            <xsl:with-param name="string">
                <xsl:value-of select="$twastring" />
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="string-replace">
        <xsl:param name="string" />
        <xsl:variable name="nstring">
            <xsl:value-of select="normalize-space($string)" />
        </xsl:variable>
        <xsl:value-of select="$nstring" />
    </xsl:template>

    <xsl:template name="upperCase">
        <xsl:param name="string" />
        <xsl:value-of
            select="translate($string,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
    </xsl:template>

    <xsl:template name="lowerCase">
        <xsl:param name="string" />
        <xsl:value-of
            select="translate($string,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')" />
    </xsl:template>

    <xsl:template name="getMAJDate">
        <xsl:variable name="date">
            <xsl:value-of select="//*/dc:date" />
        </xsl:variable>
        <xsl:text>Mis à jour le </xsl:text>
        <xsl:call-template name="transformRssDate">
            <xsl:with-param name="date">
                <xsl:value-of select="substring-after($date,' ')" />
                <xsl:text>TZ</xsl:text>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="getMAJDateContributor">
        <xsl:call-template name="getMAJDate" />
        <xsl:text> - </xsl:text>
        <xsl:value-of select="//*/dc:contributor" />
    </xsl:template>

    <xsl:template name="ancreTop">
        <div class="clearall">
            <br class="clearall" />
            <a title="Retour vers le haut de la page">
                <xsl:attribute name="href">
                 <xsl:value-of select="$REFERER"></xsl:value-of><xsl:value-of
                    select="/Publication/@ID"></xsl:value-of><xsl:text>#top</xsl:text>
             </xsl:attribute>
                <img class="entiteImageFloatRight" alt="Retour vers le haut de la page"
                    width="32" height="32">
                    <xsl:attribute name="src">
                        <xsl:value-of select="$IMAGES" />
                        <xsl:text>images/local/skin/plugins/dila/fleche.png</xsl:text>
                    </xsl:attribute>
                </img>
            </a>
        </div>
    </xsl:template>

    <xsl:template name="createDossierAzId">
        <xsl:text>sp-dossieraz-</xsl:text>
        <xsl:call-template name="lowerCase">
            <xsl:with-param name="string">
                <xsl:value-of select="Titre" />
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="frenchMonth">
        <xsl:param name="month" />
        <xsl:choose>
            <xsl:when test="$month = '01'">
                <xsl:text> janvier </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '02'">
                <xsl:text> février </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '03'">
                <xsl:text> mars </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '04'">
                <xsl:text> avril </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '05'">
                <xsl:text> mai </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '06'">
                <xsl:text> juin </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '07'">
                <xsl:text> juillet </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '08'">
                <xsl:text> août </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '09'">
                <xsl:text> septembre </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '10'">
                <xsl:text> octobre </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '11'">
                <xsl:text> novembre </xsl:text>
            </xsl:when>
            <xsl:when test="$month = '12'">
                <xsl:text> décembre </xsl:text>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- Fiche -->
    <xsl:template name="commentFaire">
        <li>
            <a>
                <xsl:attribute name="href">
                    <xsl:value-of select="$REFERER" />
                    <xsl:value-of select="@ID" />
                </xsl:attribute>
                <xsl:value-of select="text()" />
            </a>
        </li>
    </xsl:template>

    <!-- Fiche -->
    <xsl:template name="ficheLocale">
        <li>
            <a>
                <xsl:attribute name="href">
                    <xsl:value-of select="$REFERER" />
                    <xsl:value-of select="@ID" />
                </xsl:attribute>
                <xsl:value-of select="text()" />
            </a>
        </li>
    </xsl:template>

    <!-- How to and local cards links -->
    <xsl:template name="displayHowToAndLocalCardsLinks">
        <div class="well">
            <ul>
                <li>
                    <xsl:element name="a">
                        <xsl:attribute name="href">
                                        <xsl:value-of
                            select="$REFERER" />
                                        <xsl:value-of
                            select="$HOW_TO_ID" />
                                    </xsl:attribute>
                        <xsl:value-of select="$HOW_TO_TITLE" />
                    </xsl:element>
                </li>
                <li>
                    <xsl:element name="a">
                        <xsl:attribute name="href">
                                        <xsl:text>jsp/site/Portal.jsp?page=dilaLocal&amp;categorie=</xsl:text>
                                        <xsl:value-of
                            select="$CATEGORIE"></xsl:value-of>
                                    </xsl:attribute>
                        Fiches locales
                    </xsl:element>
                </li>
            </ul>
        </div>
    </xsl:template>

</xsl:stylesheet>
