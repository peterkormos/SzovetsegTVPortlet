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
  Program programFromSession = (Program) PortletUtils.getPortletSession(
      renderRequest).getAttribute(
      SzovetsegTVPortlet.PROGRAM_SESSION_ATTRIBUTE);

  if (programFromSession == null)
    programFromSession = new Program(SzovetsegTVPortlet.dao.getNextProgramID(), "",
        "", 0, null, Program.NO_PHOTOURL, Program.NO_VIDEOURL);
%>

<head>

<script type="text/javascript">
function saveProgram() 
{
    if(document.getElementById('changeto_programID').value == 0)
    {
        document.getElementById('programID').value='0';
	}             

    document.getElementById('command').value='<%=SzovetsegTVPortlet.SAVE_PROGRAM_COMMAND%>';

	return true;
}

function deleteProgram()
{
    if(document.getElementById('changeto_programID').value == document.getElementById('programID').value)
    {
//      alert('OK!');
        document.getElementById('command').value='<%= SzovetsegTVPortlet.DELETE_PROGRAM_COMMAND %>';
			return true;
		} else {
			alert("A kivalasztott es a jelenleg megmutatott musor nem ugyanaz!");
			return false;
		}
	}
</script>

</head>

<body>
<font face="Geneva, Arial, Helvetica, sans-serif"><portlet:defineObjects /></font>
<form action="<portlet:actionURL/>" method="POST"
	name="<portlet:namespace />fm"><input type="hidden"
	id="programID" name="programID" value="<%=programFromSession.id%>">

<table width="100%" border="0">
	<tr>
		<td bgcolor="#B9DCFF">
		<p>&nbsp;</p>
		<table border="0" align="center" cellpadding="5" cellspacing="5">
			<tr>
				
            <td bgcolor="#999999"> <p align="center"><br><font size="+2"
					face="Geneva, Arial, Helvetica, sans-serif">
                M&ucirc;sorok kezel&eacute;se:</font></p>
				<table width="100%" border="0" cellpadding="5" cellspacing="5">
					<tr>
						
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> Neve:</font></td>
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> 
                    <input name="program_title" type="text"
							value="<%=programFromSession.title%>" size="60">
                    <br>
                    </font></td>
					</tr>
					<tr>
						
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> Le&iacute;r&aacute;s:</font></td>
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> 
                    <textarea name="program_description" cols="60" rows="8">
<%=programFromSession.description%>
</textarea>
                    <br>
                    </font></td>
					</tr>
					<tr>
						
                  <td> K&eacute;p:<br>
                  </td>
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> 
                    <input name="program_photoUrl" type="text"
							value="<%=programFromSession.photoUrl%>" size="60">
                    <br>
                    </font></td>
					</tr>
					<tr>
						
                  <td> Video:<br>
                  </td>
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> 
                    <input name="program_videoUrl" type="text"
							value="<%=programFromSession.videoUrl%>" size="60">
                    <br>
                    </font></td>
					</tr>
					<tr>
						
                  <td> Hat&aacute;lyos:</td>
                  <td><font face="Geneva, Arial, Helvetica, sans-serif"> 
                    <select name="timeframeID">
                      <%
							  Collection<Timeframe> timeframes = SzovetsegTVPortlet.dao.getTimeframes();
							  for (Timeframe timeframe : timeframes)
							    out.println("<option "
							        + (programFromSession.timeframe != null
							            && programFromSession.timeframe.id == timeframe.id ? "selected"
							            : "") + " value='" + timeframe.id + "'>" + timeframe.id + ". "
                          + timeframe.cycle + " ("
							        + SzovetsegTVPortlet.convertDateTime(timeframe.from)
							        + ") t&oacute;l - ("
							        + SzovetsegTVPortlet.convertDateTime(timeframe.to) + ") ig "
							        + (timeframe.closed ? "LEZ&Aacute;RVA" : "") + "</option>");
							%>
                    </select>
                    <br>
                    </font></td>
					</tr>
					<tr>
						
                  <td> Sorsz&aacute;m:</td>
						<td><font face="Geneva, Arial, Helvetica, sans-serif">
						<input name="program_id" type="text" size="4"
							value="<%=programFromSession.id%>"> </font> (az id&otilde;szak
						hanyadik m&ucirc;sora?) Hossza: <font
							face="Geneva, Arial, Helvetica, sans-serif"> <input
							type="text" name="program_length"
							value="<%=SzovetsegTVPortlet.convertTime(programFromSession.length)%>">
                    </font> (&oacute;ra:perc:msp:tmsp)<br>
                  </td>
					</tr>
					<tr>
						<td colspan="2">
						<div align="center">
                      <p>&nbsp;</p>
                      <p><font
							face="Geneva, Arial, Helvetica, sans-serif"> 
                        <input
							type="submit" onClick="saveProgram()" value="Ment&eacute;s">
                        <input type="submit" onClick="deleteProgram()"
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
		<p align="center">A m&aacute;r felt&ouml;lt&ouml;tt m&ucirc;sorok
		list&aacute;ja:</p>
		<table width="100%" border="1">
			<tr>
				<td bgcolor="#CCCCCC">
				<div align="center"><font
					face="Geneva, Arial, Helvetica, sans-serif">&Ouml;sszes</font><font
					face="Geneva, Arial, Helvetica, sans-serif">: <select
					id="changeto_programID" name="changeto_programID">
					<%
					  out.println("<option value='0'>&Uacute;j program</option>");

					  for (Timeframe timeframe : timeframes)
					  {
					    out.println("<optgroup label='" + timeframe.id + ". "
                  + timeframe.cycle + " ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.from)
					        + ") t&oacute;l - ("
					        + SzovetsegTVPortlet.convertDateTime(timeframe.to) + ") ig "
                  + (timeframe.closed ? "LEZ&Aacute;RVA" : "") + "'>");

					    Collection<Program> programs = SzovetsegTVPortlet.dao.getPrograms(timeframe.id);
					    for (Program program : programs)
					      out.println("<option value='"
					          + program.id
					          + "' "
					          + (programFromSession.id == program.id ? "selected" : "")
					          + ">"
					          + program.id
					          + ". "
					          + program.title
					          + " ("
					          + SzovetsegTVPortlet.convertTime(program.length)
					          + ") |"
					          + (program.photoUrl.length() == 0 ? Program.NO_PHOTOURL
					              : program.photoUrl)
					          + "|"
					          + (program.videoUrl.length() == 0 ? Program.NO_VIDEOURL
					              : program.videoUrl) + "|" + "</option>");

					    out.println("</optgroup>");
					  }
					%>
				</select> <input type="submit"
					onClick="document.getElementById('command').value='<%=SzovetsegTVPortlet.CHANGE_PROGRAM_COMMAND%>'"
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