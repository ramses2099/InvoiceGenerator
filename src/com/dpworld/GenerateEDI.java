package com.dpworld;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class GenerateEDI {

    public GenerateEDI() {

    }

    public void createEDI(String nbrFinal) {
        DBConnection dbConnection = null;
        Translation translation = null;
        try {
            //---------------------------------------------------------------------//
            //---------------------GET DATA---------------------------------------//
            dbConnection = new DBConnection(DBConnectionType.BILLING);
            Invoice invoice = dbConnection.getInvoiceByFinalNumber(nbrFinal);
            if (invoice == null) {
                throw new Exception("Invoice is null");
            }
            //-------------------------------------------------------------------//
            //------------------FILE NAME----------------------------------------//
            String fileName = String.format("%s.edi", Utils.getFilename(invoice));
            String path = String.format("\\\\192.168.6.29\\edi\\PRODUCTION\\OUT\\INVOICE\\MSC\\InvoiceGenerator\\%s", fileName);

            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //-------------------------------------------//
            //----------WRITE---------------------------//
            //-------------------------------------------//

            //-------------------------------------------//
            // --------Segment UNB----------------------//
            Date today = new Date();
            String yymmdd = Utils.getDateString(today, "yyMMdd");
            String hhmm = Utils.getDateString(today, "HHmm");
            String controlNumber = Utils.getControlNumber();
            String UNB = String.format("UNB+UNOA:3+DPWCAU:ZZ+MSCU:ZZ+%s:%s+%s'\n", yymmdd, hhmm, controlNumber);
            bw.write(UNB);
            //-------------------------------------------//
            // --------Segment UNH----------------------//
            String UNH = "UNH+002009350051+INVOIC:D:96A:UN:EAN008'\n";
            bw.write(UNH);
            //-------------------------------------------//
            // --------Segment BGM----------------------//
            String BGM = String.format("BGM+380+6422%S+9'\n", invoice.get_final_nbr());
            bw.write(BGM);
            //-------------------------------------------//
            // --------Segment DTM+137----------------------//
            String DTM_137 = String.format("DTM+137:%s:102'\n", Utils.getDateString(today, "yyyyMMdd"));
            bw.write(DTM_137);
            //-------------------------------------------//
            // --------Segment RFF+CR--------------------//
            String RFF_CR = String.format("RFF+CR:%s'\n", invoice.get_customer_references());
            bw.write(RFF_CR);
            //-------------------------------------------//
            // --------Segment RFF+IV--------------------//
            String RFF_IV = String.format("RFF+IV:6422%s'\n", invoice.get_final_nbr());
            bw.write(RFF_IV);
            //-------------------------------------------//
            // --------Segment--------------------------//
            String NAD_IV = "NAD+IV+MSCR+CHEMIN RIEU, 12-14:1208+++GENEVA+++CH'\n";
            bw.write(NAD_IV);
            String NAD_BY = "NAD+BY+8200+MEDITERRANEAN SHIPPING COMPANY (R)'\n";
            bw.write(NAD_BY);
            String NAD_SU = "NAD+SU+1000143468::9++DPW CAUCEDO+201 MISSION ST+SAN FRANCISCO+CA+94105+US'\n";
            bw.write(NAD_SU);
            String RFF_VA = "RFF+VA:GB0448826918'\n";
            bw.write(RFF_VA);
            String CUX = "CUX+2:USD:4++1.1'\n";
            bw.write(CUX);

            //-------------------------------------------//
            //---------DETAILS--------------------------//
            //---------COUNT SEGMENT - 14--------------//
            Integer CountLin = 1;
            dbConnection = new DBConnection(DBConnectionType.BILLING);
            List<InvoiceItem> invoiceItems = dbConnection.getInvoiceItmesByFinalNumber(nbrFinal);
            if (invoiceItems == null) {
                throw new Exception("Invoice Items are null");
            }
            //---------------------------------------//
            if (invoiceItems.size() > 0) {
                for (InvoiceItem item : invoiceItems) {
                    if (item.get_amount() > 0) {
                        //-------------------------------------------//
                        // --------Segment LIN----------------------//
                        String LIN = String.format("LIN+%s++:EN'", CountLin);
                        bw.write(LIN);
                        //-------------------------------------------//
                        // --------Segment IMD----------------------//
                        translation = new Translation(TranslationType.ID);
                        String tariffs = translation.getValue(item.get_tariff());
                        //-----------------------------------------//
                        translation = new Translation(TranslationType.DESCRIPTION);
                        String tariff_desc = translation.getValue(item.get_description());
                        //-----------------------------------------//
                        String IMD = String.format("IMD+F++%s:::%s:%s'\n", tariffs, tariff_desc, tariff_desc);
                        bw.write(IMD);
                        //-------------------------------------------//
                        // --------Segment QTY----------------------//
                        String QTY = String.format("QTY+47:%s:CH'\n", item.get_quantity());
                        bw.write(QTY);
                        //-------------------------------------------//
                        //--------Segment DTM_194-------------------//
                        String DTM_194 = String.format("DTM+194:%s:102'\n", Utils.getDateString(today, "yyyyMMdd"));
                        bw.write(DTM_194);
                        //-------------------------------------------//
                        //--------Segment DTM_206-------------------//
                        String DTM_206 = String.format("DTM+206:%s:102'\n", Utils.getDateString(today, "yyyyMMdd"));
                        bw.write(DTM_206);
                        //-------------------------------------------//
                        //--------Segment DTM_318-------------------//
                        String DTM_318 = String.format("DTM+318:%s:102'\n", Utils.getDateString(today, "yyyyMMdd"));
                        bw.write(DTM_318);

                        CountLin++;
                    }
                }

            /*---
            LIN+1++:EN'
            IMD+F++ECR000381:::ISPS FULL TRANSHIPMENT:ISPS FULL TRANSHIPMENT'
            QTY+47:1.0:CH'
            DTM+194:20231122:102'
            DTM+206:20231122:102'
            DTM+318:20231122:102'
            EQD+CN+MEDU7330408+18'
            PRI+AAA:2.38'
            RFF+BM:MEDUQM026868A'
            RFF+VON:ASLPD345A'
            DTM+7:20230116:102'
            LOC+1+DOCAU:139:6+9314947:146:11:AS CAMELLIA+DOCAUDW:ZZZ:6'
            TAX+7+VAT+++:::0+S'
            MOA+124:0.0'
            --*/
            }


            bw.close();

        } catch (Exception ex) {
            System.out.println("Method createEDI " + ex.getMessage());
        }

    }


}
