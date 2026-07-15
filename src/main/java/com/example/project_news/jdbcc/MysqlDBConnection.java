package com.example.project_news.jdbcc;

import java.sql.*;

public class MysqlDBConnection {
    public static Connection getMySQLDBConnection()
    {
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","Diya@2005");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
