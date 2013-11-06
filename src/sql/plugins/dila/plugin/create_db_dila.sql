--
-- Drop 
--
DROP TABLE IF EXISTS dila_content_type;
DROP TABLE IF EXISTS dila_stylesheet;
DROP TABLE IF EXISTS dila_action;
DROP TABLE IF EXISTS dila_audience;
DROP TABLE IF EXISTS dila_xml;
DROP TABLE IF EXISTS dila_type;
DROP TABLE IF EXISTS dila_local;
DROP TABLE IF EXISTS dila_local_folder;
DROP TABLE IF EXISTS dila_local_folder_link;
DROP TABLE IF EXISTS dila_local_card;
DROP TABLE IF EXISTS dila_local_card_chapter;
DROP TABLE IF EXISTS dila_complementary_data;
DROP TABLE IF EXISTS dila_complementary_data_teleservice;
DROP TABLE IF EXISTS dila_complementary_data_learn_more;

--
-- Table structure for table dila_content_type
--
CREATE TABLE dila_content_type (
    id bigint NOT NULL,
    label character varying(255),
	CONSTRAINT dila_content_type_pk PRIMARY KEY (id)
);

--
-- Table structure for table dila_stylesheet
--
CREATE TABLE dila_stylesheet (
	id_stylesheet bigint NOT NULL,
	description varchar(255),
	file_name varchar(255),
	source long varbinary,
	content_type_id bigint,
	CONSTRAINT dila_stylesheet_pk PRIMARY KEY (id_stylesheet)
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
	audience_id int NOT NULL,
	date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	date_modification TIMESTAMP NULL,
	CONSTRAINT dila_xml_pk PRIMARY KEY (id),
	CONSTRAINT dila_xml_uq_xml UNIQUE (id_xml, audience_id)
);

CREATE TABLE dila_type (
    id bigint NOT NULL,
    name_key varchar(100) default NULL,
    CONSTRAINT dila_type_pk PRIMARY KEY (id)
);

--
-- Table structure local
--
CREATE TABLE dila_local (
    id bigint NOT NULL,
    title varchar(255) NOT NULL,
    author varchar(255) NOT NULL,
    path varchar(255) NOT NULL,
    xml LONG VARCHAR NOT NULL,
    audience_id bigint NOT NULL,
    type_id bigint NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT dila_local_pk PRIMARY KEY (id)
);

--
-- Table structure local folder
--
CREATE TABLE dila_local_folder(
    id bigint NOT NULL,
    parent_theme_id varchar(255) NOT NULL,
    sibling_folder_id varchar(255) DEFAULT NULL,
    position bigint DEFAULT NULL,
    presentation LONG VARCHAR,
    local_id bigint NOT NULL,
    CONSTRAINT dila_local_folder_pk PRIMARY KEY (id)
);

--
-- Table structure local folder link
--
CREATE TABLE dila_local_folder_link (
    id bigint NOT NULL,
    title varchar(255) NOT NULL,
    position bigint NOT NULL,
    card_id varchar(255) NOT NULL,
    local_folder_id bigint NOT NULL,
    CONSTRAINT dila_local_folder_link_pk PRIMARY KEY (id)
);

--
-- Table structure local card
--
CREATE TABLE dila_local_card (
    id bigint NOT NULL,
    parent_folder_id varchar(255) NOT NULL,
    sibling_card_id varchar(255) DEFAULT NULL,
    position bigint DEFAULT NULL,
    local_id bigint NOT NULL,
    CONSTRAINT dila_local_card_pk PRIMARY KEY (id)
);

--
-- Table structure local card chapter
--
CREATE TABLE dila_local_card_chapter (
    id bigint NOT NULL,
    title varchar(255) NOT NULL,
    content LONG VARCHAR NOT NULL,
    position bigint NOT NULL,
    local_card_id bigint NOT NULL,
    CONSTRAINT dila_local_card_chapter_pk PRIMARY KEY (id)
);

--
-- Table structure dila_complementary_data
--
CREATE TABLE dila_complementary_data (
    id bigint NOT NULL,
    bottom_block LONG VARCHAR DEFAULT NULL,
    column_block LONG VARCHAR DEFAULT NULL,
    xml_id bigint NOT NULL,
    audience_id bigint NOT NULL,
    CONSTRAINT dila_complementary_data_pk PRIMARY KEY (id)
);

--
-- Table structure dila_complementary_data_teleservice
--
CREATE TABLE dila_complementary_data_teleservice (
    id bigint NOT NULL,
    title varchar(255) NOT NULL, 
    url varchar(255) NOT NULL,
    position bigint NOT NULL,
    complementary_data_id bigint NOT NULL,
    CONSTRAINT dila_complementary_data_teleservice_pk PRIMARY KEY (id)
);

--
-- Table structure dila_complementary_data_learn_more
--
CREATE TABLE dila_complementary_data_learn_more (
    id bigint NOT NULL,
    title varchar(255) NOT NULL, 
    url varchar(255) NOT NULL,
    position bigint NOT NULL,
    complementary_data_id bigint NOT NULL,
    CONSTRAINT dila_complementary_data_learn_more_pk PRIMARY KEY (id)
);

