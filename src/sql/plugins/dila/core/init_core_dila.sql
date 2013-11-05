-- add the admin right for the module fdw wizard
INSERT INTO core_admin_right (id_right, name, level_right, admin_url, description, is_updatable, plugin_name, id_feature_group, icon_url, documentation_url, id_order)
VALUES ('DILA_COMPLEMENTARY_DATA_MANAGEMENT', 'dila.adminFeature.donnees-complementaires_management.name', 2, 'jsp/admin/plugins/dila/ManageDonneeComplementaire.jsp', 'dila.adminFeature.donnees-complementaires_management.description', '0', 'dila', NULL, 'images/admin/skin/plugins/dila/manage_complementary_data.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=dila', '4');
INSERT INTO core_admin_right (id_right, name, level_right, admin_url, description, is_updatable, plugin_name, id_feature_group, icon_url, documentation_url, id_order)
 VALUES ('DILA_STYLESHEET_MANAGEMENT', 'dila.adminFeature.stylesheetmanagement.name', 2, 'jsp/admin/plugins/dila/ManageStyleSheets.jsp', 'dila.adminFeature.stylesheetmanagement.description', 0, 'dila', NULL, 'images/admin/skin/plugins/dila/manage_stylesheets.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=dila', 1);
INSERT INTO core_admin_right (id_right, name, level_right, admin_url, description, is_updatable, plugin_name, id_feature_group, icon_url, documentation_url, id_order)
 VALUES ('DILA_LOCAL_CARD_MANAGEMENT', 'dila.adminFeature.fichelocale.name', 2, 'jsp/admin/plugins/dila/ManageFicheLocale.jsp', 'dila.adminFeature.fichelocale.description', 0, 'dila', NULL, 'images/admin/skin/plugins/dila/manage_local_card.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=dila', 1);

-- add the role for admin for the module fdw wizard
INSERT INTO core_user_right VALUES ('DILA_COMPLEMENTARY_DATA_MANAGEMENT',1);
INSERT INTO core_user_right VALUES ('DILA_STYLESHEET_MANAGEMENT',1);
INSERT INTO core_user_right VALUES ('DILA_LOCAL_CARD_MANAGEMENT',1);