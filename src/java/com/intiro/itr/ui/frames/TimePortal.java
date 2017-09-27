/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui.frames;

import com.intiro.itr.ITRResources;

import com.intiro.itr.ui.*;
import com.intiro.itr.ui.ITRServlet;

import com.intiro.itr.ui.constants.*;

import com.intiro.itr.ui.error.NoSessionException;

import com.intiro.itr.ui.xsl.*;

import com.intiro.itr.util.*;

import com.intiro.itr.util.personalization.*;

import com.intiro.itr.util.xml.StaticXMLCarrier;

import com.intiro.toolbox.log.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class TimePortal extends ITRServlet implements URLs, Commands {
  //~ Methods ..........................................................................................................

  public void doGet( HttpServletRequest request, HttpServletResponse response )
             throws ServletException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".doGet(): Entering" );
    }

    PrintWriter out = null;

    try {

      /*Fetching output stream*/
      out = response.getWriter();

      /*Create an output page*/
      Page page = new Page( request );

      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".doGet(): Page created" );
      }

      /*Fetching session and ClientInfo*/
      HttpSession session = request.getSession( false );
      UserProfile userProfile = ( UserProfile )session.getAttribute( ITRResources.ITR_USER_PROFILE );

      /*Setting contentType*/
      response.setContentType( userProfile.getClientInfo().getContentType() );

      /*Creating XSLFormatedArea*/
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".doGet(): Creating XSLFormatedArea" );
      }

      StaticXMLCarrier xmlCarrier = new StaticXMLCarrier( FRAMESET_XML, userProfile );
      XSLFormatedArea xslArea = new XSLFormatedArea( xmlCarrier, FRAMESET_XSL );

      /*Adding XSLFormatedArea to page*/
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".doGet(): Adding XSLFormatedArea to page" );
      }

      page.add( xslArea );

      /*Display page*/
      page.display( out );

      if ( out != null ) {
        out.flush();
      }
    } catch ( NoSessionException e ) {
      if ( IntiroLog.i() ) {
        IntiroLog.info( getClass(), getClass().getName() + ".Nosession exception caught in " + getClass().getName() );
      }

      reAuthenticate( request, response, getServletContext(), Commands.REAUTHENTICATE_MESSAGE );

      return;
    } catch ( Exception exception ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( getClass(), getClass().getName() + ".doGet(): Entering catch Exception" );
      }

      /*Create errorHandler*/
      UserProfile userProfile = ( UserProfile )request.getSession( false ).getAttribute( ITRResources.ITR_USER_PROFILE );
      ErrorHandler errorHandler = null;

      try {
        errorHandler = new ErrorHandler( userProfile );
      } catch ( Exception e ) {
        System.out.println( "message: " + e.getMessage() );
        e.printStackTrace();
      }

      errorHandler.setErrorMessage( "A problem occured when trying to display the frameset (TimePortal page)." );
      errorHandler.setException( exception );
      handleError( request, response, getServletContext(), errorHandler );
    }
    return;
  }


  public void doPost( HttpServletRequest request, HttpServletResponse response )
              throws ServletException {
    doGet( request, response );
  }
}