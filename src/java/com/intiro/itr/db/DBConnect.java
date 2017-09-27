/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.intiro.itr.util.StringRecordset;
import com.intiro.toolbox.log.IntiroLog;

class DBConnect {

  //~ Instance/static variables ........................................................................................

  private static Driver drv = null;
  private static Connection myConnection = null;

  //~ Methods ..........................................................................................................

  public void closeDB() {
    try {
      if (myConnection != null) {
        myConnection.close();
        myConnection = null;
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".finalize(): Exception occured, exception = " + e.getMessage());
      }
    }
  }

  protected synchronized StringRecordset executeQuery(StringBuffer sql) throws Exception {
    Statement myStatement = null;
    StringRecordset retval = null;

    try {

      /*Get connection*/
      myConnection = getConnection();

      /*Create statement and execute query*/
      myStatement = myConnection.createStatement();

      ResultSet rs = myStatement.executeQuery(sql.toString());

      /*Create the StringRecordSet*/
      retval = new StringRecordset(rs);

      /*Close resultset and statement*/
      rs.close();
      myStatement.close();
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".executeQuery(): Exception occured, exception = " + e.getMessage());
      }

      throw new Exception(e.getMessage());
    }

    return retval;
  }

  protected synchronized boolean executeUpdate(StringBuffer sql) throws Exception {
    boolean retval = false;
    Statement myStatement = null;

    try {
      myConnection = getConnection();
      myStatement = myConnection.createStatement();

    
      int rowsEffected = myStatement.executeUpdate(sql.toString());

      if (rowsEffected > 0) {
        retval = true;
      }
      if (myStatement != null) {
        myStatement.close();
      }
    } catch (Exception e) {
      if (IntiroLog.e()) {
        IntiroLog.error(getClass(), getClass().getName() + ".executeUpdate(): Exception occured, exception = " + e.getMessage());
      }

      throw new Exception(e.getMessage());
    }

    return retval;
  }

  private synchronized Connection getConnection() throws Exception {
    if (drv == null) {
      try {

        /*Driver*/
        //drv = (Driver) Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
        drv = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();

      } catch (Exception e) {
        if (IntiroLog.e()) {
          IntiroLog.error(getClass(), getClass().getName() + ".getConnection(): Exception occured, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
    if (myConnection == null || myConnection.isClosed()) {
      try {

        /*Connect to database*/
        //myConnection = DriverManager.getConnection("jdbc:odbc:ITR_DB");
        myConnection = DriverManager.getConnection("jdbc:mysql:///iTR", "root", "admin");
        //myConnection = DriverManager.getConnection("jdbc:mysql:///iTR", "root", "root");
      } catch (Exception e) {
        if (IntiroLog.e()) {
          IntiroLog.error(getClass(), getClass().getName() + ".getConnection(): Exception occured, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
    
    return myConnection;
  }
}