<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="dilaFicheLocale" scope="session" class="fr.paris.lutece.plugins.dila.web.LocalCardJspBean" />

<%
    dilaFicheLocale.init(request,  dilaFicheLocale.RIGHT_MANAGE_LOCAL_CARD  );
    response.sendRedirect( dilaFicheLocale.doDeleteLocalFolder( request ));
%>