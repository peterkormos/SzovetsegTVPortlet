<%@ page import="javax.portlet.*"%>

<%@page import="java.util.*"%>

<%@ page import="com.liferay.portal.kernel.util.*"%>
<%@ page import="com.liferay.portal.util.*"%>
<%@ page import="com.liferay.portlet.*"%>

<%@ page import="org.msz.liferay.portlet.SzovetsegTVPortlet"%>
<%@ page import="org.msz.liferay.portlet.datatype.*"%>
<%@ page import="org.msz.liferay.portlet.util.*"%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>



<portlet:defineObjects />

<!-- 
  String currentURL = PortalUtil.getCurrentURL(request);

  PortletPreferences preferences = renderRequest.getPreferences();

  String portletResource = ParamUtil.getString(request, "portletResource");

  if (Validator.isNotNull(portletResource))
  {
    preferences = PortletPreferencesFactoryUtil.getPortletSetup(request,
        portletResource);
  }

  out.println("successURL: "
      + preferences.getValue("successURL", StringPool.BLANK));
  out.println("<p>"
      + PortletUtils.getPortletSession(renderRequest).getAttribute(
          "actionRequest"));
 -->


<form action="<portlet:actionURL/>" method="POST"
	name="<portlet:namespace />fm">
<p><img
	src="http://szovetsegtv.hu/sites/all/themes/marinelli/sztv_atlatszo.png"
	align="right"></p>
<p><input type="submit" value="<%= SzovetsegTVPortlet.SHOW_TIMEFRAME_TILE %>"
	onClick="document.getElementById('command').value='<%= SzovetsegTVPortlet.SHOW_TIMEFRAME %>'">
<input type="submit" value="<%= SzovetsegTVPortlet.SHOW_PROGRAM_TILE %>"
    onClick="document.getElementById('command').value='<%= SzovetsegTVPortlet.SHOW_PROGRAM %>'"> 
<input type="submit" value="<%= SzovetsegTVPortlet.SHOW_PROGRAMMES_TILE %>"
    onClick="document.getElementById('command').value='<%= SzovetsegTVPortlet.SHOW_PROGRAMMES %>'"> 
<input
    type="hidden" id="command" name="command" value=""></p>
<p><a
	href="<portlet:renderURL><portlet:param name='command' value='<%= SzovetsegTVPortlet.SHOW_ABOUT %>'></portlet:param></portlet:renderURL>">A
portletr&ocirc;l...</a></p>
</form>