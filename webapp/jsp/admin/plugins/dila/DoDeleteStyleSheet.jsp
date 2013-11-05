<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="dilastylesheet" scope="session" class="fr.paris.lutece.plugins.dila.web.StyleSheetJspBean" />

<%
	dilastylesheet.init(request,  dilastylesheet.RIGHT_MANAGE_STYLESHEET  );
	response.sendRedirect( dilastylesheet.doDeleteStyleSheet( request ) );
%>