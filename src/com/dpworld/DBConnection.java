package com.dpworld;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private Connection con;
    private PreparedStatement ps;

    public DBConnection(DBConnectionType connectionType) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            ConnectionString connectionString = new ConnectionString();
            if (connectionType == DBConnectionType.N4) {
                Credential credential = connectionString.getValue(DBConnectionType.N4);
                con = DriverManager.getConnection(credential.get_url(), credential.get_user(), credential.get_pwd());
            } else if (connectionType == DBConnectionType.BILLING) {
                Credential credential = connectionString.getValue(DBConnectionType.BILLING);
                con = DriverManager.getConnection(credential.get_url(), credential.get_user(), credential.get_pwd());
            } else {
                System.out.println("DBConnection Type no implement");
            }
        } catch (Exception ex) {
            System.out.println("DBConnection Error: " + ex.getMessage());
        }
    }

    public List<InvoiceTypeItem> getInvoiceTypeItemData() {
        List<InvoiceTypeItem> list = new ArrayList<InvoiceTypeItem>();
        try {
            String sql = "SELECT gkey, id  FROM bil_invoice_type";
            if (con == null) {
                throw new Exception("DBConnection no initializate");
            }

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String gkey = result.getString("gkey");
                String id = result.getString("id");
                list.add(new InvoiceTypeItem(gkey, id));
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getInvoiceTypeItemData Error: " + e.getMessage());
        }
        return list;
    }

    public List<InvoiceDetails> getInvoiceDetails(String invtype) {
        List<InvoiceDetails> list = new ArrayList<InvoiceDetails>();
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT i.draft_nbr,i.final_nbr, i.status, c.name as Payee,\n" +
                    "i.finalized_date,\n" +
                    "CASE WHEN bit.applied_to_class = 'INV' THEN 'UNIT' ELSE 'MARINE' END AS applied\n" +
                    "FROM bil_invoices i \n" +
                    "INNER JOIN bil_customer c ON c.gkey = i.payee_customer_gkey\n" +
                    "INNER JOIN bil_invoice_type bit ON bit.gkey = i.invtype_gkey\n" +
                    "WHERE i.status  ='FINAL' AND i.payee_customer_gkey in(10611, 10624)\n" +
                    "AND EXTRACT(YEAR FROM i.finalized_date) = EXTRACT(YEAR FROM SYSDATE)\n" +
                    "AND EXTRACT(MONTH FROM i.finalized_date) >= 11\n" +
                    "-- AND EXTRACT(MONTH FROM i.finalized_date) = EXTRACT(MONTH FROM SYSDATE)\n" +
                    "-- AND (i.flex_date05 IS NULL OR i.flex_date05 = NULL) \n"+
                    "AND  i.invtype_gkey = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, invtype);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String draft_nbr = result.getString("draft_nbr");
                String final_nbr = result.getString("final_nbr");
                String status = result.getString("status");
                String Payee = result.getString("Payee");
                String applied = result.getString("applied");
                list.add(new InvoiceDetails(draft_nbr, final_nbr, status, Payee,applied));
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getInvoiceItem Error: " + e.getMessage());
        }
        return list;
    }

    public VesselVisit getVesselVisitByVisitId(String visit_id) {
        VesselVisit vesselVisit = null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "cv.id as visit_id,\n" +
                    "vvd.ib_vyg,\n" +
                    "vvd.ob_vyg,\n" +
                    "vsls.lloyds_id as lloyds,\n" +
                    "vsls.name as vessel_name\n" +
                    "FROM argo_carrier_visit cv\n" +
                    "INNER JOIN argo_visit_details avd ON cv.cvcvd_gkey = avd.gkey\n" +
                    "INNER JOIN vsl_vessel_visit_details vvd ON vvd.vvd_gkey = avd.gkey\n" +
                    "INNER JOIN vsl_vessel_visit_lines vvl ON vvl.vvd_gkey = vvd.vvd_gkey\n" +
                    "INNER JOIN vsl_vessels vsls ON vsls.gkey = vvd.vessel_gkey\n" +
                    "INNER JOIN vsl_vessel_classes vvc ON vvc.gkey = vsls.vesclass_gkey\n" +
                    "WHERE cv.id = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, visit_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String visitid = result.getString("visit_id");
                String ib_vyg = result.getString("ib_vyg");
                String ob_vyg = result.getString("ob_vyg");
                String lloyds = result.getString("lloyds");
                String vessel_name = result.getString("vessel_name");

                vesselVisit = new VesselVisit(visit_id, ib_vyg, ob_vyg, lloyds, vessel_name);
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getVesselVisitByVisitId Error: " + e.getMessage());
        }
        return vesselVisit;
    }

    public Invoice getInvoiceByFinalNumber(String final_number) {
        Invoice invoice = null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT i.gkey,\n" +
                    "i.draft_nbr,\n" +
                    "i.final_nbr, \n" +
                    "i.status,\n" +
                    "c.id as payeeid,\n" +
                    "c.name as payee,\n" +
                    "i.customer_reference,\n" +
                    "i.flex_string06, \n" +
                    "i.finalized_date \n" +
                    "FROM bil_invoices i \n" +
                    "INNER JOIN bil_customer c ON c.gkey = i.payee_customer_gkey \n" +
                    "WHERE i.status  ='FINAL' and i.final_nbr = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, final_number);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Long gkey = result.getLong("gkey");
                Long draft_nbr = result.getLong("draft_nbr");
                String final_nbr = result.getString("final_nbr");
                String status = result.getString("status");
                String payeeid = result.getString("payeeid");
                String payee = result.getString("payee");
                String visitId = result.getString("flex_string06");
                String customer_reference = result.getString("customer_reference");

                invoice = new Invoice(gkey, draft_nbr, final_nbr, status, payeeid, payee, visitId, customer_reference);
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getInvoiceByFinalNumber Error: " + e.getMessage());
        }
        return invoice;
    }

    public Double getInvoiceTotalAmoutByFinalNumber(String final_number) {
        Double totalAmount = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "SUM(BII.amount) AS AMOUNT\n" +
                    "FROM bil_invoices BI \n" +
                    "INNER JOIN bil_invoice_items BII ON BII.invoice_gkey = BI.gkey\n" +
                    "WHERE BI.status  ='FINAL' and BI.final_nbr = ?\n" +
                    "GROUP BY BII.amount");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, final_number);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                totalAmount += result.getDouble("AMOUNT");
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getInvoiceTotalAmoutByFinalNumber Error: " + e.getMessage());
        }
        return totalAmount;
    }

    public List<InvoiceItem> getInvoiceItmesByFinalNumber(String final_number) {
        List<InvoiceItem> list = new ArrayList<InvoiceItem>();
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "BII.gkey,\n" +
                    "BII.extract_gkey,\n" +
                    "BII.event_id,\n" +
                    "BII.amount,\n" +
                    "BII.quantity,\n" +
                    "BII.from_date,\n" +
                    "BT.id as tariff, \n" +
                    "BT.description, BT.long_description,\n" +
                    "BTR.effective_date\n" +
                    "FROM bil_invoices BI \n" +
                    "INNER JOIN bil_invoice_items BII ON BII.invoice_gkey = BI.gkey\n" +
                    "INNER JOIN bil_tariff_rates BTR ON BTR.gkey = BII.rate_gkey \n" +
                    "INNER JOIN bil_tariffs BT ON BT.gkey = BTR.tariff_gkey  \n" +
                    "WHERE BI.status  ='FINAL' and BI.final_nbr =?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, final_number);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Long gkey = result.getLong("gkey");
                Long extract_gkey = result.getLong("extract_gkey");
                String event_id = result.getString("event_id");
                Double amount = result.getDouble("amount");
                int quantity = result.getInt("quantity");
                Date from_date = result.getDate("from_date");
                String tariff = result.getString("tariff");
                String description = result.getString("description");
                String long_description = result.getString("long_description");
                Date effective_date = result.getDate("effective_date");

                //
                list.add(new InvoiceItem(gkey, extract_gkey, event_id, amount, quantity,from_date,
                        tariff, description, long_description, effective_date));
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getInvoiceItmesByFinalNumber Error: " + e.getMessage());
        }
        return list;
    }

    public ChargebleMarine getChargebleMarineDetails(Long gkey){
        ChargebleMarine cme =  null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "ACME.gkey,\n" +
                    "ACME.event_type_id,\n" +
                    "ACME.ib_voy_nbr,\n" +
                    "ACME.ob_voy_nbr,\n" +
                    "ACME.vv_id,\n" +
                    "ACME.vsl_name,\n" +
                    "VV.lloyds_id\n" +
                    "FROM argo_chargeable_marine_events ACME \n" +
                    "INNER JOIN vsl_vessel_visit_details VVD ON VVD.vvd_gkey = ACME.vvd_gkey\n" +
                    "INNER JOIN vsl_vessels VV ON VV.gkey = VVD.vessel_gkey\n" +
                    "WHERE ACME.batch_id = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setLong(1, gkey);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Long cue_gkey = result.getLong("gkey");
                String event_type = result.getString("event_type");
                String vv_id = result.getString("vv_id");
                String ib_id = result.getString("ib_voy_nbr");
                String ob_id = result.getString("ob_voy_nbr");
                String vsl_name = result.getString("ib_carrier_name");
                String lloyds_id = result.getString("ob_vessel_lloyds_id");
                //
                cme = new ChargebleMarine(cue_gkey, event_type,vv_id,ib_id,ob_id,lloyds_id,vsl_name);
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getChargebleMarinetDetails Error: " + e.getMessage());
        }
        return cme;
    }

    public ChargebleUnit getChargebleUnitDetails(Long gkey){
        ChargebleUnit cue =  null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "ACUE.gkey,\n" +
                    "ACUE.event_type,\n" +
                    "ACUE.ib_vessel_lloyds_id,\n" +
                    "ACUE.ib_id,\n" +
                    "ACUE.ib_visit_id,\n" +
                    "ACUE.ib_carrier_name,\n" +
                    "ACUE.ob_vessel_lloyds_id,\n" +
                    "ACUE.ob_id,\n" +
                    "ACUE.ob_visit_id,\n" +
                    "ACUE.ob_carrier_name,\n" +
                    "ACUE.booking_nbr,\n" +
                    "ACUE.bl_nbr,\n" +
                    "ACUE.event_start_time,\n" +
                    "ACUE.event_end_time\n" +
                    "FROM argo_chargeable_unit_events ACUE WHERE ACUE.gkey =?");

            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setLong(1, gkey);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Long cue_gkey = result.getLong("gkey");
                String event_type = result.getString("event_type");
                Long ib_vessel_lloyds_id = result.getLong("ib_vessel_lloyds_id");
                String ib_id = result.getString("ib_id");
                String ib_visit_id = result.getString("ib_visit_id");
                String ib_carrier_name = result.getString("ib_carrier_name");
                Long ob_vessel_lloyds_id = result.getLong("ob_vessel_lloyds_id");
                String ob_id = result.getString("ob_id");
                String ob_visit_id = result.getString("ob_visit_id");
                String ob_carrier_name = result.getString("ob_carrier_name");
                String booking_nbr = result.getString("booking_nbr");
                String bl_nbr = result.getString("bl_nbr");
                Date event_start_time = result.getDate("event_start_time");
                Date event_end_time = result.getDate("event_end_time");
                //
                cue = new ChargebleUnit(cue_gkey, event_type,ib_vessel_lloyds_id, ib_id, ib_visit_id, ib_carrier_name,
                        ob_vessel_lloyds_id, ob_id,ob_visit_id,ob_carrier_name,booking_nbr, bl_nbr,event_start_time,event_end_time);
            }

            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection getChargebleUnitDetails Error: " + e.getMessage());
        }
        return cue;
    }


}
