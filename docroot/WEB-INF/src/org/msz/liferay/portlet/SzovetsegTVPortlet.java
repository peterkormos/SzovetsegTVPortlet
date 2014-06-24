/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.msz.liferay.portlet;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.msz.liferay.portlet.datatype.Program;
import org.msz.liferay.portlet.datatype.Timeframe;
import org.msz.liferay.portlet.util.IDAO;
import org.msz.liferay.portlet.util.PortletUtils;
import org.msz.liferay.portlet.util.SzovetsegTVHibernateDAO;

/**
 * <a href="JSPPortlet.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class SzovetsegTVPortlet extends GenericPortlet
{
  public static final String SHOW_ABOUT = "showAbout";
  public static final String SHOW_PROGRAM = "showProgram";
  public static final String SHOW_PROGRAMMES = "showProgrammes";
  public static final String SHOW_TIMEFRAME = "showTimeframe";

  public static final String SHOW_PROGRAM_TILE = "Program szerkeszt&eacute;se";
  public static final String SHOW_PROGRAMMES_TILE = "M&ucirc;sor list&aacute;z&aacute;sa";
  public static final String SHOW_TIMEFRAME_TILE = "Id&ocirc;szak szerkeszt&eacute;se";

  public static final String SAVE_TIMEFRAME_COMMAND = "saveTimeframeCommand";
  public static final String CHANGE_TIMEFRAME_COMMAND = "changeTimeframeCommand";
  public static final String DELETE_TIMEFRAME_COMMAND = "deleteTimeframeCommand";

  public static final String SAVE_PROGRAM_COMMAND = "saveProgramCommand";
  public static final String CHANGE_PROGRAM_COMMAND = "changeProgramCommand";
  public static final String DELETE_PROGRAM_COMMAND = "deleteProgramCommand";

  public static final String CHANGE_PROGRAMME_COMMAND = "changeProgrammeCommand";

  private static final String COMMAND = "command";
  
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
  private static final String TIME_FORMAT = "HH:mm:ss.SSS";
  
  private static final String SHOW_RESPONSEMESSAGE = "SHOW_RESPONSEMESSAGE";
  
  public static final String RESPONSEMESSAGE = "RESPONSEMESSAGE";
  public static final String RESPONSE_COMMAND_TITLE = "RESPONSE_COMMAND_TITLE";
  public static final String RESPONSE_COMMAND = "RESPONSE_COMMAND";
  public static final String PROGRAM_SESSION_ATTRIBUTE = "PROGRAM_SESSION_ATTRIBUTE";
  public static final String TIMEFRAME_SESSION_ATTRIBUTE = "TIMEFRAME_SESSION_ATTRIBUTE";

  public static IDAO dao = new SzovetsegTVHibernateDAO(); 
  
  public Logger logger;

  protected String editJSP;
  protected String helpJSP;
  protected String viewJSP;

  public void init() throws PortletException
  {
    logger = Logger.getLogger(getClass());
    DOMConfigurator.configure(getClass().getClassLoader().getResource(
        "log4j.xml"));
    logger
        .fatal("************************ Logging restarted ************************");

    editJSP = getInitParameter("edit-jsp");
    helpJSP = getInitParameter("help-jsp");
    viewJSP = getInitParameter("view-jsp");
  }

  public void doDispatch(RenderRequest renderRequest,
      RenderResponse renderResponse) throws IOException, PortletException
  {

    String jspPage = renderRequest.getParameter("jspPage");

    if (jspPage != null)
    {
      include(jspPage, renderRequest, renderResponse);
    }
    else
    {
      super.doDispatch(renderRequest, renderResponse);
    }
  }

  public void doEdit(RenderRequest renderRequest, RenderResponse renderResponse)
      throws IOException, PortletException
  {

    if (renderRequest.getPreferences() == null)
    {
      super.doEdit(renderRequest, renderResponse);
    }
    else
    {
      include(editJSP, renderRequest, renderResponse);
    }
  }

  public void doHelp(RenderRequest renderRequest, RenderResponse renderResponse)
      throws IOException, PortletException
  {

    include(helpJSP, renderRequest, renderResponse);
  }

  public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
      throws IOException, PortletException
  {
    include(viewJSP, renderRequest, renderResponse);
  }

  public void processAction(ActionRequest actionRequest,
      ActionResponse actionResponse) throws IOException, PortletException
  {
    String command = actionRequest.getParameter(COMMAND);
    logger.debug("processAction(): command: " + command);

    try
    {

      if (command != null && command.length() != 0)
      {
        // timeframe
        if (command.equals(SAVE_TIMEFRAME_COMMAND))
          processSaveTimeframeRequest(actionRequest, actionResponse);
        else if (command.equals(CHANGE_TIMEFRAME_COMMAND))
          processChangeTimeframeRequest(actionRequest, actionResponse);
        else if (command.equals(DELETE_TIMEFRAME_COMMAND))
          processDeleteTimeframeRequest(actionRequest, actionResponse);
        // program
        else if (command.equals(SAVE_PROGRAM_COMMAND))
          processSaveProgramRequest(actionRequest, actionResponse);
        else if (command.equals(CHANGE_PROGRAM_COMMAND))
          processChangeProgramRequest(actionRequest, actionResponse);
        else if (command.equals(DELETE_PROGRAM_COMMAND))
          processDeleteProgramRequest(actionRequest, actionResponse);
        // programme
        else if (command.equals(CHANGE_PROGRAMME_COMMAND))
          processChangeProgrammeRequest(actionRequest, actionResponse);
        else
          actionResponse.setRenderParameter(COMMAND, command);
      }
    }
    catch (Exception e)
    {
      logger.fatal("!!! processAction(): command: " + command, e);

      actionResponse.setRenderParameter(COMMAND, SHOW_RESPONSEMESSAGE);
      actionResponse.setRenderParameter(RESPONSEMESSAGE,
          "A m&ucirc;velet feldolgoz&aacute;sa k&ouml;zben hiba t&ouml;rt&eacute;nt: "
              + e.getMessage());
    }
  }

  private void processDeleteProgramRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws Exception
  {
    int programID = PortletUtils.getIntRequestParameter(actionRequest,
        "programID");

    logger.debug("processDeleteProgramRequest(): programID: " + programID);

    if(dao.getProgram(programID).timeframe.closed)
      throw new IllegalArgumentException("A megadott id&ocirc;szak m&aacute;r le van z&aacute;rva!");

    dao.deleteProgram(programID);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    // portletSession.removeAttribute(PROGRAM_SESSION_ATTRIBUTE);

    actionResponse.setRenderParameter(COMMAND, SHOW_PROGRAM);
    // actionResponse.setRenderParameter(COMMAND, SHOW_RESPONSEMESSAGE);
    // actionResponse.setRenderParameter(RESPONSE_COMMAND, SHOW_PROGRAM);
    // actionResponse
    // .setRenderParameter(RESPONSE_COMMAND_TITLE, SHOW_PROGRAM_TILE);
  }

  private void processDeleteTimeframeRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws Exception
  {
    int timeframeID = PortletUtils.getIntRequestParameter(actionRequest,
        "timeframeID");

    logger
        .debug("processDeleteTimeframeRequest(): timeframeID: " + timeframeID);
    
    dao.deleteTimeframe(timeframeID);
    
    for (Program program : dao.getPrograms(timeframeID))
      dao.deleteProgram(program.id);
      
    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
//    portletSession.removeAttribute(TIMEFRAME_SESSION_ATTRIBUTE);

    
    actionResponse.setRenderParameter(COMMAND, SHOW_TIMEFRAME);
//    actionResponse.setRenderParameter(COMMAND, SHOW_RESPONSEMESSAGE);
//    actionResponse.setRenderParameter(RESPONSE_COMMAND, SHOW_TIMEFRAME);
//    actionResponse.setRenderParameter(RESPONSE_COMMAND_TITLE,
//        SHOW_TIMEFRAME_TILE);
  }

  private void processChangeProgramRequest(ActionRequest actionRequest,
      ActionResponse actionResponse)
  {
    int programID = PortletUtils.getIntRequestParameter(actionRequest,
        "changeto_programID");

    logger.debug("processChangeProgramRequest(): programID: " + programID);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    try
    {
      portletSession.setAttribute(PROGRAM_SESSION_ATTRIBUTE, dao
          .getProgram(programID));
    }
    catch (Exception e)
    {
      portletSession.removeAttribute(SHOW_PROGRAM);
    }

    actionResponse.setRenderParameter(COMMAND, SHOW_PROGRAM);
  }

  private void processChangeProgrammeRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws Exception, ParseException
  {
    int timeframeID = PortletUtils.getIntRequestParameter(actionRequest,
        "timeframeID");

    logger.debug("processChangeProgramRequest(): timeframeID: " + timeframeID);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    try
    {
      portletSession.setAttribute(TIMEFRAME_SESSION_ATTRIBUTE, dao
          .getTimeframe(timeframeID));
    }
    catch (Exception e)
    {
      portletSession.removeAttribute(TIMEFRAME_SESSION_ATTRIBUTE);
    }

    actionResponse.setRenderParameter(COMMAND, SHOW_PROGRAMMES);
  }

  private void processChangeTimeframeRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws Exception, ParseException
  {
    int timeframeID = PortletUtils.getIntRequestParameter(actionRequest,
        "changeto_timeframeID");

    logger
        .debug("processChangeTimeframeRequest(): timeframeID: " + timeframeID);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    try
    {
      portletSession.setAttribute(TIMEFRAME_SESSION_ATTRIBUTE, dao
          .getTimeframe(timeframeID));
    }
    catch (Exception e)
    {
      portletSession.removeAttribute(TIMEFRAME_SESSION_ATTRIBUTE);
    }

    actionResponse.setRenderParameter(COMMAND, SHOW_TIMEFRAME);
  }

  private void processSaveProgramRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws ParseException, Exception
  {
    int programID = PortletUtils.getIntRequestParameter(actionRequest,
        "programID");

    logger.debug("processSaveProgramRequest(): programID: " + programID);

    Program program = new Program(PortletUtils.getIntRequestParameter(
        actionRequest, "program_id"), PortletUtils.getStringRequestParameter(
        actionRequest, "program_title"), PortletUtils
        .getStringRequestParameter(actionRequest, "program_description"),
        convertTime(PortletUtils.getStringRequestParameter(actionRequest,
            "program_length")), dao.getTimeframe(PortletUtils
            .getIntRequestParameter(actionRequest, "timeframeID")),
        PortletUtils.getStringRequestParameter(actionRequest,
            "program_photoUrl"), PortletUtils.getStringRequestParameter(
            actionRequest, "program_videoUrl"));
    
    if(program.timeframe.closed)
      throw new IllegalArgumentException("A megadott id&ocirc;szak m&aacute;r le van z&aacute;rva!");

    logger.debug("processSaveProgramRequest(): saving: " + program);

    if (programID == 0)
      dao.storeProgram(program);
    else
      dao.updateProgram(programID, program);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    // portletSession.removeAttribute(PROGRAM_SESSION_ATTRIBUTE);
    portletSession.setAttribute(PROGRAM_SESSION_ATTRIBUTE, program);

    actionResponse.setRenderParameter(COMMAND, SHOW_PROGRAM);
    // actionResponse.setRenderParameter(COMMAND, SHOW_RESPONSEMESSAGE);
    // actionResponse.setRenderParameter(RESPONSE_COMMAND, SHOW_PROGRAM);
    // actionResponse
    // .setRenderParameter(RESPONSE_COMMAND_TITLE, SHOW_PROGRAM_TILE);
  }

  private void processSaveTimeframeRequest(ActionRequest actionRequest,
      ActionResponse actionResponse) throws Exception, ParseException
  {
    int timeframeID = PortletUtils.getIntRequestParameter(actionRequest,
        "timeframeID");

    logger.debug("processSaveTimeframeRequest(): timeframeID: " + timeframeID);

    Timeframe timeframe;

    if (timeframeID == 0)
    {
      timeframe = new Timeframe(dao.getNextTimeframeID(),
          PortletUtils.getCheckboxState(actionRequest, "closeTimeframe"),
          convertDateTime(PortletUtils.getStringRequestParameter(actionRequest,
              "timeframe_From")), convertDateTime(PortletUtils
              .getStringRequestParameter(actionRequest, "timeframe_To")),
          PortletUtils.getStringRequestParameter(actionRequest,
              "timeframe_editor"), PortletUtils.getStringRequestParameter(
              actionRequest, "timeframe_cycle"));
    }
    else
    {
      timeframe = new Timeframe(timeframeID, PortletUtils.getCheckboxState(
          actionRequest, "closeTimeframe"), convertDateTime(PortletUtils
          .getStringRequestParameter(actionRequest, "timeframe_From")),
          convertDateTime(PortletUtils.getStringRequestParameter(actionRequest,
              "timeframe_To")), PortletUtils.getStringRequestParameter(
              actionRequest, "timeframe_editor"), PortletUtils
              .getStringRequestParameter(actionRequest, "timeframe_cycle"));
    }

    logger.debug("processSaveTimeframeRequest(): saving: " + timeframe);

    if (timeframeID == 0)
      dao.storeTimeframe(timeframe);
    else
      dao.updateTimeframe(timeframe);

    PortletSession portletSession = PortletUtils
        .getPortletSession(actionRequest);
    // portletSession.removeAttribute(TIMEFRAME_SESSION_ATTRIBUTE);
    portletSession.setAttribute(TIMEFRAME_SESSION_ATTRIBUTE, timeframe);

    actionResponse.setRenderParameter(COMMAND, SHOW_TIMEFRAME);
    // actionResponse.setRenderParameter(COMMAND, SHOW_RESPONSEMESSAGE);
    // actionResponse.setRenderParameter(RESPONSE_COMMAND, SHOW_TIMEFRAME);
    // actionResponse.setRenderParameter(RESPONSE_COMMAND_TITLE,
    // SHOW_TIMEFRAME_TILE);
  }

  protected void include(String path, RenderRequest renderRequest,
      RenderResponse renderResponse) throws IOException, PortletException
  {
    String command = renderRequest.getParameter(COMMAND);
    logger.debug("include(): command: " + command);

    if (command != null && command.length() != 0)
    {
      try
      {
        if (command.equals(SHOW_ABOUT))
          changeView(PortletUtils.getPortletPreferences(renderRequest,
              "aboutURL"), renderRequest, renderResponse);
        else if (command.equals(SHOW_TIMEFRAME))
          changeView(PortletUtils.getPortletPreferences(renderRequest,
              "timeframeURL"), renderRequest, renderResponse);
        else if (command.equals(SHOW_PROGRAM))
          changeView(PortletUtils.getPortletPreferences(renderRequest,
              "programURL"), renderRequest, renderResponse);
        else if (command.equals(SHOW_PROGRAMMES))
          changeView(PortletUtils.getPortletPreferences(renderRequest,
              "programmesURL"), renderRequest, renderResponse);

        else if (command.equals(SHOW_RESPONSEMESSAGE))
          changeView(PortletUtils.getPortletPreferences(renderRequest,
              "responseMessageURL"), renderRequest, renderResponse);
      }
      catch (SystemException ex)
      {
        logger.fatal("!!! include(): command: " + command, ex);
      }
    }
    else
    {
      changeView(path, renderRequest, renderResponse);
    }
  }

  private void changeView(String path, RenderRequest renderRequest,
      RenderResponse renderResponse) throws PortletException, IOException
  {
    PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
        .getRequestDispatcher(path);

    if (portletRequestDispatcher == null)
      logger.error("Path: [" + path + "] is not valid...");
    else
      portletRequestDispatcher.include(renderRequest, renderResponse);
  }

  public static String convertDateTime(long timestamp)
  {
    return new SimpleDateFormat(DATETIME_FORMAT).format(new Date(timestamp));
  }

  public static long convertDateTime(String timestamp)
  {
    try
    {
      return new SimpleDateFormat(DATETIME_FORMAT).parse(timestamp).getTime();
    }
    catch (ParseException e)
    {
      throw new IllegalArgumentException(
          "A megadott d&aacute;tumnak ("
              + timestamp
              + ") nem megfelel&ocirc; a form&aacute;tuma! A j&oacute; form&aacute;tum: <b>"
              + DATETIME_FORMAT + "</b>", e);
    }
  }

  public static String convertTime(long timestamp)
  {
    return new SimpleDateFormat(TIME_FORMAT).format(new Date(timestamp));
  }

  public static long convertTime(String timestamp)
  {
    try
    {
      return new SimpleDateFormat(TIME_FORMAT).parse(timestamp).getTime();
    }
    catch (ParseException e)
    {
      throw new IllegalArgumentException(
          "A megadott id&ocirc;nek ("
              + timestamp
              + ") nem megfelel&ocirc; a form&aacute;tuma! A j&oacute; form&aacute;tum: <b>"
              + TIME_FORMAT + "</b>", e);
    }
  }
}