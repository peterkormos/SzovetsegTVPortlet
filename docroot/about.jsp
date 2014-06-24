<%@ page import="javax.portlet.*" %>

<%@ page import="com.liferay.portal.kernel.util.*" %> 
<%@ page import="com.liferay.portal.util.*" %>
<%@ page import="com.liferay.portlet.*" %> 


<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects />

<p>Portletet k&eacute;sz&iacute;t&otilde;je: <a href="mailto:msz.kormospeter@gmail.com">Kormos 
  P&eacute;ter</a></p>
<p>Verzi&oacute;: 2.0</p>
<p>2010. Budapest</p>

  <form action="<portlet:actionURL/>" method="POST" name="<portlet:namespace />fm">

  <p> 
    <input type="submit" name="Submit" value="Vissza a f&ocirc;oldalra">
  </p>
  </form>
