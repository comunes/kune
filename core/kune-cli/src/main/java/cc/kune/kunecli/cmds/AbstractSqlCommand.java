/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.kune.kunecli.cmds;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;

public class AbstractSqlCommand extends Command {

  public static class AbstractSqlSampleICommand implements ICommandExecutor {
    public static final Log LOG = LogFactory.getLog(AbstractSqlSampleICommand.class);

    private final String[] queries;

    public AbstractSqlSampleICommand(final String[] queries) {
      this.queries = queries;
    }

    @Override
    public void execute(final ParseResult result) throws ExecutionException {
      // https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-statements.html
      final Properties prop = new Properties();
      InputStream input = null;

      try {
        final String properties = result.getParameterCount() > 0 ? result.getParameterValue(0).toString()
            : "/etc/kune/kune.properties";
        input = new FileInputStream(properties);
        prop.load(input);
        final String user = prop.getProperty("kune.db.user");
        final String pass = prop.getProperty("kune.db.password");
        final String url = prop.getProperty("kune.db.url");
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        try {
          conn = DriverManager.getConnection(url, user, pass);
          stmt = conn.createStatement();
          for (final String query : queries) {
            rs = stmt.executeQuery(query);
          }
          final ResultSetMetaData rsmd = rs.getMetaData();
          final int numColumns = rsmd.getColumnCount();
          for (int i = 1; i <= numColumns; i++) {
            System.out.print(rsmd.getColumnName(i) + " ");
          }
          System.out.println("");
          while (rs.next()) {
            for (int i = 1; i <= numColumns; i++) {
              System.out.print(rs.getString(i) + " ");
            }
            System.out.println("");
          }
        } catch (final SQLException ex) {
          LOG.error("SQLException: " + ex.getMessage());
          LOG.error("SQLState: " + ex.getSQLState());
          LOG.error("VendorError: " + ex.getErrorCode());
        } finally {
          // it is a good idea to release
          // resources in a finally{} block
          // in reverse-order of their creation
          // if they are no-longer needed

          if (rs != null) {
            try {
              rs.close();
            } catch (final SQLException sqlEx) {
            } // ignore

            rs = null;
          }

          if (stmt != null) {
            try {
              stmt.close();
            } catch (final SQLException sqlEx) {
            } // ignore

            stmt = null;
          }
        }
      } catch (final IOException ex) {
        LOG.error(ex);
        ex.printStackTrace();
      } finally {
        if (input != null) {
          try {
            input.close();
          } catch (final IOException ex) {
            LOG.error(ex);

          }
        }
      }
    }
  }

  public AbstractSqlCommand(final String title, final String description, final String... queries)
      throws InvalidSyntaxException {
    super(title + " [<kune.properties:string>]",
        description
            + " (if kune.properties is not defined we use /etc/kune/kune.properties for get the db parameters)",
        new AbstractSqlSampleICommand(queries));
  }
}
