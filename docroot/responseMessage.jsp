<%@ page import="javax.portlet.*"%>

<%@ page import="com.liferay.portal.kernel.util.*"%>
<%@ page import="com.liferay.portal.util.*"%>
<%@ page import="com.liferay.portlet.*"%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>


<%@page import="org.msz.liferay.portlet.SzovetsegTVPortlet"%><portlet:defineObjects />

<p>
<%
  String responseMessage = renderRequest
      .getParameter(SzovetsegTVPortlet.RESPONSEMESSAGE);

  if (responseMessage == null)
    out.println("A m&ucirc;velet sikeresen v&eacute;grehajtva...");
  else
    out.println(responseMessage);
%>
</p>

<form action="<portlet:actionURL/>" method="POST"
	name="<portlet:namespace />fm">

<p>

<input
    type="hidden" id="command" name="command" value="">

<input type="submit" 
	<%String command = renderRequest
          .getParameter(SzovetsegTVPortlet.RESPONSE_COMMAND);

// System.out.println("responseMessage.jsp command: " + command);

      if (command != null)
      {
        out.println(" onClick=\"document.getElementById('command').value='"
            + command + "'\"");
        out.println(" value='"
            + renderRequest
                .getParameter(SzovetsegTVPortlet.RESPONSE_COMMAND_TITLE) + "'");
      }
      else
        out.println(" value='Vissza a f&ocirc;oldalra'");
      %>>

<%
  if (command != null)
    out.println(" <input type='submit' value='Vissza a f&ocirc;oldalra'>");
%> <input type="hidden" id="command" name="command" value=""></p>
</form>
