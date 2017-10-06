package com.intiro.itr.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.log.IntiroLog;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnect {

  private static DataSource dataSource;

  static {
    try {
      dataSource = (DataSource) new InitialContext().lookup("jdbc/itr");
    } catch (NamingException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static void testMode() {
    if (!(dataSource instanceof TestDataSource)) {
      dataSource = new TestDataSource();
    }
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  protected synchronized StringRecordset executeQuery(StringBuffer sql) throws Exception {
    Statement stmt = null;
    StringRecordset retval = null;
    ResultSet rs = null;
    Connection conn = null;
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      retval = new StringRecordset(rs);
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".executeQuery(): Exception occured, exception = " + e.getMessage());
      throw new Exception(e.getMessage());
    } finally {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    return retval;
  }

  protected synchronized boolean executeUpdate(StringBuffer sql) throws Exception {
    boolean retval = false;
    Statement stmt = null;
    Connection conn = null;
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      int rowsEffected = stmt.executeUpdate(sql.toString());
      if (rowsEffected > 0) {
        retval = true;
      }
    } catch (SQLException e) {
      IntiroLog.error(getClass(), getClass().getName() + ".executeUpdate(): Exception occured, exception = " + e.getMessage());
      throw new Exception(e.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    return retval;
  }
}

class TestDataSource implements DataSource {

  private Driver drv = null;
  private Connection myConnection = null;

  public void setDriver() throws SQLException {
    if (drv == null) {
      try {
        drv = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
        IntiroLog.error(DBConnect.class, DBConnect.class.getName() + ".getConnection(): Exception occured, exception = " + e.getMessage());
        throw new SQLException(e.getMessage());
      }
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    setDriver();
    if (myConnection == null || myConnection.isClosed()) {
      try {
        myConnection = DriverManager.getConnection("jdbc:mysql://localhost/itr", "root", "admin");
      } catch (SQLException e) {
        IntiroLog.error(DBConnect.class, DBConnect.class.getName() + ".getConnection(): Exception occured, exception = " + e.getMessage());
        throw new SQLException(e.getMessage());
      }
    }
    return myConnection;
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
