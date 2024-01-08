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
            Integer segmentCount = 0;
            Double moaTotal = 0.0;
            Date today = new Date();
            String yymmdd = Utils.getDateString(today, "yyMMdd");
            String hhmm = Utils.getDateString(today, "HHmm");
            String controlNumber = Utils.getControlNumber();

            String UNB = String.format("UNB+UNOA:3+DPWCAU:ZZ+MSCU:ZZ+%s:%s+%s'\n", yymmdd, hhmm, controlNumber);
            bw.write(UNB);
            //-------------------------------------------//
            // --------Segment UNH----------------------//
            String numberRef = String.format("0020%s%s", yymmdd, hhmm);
            String UNH = String.format("UNH+%s+INVOIC:D:96A:UN:EAN008'\n", numberRef);
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
            Integer countLin = 1;
            dbConnection = new DBConnection(DBConnectionType.BILLING);
            List<InvoiceItem> invoiceItems = dbConnection.getInvoiceItmesByFinalNumber(nbrFinal);
            if (invoiceItems == null) {
                throw new Exception("Invoice Items are null");
            }
            //-------DETAILS INVOICE---------------------//
            if (invoiceItems.size() > 0) {
                for (InvoiceItem item : invoiceItems) {
                    if (item.get_amount() > 0) {
                        //-------------------------------------------//
                        // --------Segment LIN----------------------//
                        String LIN = String.format("LIN+%s++:EN'", countLin);
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
                        //-------------------------------------------//
                        //--------Segment EQD-----------------------//
                        String EQD = String.format("EQD+CN+%s+18'\n", item.get_event_id());
                        bw.write(EQD);
                        //-------------------------------------------//
                        //--------Segment PRI-----------------------//
                        String PRI = String.format("PRI+AAA:%s'\n", item.get_amount());
                        bw.write(PRI);
                        //-------------------------------------------//
                        //--------Segment RFF+BM-----------------------//
                        String RFF_BM = String.format("RFF+BM:%s\n'", "MEDUQM026868A");
                        bw.write(RFF_BM);
                        //-------------------------------------------//
                        //--------Segment RFF+VON--------------------//
                        String RFF_VON = String.format("RFF+VON:%s'\n", invoice.get_vessel_visit_id());
                        bw.write(RFF_VON);
                        //-------------------------------------------//
                        //--------Segment DTM_7----------------------//
                        String DTM_7 = String.format(" DTM+7:%s:102'\n", Utils.getDateString(today, "yyyyMMdd"));
                        bw.write(DTM_7);
                        //-------------------------------------------//
                        //--------Segment LOC_1----------------------//
                        String vesselClass = "";
                        String vesselName = "";
                        String LOC_1 = String.format("LOC+1+DOCAU:139:6+%s:146:11:%s+DOCAUDW:ZZZ:6'\n", vesselClass, vesselName);
                        bw.write(LOC_1);
                        //-------------------------------------------//
                        //--------Segment TAX----------------------//
                        String TAX = " TAX+7+VAT+++:::0+S'\n";
                        bw.write(TAX);
                        //-------------------------------------------//
                        //--------Segment MOA----------------------//
                        String MOA = "MOA+124:0.0'\n";
                        bw.write(MOA);


                        countLin++;
                        segmentCount += 14;
                        moaTotal += item.get_amount();

                    }
                }
            }
            //---------------------------------------//
            //---------------------------------------//
            // --------Segment UNS------------------//
            String UNS = "UNS+S'\n";
            bw.write(UNS);
            //---------------------------------------//
            // --------Segment CNT_1----------------//
            String CNT_1 = String.format("CNT+1:%s'\n", countLin);
            bw.write(CNT_1);
            //---------------------------------------//
            // --------Segment CNT_1----------------//
            String CNT_2 = String.format("CNT+2:%s'\n", countLin);
            bw.write(CNT_2);
            //---------------------------------------//
            // --------Segment MOA----------------//
            String MOA = String.format(" MOA+124:%s'\n", moaTotal);
            bw.write(MOA);
            //---------------------------------------//
            // --------Segment TAX----------------//
            String TAX = "TAX+7+VAT:::0.0'\n";
            bw.write(TAX);
            //---------------------------------------//
            // --------Segment UNT----------------//
            String UNT = String.format("UNT+%s+%s'\n",segmentCount,numberRef);
            bw.write(UNT);
            //---------------------------------------//
            // --------Segment UNZ----------------//
            String UNZ = String.format("UNZ+1+%s'\n",controlNumber);
            bw.write(UNZ);

            bw.close();

        } catch (Exception ex) {
            System.out.println("Method createEDI " + ex.getMessage());
        }

    }


}
