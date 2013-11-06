<%@page import="fr.paris.lutece.portal.web.pluginaction.IPluginActionResult"%>
<jsp:useBean id="dilaFicheLocale" scope="session" class="fr.paris.lutece.plugins.dila.web.LocalCardJspBean" />
<%
    dilaFicheLocale.init( request, fr.paris.lutece.plugins.dila.web.LocalCardJspBean.RIGHT_MANAGE_LOCAL_CARD);
    IPluginActionResult result = dilaFicheLocale.doVerifyParentTheme( request );
    if ( result.getRedirect(  ) != null )
    {
        response.sendRedirect( result.getRedirect(  ) );
    }
    else if ( result.getHtmlContent(  ) != null )
    {
%>
        <%@ page errorPage="../../ErrorPage.jsp" %>
        <jsp:include page="../../AdminHeader.jsp" />

        <%= result.getHtmlContent(  ) %>

        <%@ include file="../../AdminFooter.jsp" %>
<%
    }
%>