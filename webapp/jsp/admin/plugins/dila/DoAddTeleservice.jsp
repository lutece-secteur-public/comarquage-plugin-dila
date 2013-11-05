<%@ page import="fr.paris.lutece.portal.web.pluginaction.IPluginActionResult"%>
<jsp:include page="../../AdminHeader.jsp" />
<jsp:useBean id="dilaDonneeComplementaire" scope="session" class="fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean" />
<%
    dilaDonneeComplementaire.init( request, fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean.RIGHT_MANAGE_COMPLEMENTARY_DATA);
%>
<%= dilaDonneeComplementaire.doAddTeleservice( request ) %>
<%@ include file="../../AdminFooter.jsp" %>