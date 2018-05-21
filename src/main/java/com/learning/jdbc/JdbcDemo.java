package com.learning.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

public class JdbcDemo {
    
    @Test
    public void test() throws SQLException, ClassNotFoundException {
        Class<?> driver = Class.forName("");
        Connection con = DriverManager.getConnection("");
        PreparedStatement pre = con.prepareStatement("");
        pre.executeQuery();
    }
}
