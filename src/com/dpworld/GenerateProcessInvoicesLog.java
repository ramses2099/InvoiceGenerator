package com.dpworld;

public class GenerateProcessInvoicesLog {

    private  DBConnectionSQL dbConnectionSQL;
    public GenerateProcessInvoicesLog(){
        dbConnectionSQL = new DBConnectionSQL();
    }

    public void generatedProcessInvoicesLog(ProcessInvoicesLog processInvoicesLog){
        try{
            dbConnectionSQL.addProcessInvoicesLog(processInvoicesLog);
        }catch (Exception ex){
            System.out.println("GenerateProcessInvoicesLog generatedGenerateProcessInvoicesLog Error: " + ex.getMessage());
        }
    }

}
