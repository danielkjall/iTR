package com.intiro.itr.ui.superadmin.users;

import com.intiro.itr.ITRResources;
import com.intiro.itr.logic.superadmin.users.UserEdit;
import com.intiro.itr.ui.*;
import com.intiro.itr.ui.constants.*;
import com.intiro.itr.ui.error.NoSessionException;
import com.intiro.itr.ui.xsl.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.log.IntiroLog;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserEditView extends ITRServlet implements URLs, Commands {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): entered doGet");
    }

    PrintWriter out;

    try {
      out = response.getWriter();

      //Create an output page
      Page page = new Page(request);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page created");
      }

      HttpSession session = request.getSession(false);
      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);
      response.setContentType(userProfile.getClientInfo().getContentType());

      //START ACTION, Find out what action is called and perform it
      String edit = request.getParameter("btnEdit");
      String add = request.getParameter("btnAdd");
      String delete = request.getParameter("btnDelete");
      String view = request.getParameter("btnView");
      String activate = request.getParameter("btnActivate");
      //boolean bEdit = false;
      boolean bAdd = false;
      //boolean bView = false;
      //boolean bDelete = false;
      boolean bActivate = false;

      //Find out what button was pressed
      String mode = "error";

      if (edit != null && edit.length() > 0) {
        mode = edit.trim();
        //bEdit = true;
      } else if (add != null && add.length() > 0) {
        mode = add.trim();
        bAdd = true;
      } else if (delete != null && delete.length() > 0) {
        mode = delete.trim();
        //bDelete = true;
      } else if (view != null && view.length() > 0) {
        mode = view.trim();
        //bView = true;
      } else if (activate != null && activate.length() > 0) {
        mode = activate.trim();
        bActivate = true;
      }

      //Fetch userid
      String userId = "-1";

      if (bActivate) {
        userId = request.getParameter("deactivateduser");
      } else if (!bAdd) {
        userId = request.getParameter("activateduser");
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): userId = " + userId);
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): mode = " + mode);
      }

      //Create xmlCarrier
      UserEdit xmlCarrier = new UserEdit(userProfile, mode, userId);

      //Set modified user in session
      session.setAttribute(ITRResources.ITR_MODIFIED_USER, xmlCarrier);

      XSLFormatedArea xslUser = new XSLFormatedArea(xmlCarrier, SUPERADMIN_USERS_CHANGE_HTML_XSL);
      page.add(xslUser);

      //Display the page
      page.display(out);

      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Page displayed");
      }
      if (out != null) {
        out.flush();
      }
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".doGet(): Leaving");
      }
    } catch (NoSessionException e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(),
                getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName());
      }

      reAuthenticate(request, response, getServletContext(), "No Session - reauthenticate");

      return;
    } catch (Exception exception) {
      IntiroLog.criticalError(getClass(),
              getClass().getName() + ".doGet(): An Error occured when trying to display "
              + getClass().getName(), exception);

      UserProfile userProfile = (UserProfile) request.getSession(false).getAttribute(ITRResources.ITR_USER_PROFILE);
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler(userProfile);
      } catch (Exception e) {
        System.out.println("message: " + e.getMessage());
        e.printStackTrace();
      }

      errorHandler.setErrorMessage("A problem occured when trying to display the " + getClass().getName() + " page.");
      errorHandler.setException(exception);
      handleError(request, response, getServletContext(), errorHandler);
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
