<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />
<jsp:useBean id="dilaDonneeComplementaire" scope="session" class="fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean" />
<%
    dilaDonneeComplementaire.init( request, fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean.RIGHT_MANAGE_COMPLEMENTARY_DATA);
%>
<%=dilaDonneeComplementaire.getCreateComplementaryData( request )%>
<%@ include file="../../AdminFooter.jsp" %>