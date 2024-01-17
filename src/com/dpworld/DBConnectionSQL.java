package com.dpworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.apache.log4j.*;


public class DBConnectionSQL {


    private Connection con;
    private PreparedStatement ps;
    public DBConnectionSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ConnectionString connectionString = new ConnectionString();
            Credential credential = connectionString.getValue(DBConnectionType.N4INVOICEGENERATOR);
            con = DriverManager.getConnection(credential.get_url());
        } catch (Exception ex) {
            System.out.println("DBConnectionSQL Error: " + ex.getMessage());
        }
    }
    //
    public void addProcessInvoices(ProcessInvoices processInvoices) {
        try {
            String sql = "INSERT INTO [dbo].[ProcessInvoices]([DRAFT_NBR],[FINAL_NBR],[INVOCE_NBR_MSC],[FILENAME],[STATUS],[CREATED])VALUES(?,?,?,?,1,GETDATE());";
            if (con == null) {
                throw new Exception("DBConnectionSQL no initializate");
            }
            //
            PreparedStatement statement = con.prepareStatement(sql);
            //------------------------------------------------------//
            statement.setLong(1, processInvoices.get_drafNbr());
            statement.setString(2, processInvoices.get_finalNbr());
            statement.setString(3, processInvoices.get_invoiceNbrMsc());
            statement.setString(4,processInvoices.get_filename());

            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("A new user was inserted successfully");
            }
            //------------------------------------------------------//
            con.close();
        } catch (Exception e) {
            System.out.println("DBConnectionSQL addProcessInvoices Error: " + e.getMessage());
        }
    }
    //
    public void addProcessInvoicesLog(ProcessInvoicesLog processInvoicesLog) {
        try {
            String sql = "INSERT INTO [dbo].[ProcessInvoicesLog]([DRAFT_NBR],[FINAL_NBR],[INVOICE_NBR_MSC],[STATUS],[MESSAGE],[CREATED]) \n" +
                    "VALUES(?,?,?,1,?,GETDATE())";
            if (con == null) {
                throw new Exception("DBConnectionSQL no initializate");
            }
            //
            PreparedStatement statement = con.prepareStatement(sql);
            //------------------------------------------------------//
            statement.setLong(1, processInvoicesLog.get_draftNbr());
            statement.setString(2, processInvoicesLog.get_finalNbr());
            statement.setString(3, processInvoicesLog.get_invoiceNbrMsc());
            statement.setString(4,processInvoicesLog.get_message());

            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("A new log was inserted successfully");
            }
            //------------------------------------------------------//
            con.close();
        } catch (Exception e) {
            System.out.println("DBConnectionSQL addProcessInvoicesLog Error: " + e.getMessage());
        }
    }

}
