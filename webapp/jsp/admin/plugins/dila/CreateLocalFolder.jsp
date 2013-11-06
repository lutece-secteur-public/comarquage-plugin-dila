<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />
<jsp:useBean id="dilaFicheLocale" scope="session" class="fr.paris.lutece.plugins.dila.web.LocalCardJspBean" />
<%
    dilaFicheLocale.init( request, fr.paris.lutece.plugins.dila.web.LocalCardJspBean.RIGHT_MANAGE_LOCAL_CARD);
%>
<%=dilaFicheLocale.getCreateLocalFolder( request )%>
<%@ include file="../../AdminFooter.jsp" %>