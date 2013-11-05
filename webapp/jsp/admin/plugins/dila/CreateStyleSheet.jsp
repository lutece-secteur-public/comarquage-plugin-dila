<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="dilastylesheet" scope="session" class="fr.paris.lutece.plugins.dila.web.StyleSheetJspBean" />

<% dilastylesheet.init(request,  dilastylesheet.RIGHT_MANAGE_STYLESHEET  ) ; %>
<%= dilastylesheet.getSaveStyleSheet ( request )%>

<%@ include file="../../AdminFooter.jsp" %>