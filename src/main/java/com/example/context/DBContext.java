package com.example.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String SERVER_NAME = "localhost";
    private static final String DB_NAME = "DoAnThoiTrang_Nhom1";
    private static final String PORT_NUMBER = "1433";
    private static final String USER_ID = "sa";
    private static final String PASSWORD = "08012006@"; // <-- Sửa số 123 thành mật khẩu SQL Server máy cậu

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER
                + ";databaseName=" + DB_NAME
                + ";encrypt=true;trustServerCertificate=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, USER_ID, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("THANH CONG ROI PHUONG ANH OI!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}