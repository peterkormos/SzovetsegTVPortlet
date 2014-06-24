package org.msz.liferay.portlet.util;

import javax.portlet.*;

import com.liferay.portal.*;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class PortletUtils
{
  public static String getPortletPreferences(PortletRequest renderRequest,
      String preference) throws SystemException
  {
    PortletPreferences preferences = getPortletPreferences(renderRequest);

    return GetterUtil.getString(preferences.getValue(preference,
        StringPool.BLANK));
  }

  public static PortletPreferences getPortletPreferences(
      PortletRequest renderRequest) throws SystemException
  {
    String portletId = (String) renderRequest.getAttribute(WebKeys.PORTLET_ID);
    return PortletPreferencesFactoryUtil.getPortletSetup(renderRequest,
        portletId);
  }

  public static PortletSession getPortletSession(PortletRequest request)
  {
    return request.getPortletSession();
  }

  public static String getStringRequestParameter(PortletRequest request,
      String parameterName)
  {
    return request.getParameter(parameterName);
  }

  public static int getIntRequestParameter(PortletRequest request,
      String parameterName)
  {
    try
    {
      return Integer.parseInt(request.getParameter(parameterName));
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("parameterName: " + parameterName + " cannot be converted to int type", e);
    }
  }

  public static boolean getCheckboxState(PortletRequest request,
      String parameter) throws Exception
  {
    try
    {
//      System.out.println("getCheckboxState(): " + parameter + " "
//          + PortletUtils.getStringRequestParameter(request, parameter));
      
      return "on".equalsIgnoreCase(PortletUtils.getStringRequestParameter(
          request, parameter));
    }
    catch (Exception e)
    {
      e.printStackTrace();

      return false;
    }
  }
}
