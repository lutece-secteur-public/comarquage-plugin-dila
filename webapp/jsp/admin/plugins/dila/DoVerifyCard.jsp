<%@page import="fr.paris.lutece.portal.web.pluginaction.IPluginActionResult"%>
<jsp:useBean id="dilaDonneeComplementaire" scope="session" class="fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean" />
<%
    dilaDonneeComplementaire.init( request, fr.paris.lutece.plugins.dila.web.ComplementaryDataJspBean.RIGHT_MANAGE_COMPLEMENTARY_DATA);
    IPluginActionResult result = dilaDonneeComplementaire.doVerifyCard( request );
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