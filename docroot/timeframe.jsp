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

  if (timeframeFromSession == null)
    timeframeFromSession = new Timeframe(SzovetsegTVPortlet.dao
        .getNextTimeframeID(), false, System.currentTimeMillis(), System
        .currentTimeMillis(), "", "");
%>
<head>

<script type="text/javascript">
function saveTimeframe()
{
    if(document.getElementById('changeto_timeframeID').value == 0)
    {
        document.getElementById('command').value='<%=SzovetsegTVPortlet.SAVE_TIMEFRAME_COMMAND%>';
        document.getElementById('timeframeID').value='0';
    }             

    return true;
}


function deleteTimeframe()
{
    if(document.getElementById('changeto_timeframeID').value == document.getElementById('timeframeID').value)
    {
//      alert('OK!');
        document.getElementById('command').value='<%=SzovetsegTVPortlet.DELETE_TIMEFRAME_COMMAND%>';
             
        return true;
    }
    else
    {
        alert("A kivalasztott es a jelenleg megmutatott idoszak nem ugyanaz!");
        return false;
    }
}
</script>

</head>

<body>

<form action="<portlet:actionURL/>" method="POST"
	name="<portlet:namespace />fm">

<input type="hidden" id="timeframeID" name="timeframeID" value="<%=timeframeFromSession.id%>">

<table width="100%" border="0">
	<tr>
		<td bgcolor="#B9DCFF">
		<table border="0" align="center" cellpadding="5">
			<tr>
				
            <td bgcolor="#999999"> <br> <p align="center"><font size="+2"
					face="Geneva, Arial, Helvetica, sans-serif">
                Id&otilde;szakok kezel&eacute;se</font></p>
				<p align="center"><font
					face="Geneva, Arial, Helvetica, sans-serif"><u> <%=timeframeFromSession.id%>
				. id&otilde;szak m&oacute;dos&iacute;t&aacute;sa: <input
					type="checkbox" name="closeTimeframe"
					<%if (timeframeFromSession.closed)
        out.print("checked='checked'");%>>
				lez&aacute;r&aacute;s</u></font></p>
				<p align="left">&nbsp;</p>
				<table width="100%" border="0" cellpadding="5">
					<tr>
						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						Id&otilde;szak:</font></td>
						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						<input type="text" name="timeframe_From"
							value="<%=SzovetsegTVPortlet.convertDateTime(timeframeFromSession.from)%>">
						t&oacute;l </font></td>
						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						<input type="text" name="timeframe_To"
							value="<%=SzovetsegTVPortlet.convertDateTime(timeframeFromSession.to)%>">
						ig </font></td>
					</tr>
					<tr>

						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						Ciklus:</font></td>
						<td colspan="2"><font
							face="Geneva, Arial, Helvetica, sans-serif"> <input
							type="text" name="timeframe_cycle"
							value="<%=timeframeFromSession.cycle%>"> </font></td>
					</tr>
					<tr>
						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						&Uuml;gyeletes szerkeszt&otilde;:</font></td>
						<td colspan="2"><font
							face="Geneva, Arial, Helvetica, sans-serif"> <input
							type="text" name="timeframe_editor"
							value="<%=timeframeFromSession.editor%>"> </font></td>
					</tr>

					<tr>
						<td colspan="3">
						<div align="center">
                      <p>&nbsp;</p>
                      <p><font
							face="Geneva, Arial, Helvetica, sans-serif"> 
                        <input
							type="submit"
							onClick="document.getElementById('command').value='<%=SzovetsegTVPortlet.SAVE_TIMEFRAME_COMMAND%>'"
							value="Ment&eacute;s">
                        <input type="submit"
							onClick="deleteTimeframe()"
							value="T&ouml;rl&eacute;s">
                        <input type="hidden"
							id="command" name="command" value="">
                        </font></p>
                    </div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<p align="center">A m&aacute;r felt&ouml;lt&ouml;tt
		id&otilde;szakok list&aacute;ja:</p>
		<table width="100%" border="1">
			<tr>
				<td bgcolor="#CCCCCC">
				<div align="center"><font
					face="Geneva, Arial, Helvetica, sans-serif">Id&otilde;szakok:
				<select id="changeto_timeframeID" name="changeto_timeframeID">
					<%
					  out.println("<option value='0'>&Uacute;j id&ocirc;szak</option>");

					  Collection<Timeframe> timeframes = SzovetsegTVPortlet.dao.getTimeframes();
					  for (Timeframe timeframe : timeframes)
					    out.println("<option "
					        + (timeframeFromSession.id == timeframe.id ? "selected" : "")
					        + " value='" + timeframe.id + "'>" + timeframe.id + ". "
					        + timeframe.cycle + " ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.from)
					        + ") t&oacute;l - ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.to) + ") ig "
					        + (timeframe.closed ? "LEZ&Aacute;RVA" : "") + "</option>");
					%>
				</select> <input type="submit"
					onClick="document.getElementById('command').value='<%=SzovetsegTVPortlet.CHANGE_TIMEFRAME_COMMAND%>'"
					value="M&oacute;dos&iacute;t&aacute;s"> </font></div>
				</td>
			</tr>
		</table>
		<p><font face="Geneva, Arial, Helvetica, sans-serif"> <br>
          <input
			type="submit" value="Vissza a f&ocirc;oldalra">
          </font></p>
		</td>
	</tr>
</table>
</form>