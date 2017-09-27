/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.superadmin.users;

import com.intiro.itr.ITRResources;

import com.intiro.itr.logic.superadmin.contacts.ContactsQueryProfile;
import com.intiro.itr.logic.superadmin.users.PhoneQueryProfile;

import com.intiro.itr.ui.*;

import com.intiro.itr.ui.constants.*;

import com.intiro.itr.ui.error.NoSessionException;

import com.intiro.itr.ui.xsl.*;

import com.intiro.itr.util.*;

import com.intiro.itr.util.personalization.*;

import com.intiro.toolbox.log.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class PhoneQueryView extends ITRServlet implements URLs, Commands {
  //~ Methods ..........................................................................................................

  public void doGet( HttpServletRequest request, HttpServletResponse response )
             throws ServletException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".doGet(): entered doGet" );
    }

    PrintWriter out = null;

    try {
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page( request );

      if ( IntiroLog.t() ) {
        IntiroLog.trace( getClass(), getClass().getName() + ".doGet(): Page created" );
      }

      HttpSession session = request.getSession( false );
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );
      response.setContentType( userProfile.getClientInfo().getContentType() );

      String action = request.getParameter( "action" );
      boolean isContact = ( action != null && action.equals( "contacts" ) )
                          ? true
                          : false;

      if ( IntiroLog.t() ) {
        IntiroLog.trace( getClass(), getClass().getName() + ".doGet(): action=" + action + " isContact=" + isContact );
      }

      XSLFormatedArea xslUser = null;

      if ( isContact ) {
        ContactsQueryProfile xmlCarrier = new ContactsQueryProfile( userProfile );
        xslUser = new XSLFormatedArea( xmlCarrier, SUPERADMIN_USERS_PHONE_QUERY_HTML_XSL );
      } else {
        PhoneQueryProfile xmlCarrier = new PhoneQueryProfile( userProfile );
        xslUser = new XSLFormatedArea( xmlCarrier, SUPERADMIN_USERS_PHONE_QUERY_HTML_XSL );
      }

      page.add( xslUser );

      //Display the page
      page.display( out );

      if ( IntiroLog.t() ) {
        IntiroLog.trace( getClass(), getClass().getName() + ".doGet(): Page displayed" );
      }
      if ( out != null ) {
        out.flush();
      }
    } catch ( NoSessionException e ) {
      if ( IntiroLog.i() ) {
        IntiroLog.info( getClass(), 
                        getClass().getName() + ".doGet(): Nosession exception caught in " + getClass().getName() );
      }

      reAuthenticate( request, response, getServletContext(), "No Session - reauthenticate" );

      return;
    } catch ( Exception exception ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( getClass(), 
                                 getClass().getName() + ".doGet(): An Error occured when trying to display " + 
                                 getClass().getName(), exception );
      }

      UserProfile userProfile = ( UserProfile )request.getSession( false ).getAttribute( ITRResources.ITR_USER_PROFILE );
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler( userProfile );
      } catch ( Exception e ) {
        System.out.println( "message: " + e.getMessage() );
        e.printStackTrace();
      }

      errorHandler.setErrorMessage( "A problem occured when trying to display the " + getClass().getName() + " page." );
      errorHandler.setException( exception );
      handleError( request, response, getServletContext(), errorHandler );

      return;
    } 
  }


  public void doPost( HttpServletRequest request, HttpServletResponse response )
              throws ServletException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".doPost(): Entering doPost()" );
    }

    doGet( request, response );
  }
}