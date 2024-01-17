package com.dpworld;

public class GenerateProcessInvoices {
    private  DBConnectionSQL dbConnectionSQL;
    public GenerateProcessInvoices(){
        dbConnectionSQL = new DBConnectionSQL();
    }

    public void generatedProcessInvoices(ProcessInvoices processInvoices){
        try{
            dbConnectionSQL.addProcessInvoices(processInvoices);
        }catch (Exception ex){
            System.out.println("GenerateProcessInvoicesLog generatedGenerateProcessInvoicesLog Error: " + ex.getMessage());
        }
    }
}
