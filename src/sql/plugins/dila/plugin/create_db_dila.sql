--
-- Drop 
--
DROP TABLE IF EXISTS dila_type_contenu;
DROP TABLE IF EXISTS dila_stylesheet;
DROP TABLE IF EXISTS dila_action;
DROP TABLE IF EXISTS dila_audience;
DROP TABLE IF EXISTS dila_xml;
DROP TABLE IF EXISTS dila_type;
DROP TABLE IF EXISTS dila_local;
DROP TABLE IF EXISTS dila_dossier_local;
DROP TABLE IF EXISTS dila_lien_dossier_local;
DROP TABLE IF EXISTS dila_fiche_locale;
DROP TABLE IF EXISTS dila_chapitre_fiche_locale;
DROP TABLE IF EXISTS dila_donnees_complementaires;
DROP TABLE IF EXISTS dila_donnees_complementaires_teleservice;
DROP TABLE IF EXISTS dila_donnees_complementaires_savoir_plus;

--
-- Table structure for table dila_type_contenu
--
CREATE TABLE dila_type_contenu (
    id_type_contenu bigint NOT NULL,
    label character varying(255),
	CONSTRAINT dila_type_contenu_pk PRIMARY KEY (id_type_contenu)
);

--
-- Table structure for table dila_stylesheet
--
CREATE TABLE dila_stylesheet (
	id_stylesheet bigint NOT NULL,
	description varchar(255),
	file_name varchar(255),
	source long varbinary,
	fk_id_type_contenu bigint,
	CONSTRAINT dila_stylesheet_pk PRIMARY KEY (id_stylesheet),
);

--
-- Table structure for table form_action
--
CREATE TABLE dila_action (
	id_action int default 0 NOT NULL,
	name_key varchar(100) default NULL,
	description_key varchar(100) default NULL,
	action_url varchar(255) default NULL,
	icon_url varchar(255) default NULL,
	action_permission varchar(255) default NULL,
	CONSTRAINT dila_action_pk PRIMARY KEY (id_action)
);


--
-- Table structure for table dila_audience
--
CREATE TABLE dila_audience (
	id int NOT NULL,
	label varchar(20) NOT NULL,
	CONSTRAINT dila_audience_pk PRIMARY KEY (id)
);

--
-- Table structure for table dila_xml
--
CREATE TABLE dila_xml (
	id bigint NOT NULL,
	id_xml varchar(20) NOT NULL,
	title varchar(255) NOT NULL,
    type_resource varchar(255) NOT NULL,
	breadcrumb varchar(255) DEFAULT NULL,
	fk_audience_id int NOT NULL,
	date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	date_modification TIMESTAMP NULL,
	CONSTRAINT dila_xml_pk PRIMARY KEY (id),
	CONSTRAINT dila_xml_uq_xml UNIQUE (id_xml, fk_audience_id)
);

CREATE TABLE dila_type (
    id_type bigint NOT NULL,
    name_key varchar(100) default NULL,
    CONSTRAINT dila_type_pk PRIMARY KEY (id_type)
);

--
-- Table structure local
--
CREATE TABLE dila_local (
    id_local bigint NOT NULL,
    titre varchar(255) NOT NULL,
    auteur varchar(255) NOT NULL,
    chemin varchar(255) NOT NULL,
    xml LONG VARCHAR NOT NULL,
    fk_audience_id bigint NOT NULL,
    fk_type_id bigint NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT dila_local_pk PRIMARY KEY (id_local),
);

--
-- Table structure dossier local
--
CREATE TABLE dila_dossier_local (
    id_dossier_local bigint NOT NULL,
    id_theme_parent varchar(255) NOT NULL,
    id_dossier_frere varchar(255) DEFAULT NULL,
    position bigint DEFAULT NULL,
    presentation LONG VARCHAR,
    fk_id_local bigint NOT NULL,
    CONSTRAINT dila_dossier_local_pk PRIMARY KEY (id_dossier_local),
);

--
-- Table structure lien local
--
CREATE TABLE dila_lien_dossier_local (
    id_lien_dossier_local bigint NOT NULL,
    titre varchar(255) NOT NULL,
    position bigint NOT NULL,
    id_fiche varchar(255) NOT NULL,
    fk_dossier_local_id bigint NOT NULL,
    CONSTRAINT dila_lien_dossier_local_pk PRIMARY KEY (id_lien_dossier_local),
);

--
-- Table structure fiche locale
--
CREATE TABLE dila_fiche_locale (
    id_fiche_locale bigint NOT NULL,
    id_dossier_parent varchar(255) NOT NULL,
    id_fiche_soeur varchar(255) DEFAULT NULL,
    position bigint DEFAULT NULL,
    fk_id_local bigint NOT NULL,
    CONSTRAINT dila_fiche_locale_pk PRIMARY KEY (id_fiche_locale),
);

--
-- Table structure chapitre fiche locale
--
CREATE TABLE dila_chapitre_fiche_locale (
    id_chapitre_fiche_local bigint NOT NULL,
    titre varchar(255) NOT NULL,
    contenu LONG VARCHAR NOT NULL,
    position bigint NOT NULL,
    fk_fiche_locale_id bigint NOT NULL,
    CONSTRAINT dila_chapitre_fiche_locale_pk PRIMARY KEY (id_chapitre_fiche_local),
);

--
-- Table structure dila_donnees_complementaires
--
CREATE TABLE dila_donnees_complementaires (
    id bigint NOT NULL,
    bloc_bas LONG VARCHAR DEFAULT NULL,
    bloc_colonne LONG VARCHAR DEFAULT NULL,
    fk_fiche_nationale_id bigint NOT NULL,
    fk_audience_id bigint NOT NULL,
    CONSTRAINT dila_donnees_complementaires_pk PRIMARY KEY (id),
);

--
-- Table structure dila_donnees_complementaires_teleservice
--
CREATE TABLE dila_donnees_complementaires_teleservice (
    id bigint NOT NULL,
    titre varchar(255) NOT NULL, 
    url varchar(255) NOT NULL,
    position bigint NOT NULL,
    fk_donnees_complementaires_id bigint NOT NULL,
    CONSTRAINT dila_donnees_complementaires_teleservice_pk PRIMARY KEY (id),
);

--
-- Table structure dila_donnees_complementaires_savoir_plus
--
CREATE TABLE dila_donnees_complementaires_savoir_plus (
    id bigint NOT NULL,
    titre varchar(255) NOT NULL, 
    url varchar(255) NOT NULL,
    position bigint NOT NULL,
    fk_donnees_complementaires_id bigint NOT NULL,
    CONSTRAINT dila_donnees_complementaires_savoir_plus_pk PRIMARY KEY (id),
);

