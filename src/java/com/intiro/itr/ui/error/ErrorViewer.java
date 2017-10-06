package com.intiro.itr.ui.error;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.intiro.itr.ITRResources;
import com.intiro.itr.ui.ITRServlet;
import com.intiro.itr.ui.Page;
import com.intiro.itr.ui.constants.URLs;
import com.intiro.itr.ui.xsl.XSLFormatedArea;
import com.intiro.itr.util.ErrorHandler;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.log.IntiroLog;

/**
 * ErrorViewer displays all error that occures in the itr. All errors generated is handled and sent to ErrorViewer to be displayed to the
 * user.
 */
public class ErrorViewer extends ITRServlet implements URLs {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    /*Changing logginglevel, we don't want to display this page in the log.*/
    int previousLoggingLevel = IntiroLog.getInstance().getLoggingLevel();
    IntiroLog.getInstance().setLoggingLevel(1);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): ErrorViewer entered");
    }

    PrintWriter out = null;

    try {

      /*Create an output page*/
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      /*Fetching session and ClientInfo*/
      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

      /*Setting contentType*/
      response.setContentType(userProfile.getClientInfo().getContentType());

      /*Fetching output stream*/
      out = response.getWriter();

      /*Find ErrorHandler*/
      ErrorHandler errorHandler = null;

      if (request.getAttribute(ITRResources.ERROR_HANDLER) != null) {
        errorHandler = (ErrorHandler) request.getAttribute(ITRResources.ERROR_HANDLER);
      } else if (request.getSession(false).getAttribute(ITRResources.ERROR_HANDLER) != null) {
        errorHandler = (ErrorHandler) request.getSession(false).getAttribute(ITRResources.ERROR_HANDLER);
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Errorhandler requested from req object, handler = " + errorHandler);
      }

      /*Fetch information from errorhandler.*/
      //String errorPageMessage = "No errormessage found";
      //Exception exception = null;
      if (errorHandler != null) {
        //exception = errorHandler.getException();

        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Exception fetched from errorhandler");
        }

        //errorPageMessage = errorHandler.getErrorMessage();
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Errormessage requested");
        }
      } else {
        //exception = new Exception("No exception found");
      }

      /*Creating XSLFormatedArea*/
      DynamicXMLCarrier xmlCarrier = new DynamicXMLCarrier(userProfile);
      xmlCarrier.add(errorHandler);

      /*Find out what xsl to use*/
      String xslFile = null;

      if (ITRResources.DEBUG) {
        xslFile = ERROR_DEBUG_HTML_XSL;
      } else {
        xslFile = ERROR_NORMAL_HTML_XSL;
      }

      /*Creating XSLFormatedArea*/
      XSLFormatedArea xslError = new XSLFormatedArea(xmlCarrier, xslFile);
      page.add(xslError);

      /*Display the page*/
      page.display(out);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page displayed");
      }
      if (out != null) {
        out.flush();
      }
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".doGet(): An Error occured when trying to display ErrorViewer", exception);
    } finally {

      /*Resetting logginglevel*/
      IntiroLog.getInstance().setLoggingLevel(previousLoggingLevel);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doPost(): Entering doPost");
    }

    doGet(request, response);
  }
}
