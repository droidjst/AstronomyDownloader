
/*
    Copyright 2015 Joseph Tranquillo {name of copyright owner}

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */


package com.droidjst.astronomydownloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseUtil
{
    private final static SimpleDateFormat date_apod = new SimpleDateFormat("yyMMdd");
    private final static SimpleDateFormat date_custom = new SimpleDateFormat("MMM dd, yyy");
    
    public boolean addExtendedArchiveItem(String image_src, int[] width_height, String date, String credit, String explanation)
    {
        Connection connection = null;
        Statement statement_select = null;
        Statement statement_update = null;
        
        ResultSet resultset = null;
        
        Database database = new Database();
        
        Object[] tuple = database.getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement_select = (Statement) tuple[1];
        
        boolean exc_thrown = false;
        
        try
        {
            final String sql_getid = "SELECT _id FROM apod WHERE date = %d;";
            
            statement_select = connection.createStatement();
            
            date = Long.toString(date_apod.parse(date).getTime());
            
            String sql_select = String.format(sql_getid, Long.valueOf(date));
            
            resultset = statement_select.executeQuery(sql_select);
            
            resultset.next();
            
            int _id = resultset.getInt(1);
            
            resultset.close();
            
            statement_select.close();
            
            final String format = "UPDATE apod SET url = '%s', width = %d, height = %d, credit = '%s', explanation = '%s' WHERE _id = %d;";
            
            statement_update = connection.createStatement();
            
            credit = credit.replace("'", "''");
            explanation = explanation.replace("'", "''");
            
            String sql_insert = String.format(format, image_src, width_height[0], width_height[1], credit, explanation, _id);
            
            statement_update.executeUpdate(sql_insert);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            
            exc_thrown = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            exc_thrown = true;
        }
        finally
        {
            DatabaseUtil.finalizeConnection(connection, statement_select, statement_update);
            DatabaseUtil.finalizeResultSet(resultset);
        }
        
        return exc_thrown == false;
    }
    
    public void addBasicArchive() throws SQLException
    {
        JsoupUtil jsouputil = new JsoupUtil();
        
        ArrayList<String[]> archive;
        
        archive = jsouputil.getHTMLRefs(Const.URL_NASA_APOD + "archivepix.html");
        
        Collections.reverse(archive);
        
        Connection connection = null;
        Statement statement = null;
        
        Database database = new Database();
        
        Object[] tuple = database.getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        try
        {
            final String format = "INSERT INTO apod (date, title) VALUES (%d, '%s');";
            
            for(String[] item : archive)
            {
                statement = connection.createStatement();
                
                item[1] = item[1].replace("'", "''");
                
                String sql_insert = String.format(format, parseDate(item[0]), item[1]);
                
                statement.executeUpdate(sql_insert);
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            DatabaseUtil.finalizeConnection(connection, statement);
        }
    }
    
    public String[] getURLs()
    {
        ArrayList<String> urls = new ArrayList<>();
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        Database database = new Database();
        
        Object[] tuple = database.getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        boolean exc_thrown = false;
        
        try
        {
            String sql_statement = "SELECT date FROM apod;";
            
            statement = connection.createStatement();
            
            resultset = statement.executeQuery(sql_statement);
            
            while(resultset.next())
            {
                urls.add("ap" + date_apod.format(resultset.getLong(1)) + ".html");
            }
            
            urls.trimToSize();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            
            exc_thrown = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            exc_thrown = true;
        }
        finally
        {
            DatabaseUtil.finalizeConnection(connection, statement);
        }
        
        if(exc_thrown)
        {
            return null;
        }
        else
        {
            String[] _urls = new String[urls.size()];
            
            for(int a = 0 ; a < _urls.length ; a++)
            {
                _urls[a] = urls.get(a);
            }
            
            return _urls;
        }
    }
    
    public long parseDate(String string)
    {
        try
        {
            return date_apod.parse(string.substring(2, 8)).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public static String dateToAPODString(long date)
    {
        return date_apod.format(date);
    }
    
    public static String dateToCustomString(long date)
    {
        return date_custom.format(date);
    }
    
    public static void finalizeConnection(Connection connection, Statement ... statement)
    {
        if(statement != null)
        {
            for(Statement _statement : statement)
            {
                try
                {
                    _statement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        if(connection != null)
        {
            try
            {
                connection.commit();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void finalizeStatement(Statement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void finalizeResultSet(ResultSet resultset)
    {
        if(resultset != null)
        {
            try
            {
                resultset.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
