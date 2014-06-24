<%@ page import="javax.portlet.*"%>

<%@page import="java.util.*"%>

<%@ page import="com.liferay.portal.kernel.util.*"%>
<%@ page import="com.liferay.portal.util.*"%>
<%@ page import="com.liferay.portlet.*"%>

<%@page import="org.msz.liferay.portlet.datatype.*"%>
<%@page import="org.msz.liferay.portlet.util.*"%>
<%@page import="org.msz.liferay.portlet.*"%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>

<portlet:defineObjects />

<%
  Timeframe timeframeFromSession = (Timeframe) PortletUtils
      .getPortletSession(renderRequest).getAttribute(
          SzovetsegTVPortlet.TIMEFRAME_SESSION_ATTRIBUTE);
%>

<body>
<font face="Geneva, Arial, Helvetica, sans-serif"><portlet:defineObjects /></font>
<form action="<portlet:actionURL/>" method="POST"
	name="<portlet:namespace />fm">
<table width="100%" border="0">
	<tr>
		<td bgcolor="#B9DCFF">
		<%
		  if (timeframeFromSession == null)
		    out.println("K&eacute;rem v&aacute;lasszon egy id&ocirc;szakot!");
		  else
		  {
		    out.println("<b>Id&otilde;szak: </b>");
		    out.println(timeframeFromSession.id + ". "
            + timeframeFromSession.cycle + " ("
		        + SzovetsegTVPortlet.convertDateTime(timeframeFromSession.from)
		        + ") t&oacute;l - ("
		        + SzovetsegTVPortlet.convertDateTime(timeframeFromSession.to)
		        + ") ig");
		    out.println("<p>");
		    out.println("<p>");

		    out.println("<b>Programok: </b>");
		    out.println("<p>");
		    out.println("<p>");
		    out.println("<table width='100%' border='1'><tr><td>");
		    out.println("<table width='100%' border='0'>");
		    out.println("<tr>");
		    out.println("<th style='white-space: nowrap'>ID</th>");
		    out.println("<th style='white-space: nowrap'>N&eacute;v</th>");
		    out.println("<th style='white-space: nowrap'>Hossza</th>");
		    out.println("<th style='white-space: nowrap'>K&eacute;p URL</th>");
		    out.println("<th style='white-space: nowrap'>Video URL</th>");
		    out.println("</tr>");

		    long totalProgramLength = 0;
		    for (Program program : SzovetsegTVPortlet.dao
		        .getPrograms(timeframeFromSession.id))
		    {
		      out.println("<tr>");

		      out.println("<td>" + program.id + "</td>");
		      out.println("<td>" + program.title + "</td>");

		      totalProgramLength += program.length;
		      out.println("<td>" + SzovetsegTVPortlet.convertTime(program.length)
		          + "</td>");
		      out.println("<td>"
		          + (program.photoUrl.length() == 0 ? Program.NO_PHOTOURL
		              : program.photoUrl) + "</td>");
		      out.println("<td>"
		          + (program.videoUrl.length() == 0 ? Program.NO_VIDEOURL
		              : program.videoUrl) + "</td>");

		      out.println("</tr>");
		    }
		    out.println("</table>");
		    out.println("</td></tr></table>");

		    out.println("<p>");
		    out.println("<p>");

		    out.println("<b>Teljes programid&ocirc;: </b>"
		        + SzovetsegTVPortlet.convertTime(totalProgramLength));
		    out.println("<p>");
		    out.println("<p>");
		  }
		%>


		<p align="center">A m&aacute;r felt&ouml;lt&ouml;tt
		id&otilde;szakok list&aacute;ja:</p>
		<table width="100%" border="1">
			<tr>
				<td bgcolor="#CCCCCC">
				<div align="center"><font
					face="Geneva, Arial, Helvetica, sans-serif">Id&otilde;szakok:
				<select name="timeframeID">
					<%
					  out.println("<option value='0'>&Uacute;j id&ocirc;szak</option>");

					  Collection<Timeframe> timeframes = SzovetsegTVPortlet.dao.getTimeframes();
					  for (Timeframe timeframe : timeframes)
					    out.println("<option "
					        + (timeframeFromSession != null
					            && timeframeFromSession.id == timeframe.id ? "selected" : "")
					        + " value='" + timeframe.id + "'>" + timeframe.id + ". "
					        + timeframe.cycle + " ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.from)
					        + ") t&oacute;l - ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.to) + ") ig "
					        + (timeframe.closed ? "LEZ&Aacute;RVA" : "") + "</option>");
					%>
				</select> <input type="submit"
					onClick="document.getElementById('command').value='<%=SzovetsegTVPortlet.CHANGE_PROGRAMME_COMMAND%>'"
					value="M&oacute;dos&iacute;t&aacute;s"> </font></div>
				</td>
			</tr>
		</table>
		<p><font face="Geneva, Arial, Helvetica, sans-serif"> 
          <input
			type="hidden" id="command" name="command" value="">
          <br>
          <input
			type="submit" value="Vissza a f&ocirc;oldalra">
          </font></p>
		</td>
	</tr>
</table>
</form>