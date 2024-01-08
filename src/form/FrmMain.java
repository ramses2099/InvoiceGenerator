package form;

import com.dpworld.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class FrmMain extends JFrame {

    private JPanel pnlMain;
    private JButton btnGenerateEDI;
    private JButton btnSearch;
    private JComboBox cbxInvoiceType;
    private JLabel lblInvoiceType;
    private JPanel pnlSearch;
    private JPanel pnlTable;
    private JLabel lblRowCount;
    private JPanel pnlRowCount;
    private JTable tblView;
    private JScrollPane jscPnView;

    private String _nbrfinal;

    private DBConnection dbConnection;

    public String getGkeyInvoiceType() {
        return gkeyInvoiceType;
    }

    public void setGkeyInvoiceType(String gkeyInvoiceType) {
        this.gkeyInvoiceType = gkeyInvoiceType;
    }

    private String gkeyInvoiceType;

    // draft_nbr, String final_nbr, String status, String name, String applied
    private final Object[] columnNames = {"Draft Nbr", "Final Nbr", "Status", "Peyee", "Finalized Date", "Applied"};
    private DefaultTableModel model;

    private String get_nbrfinal(){
        return _nbrfinal;
    }

    private void set_nbrfinal(String value){
        _nbrfinal = value;
    }

    public FrmMain() {
        setContentPane(pnlMain);
        //set label
        lblRowCount.setText("Count Rows 0 : ");
        // set table view
        createTableView();
        // fill combox invoice type
        fillComboxInvoiceType();
        // select Invoice Type
        cbxInvoiceType.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            InvoiceTypeItem item = (InvoiceTypeItem) c.getSelectedItem();
            setGkeyInvoiceType(item.get_gkey());
            // System.out.println(item.get_gkey() + ", " + item.get_id());
        });
        // search
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String invtype = getGkeyInvoiceType();
                if (invtype == null) {
                    JOptionPane.showMessageDialog(null, "For filter is required selected",
                            "Invoice Generator", JOptionPane.ERROR_MESSAGE);
                } else {
                    // fill table
                    loadDataTableView(invtype);
                }

            }
        });
        // generate
        btnGenerateEDI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nbrfinal = get_nbrfinal();
                if (nbrfinal == null) {
                    JOptionPane.showMessageDialog(null, "For genearte EDI is required selected an invoice",
                            "Invoive Generator", JOptionPane.ERROR_MESSAGE);
                } else {

                    //------------Generate EDI Invoice------------//
                    GenerateEDI generateEDI = new GenerateEDI();
                    GeneratePDF generatePDF = new GeneratePDF();


                    try{
                        //---------------------------------------------------------------------//
                        //---------------------GET DATA---------------------------------------//
                        dbConnection = new DBConnection(DBConnectionType.BILLING);
                        Invoice invoice = dbConnection.getInvoiceByFinalNumber(nbrfinal);
                        if (invoice == null) {
                            throw new Exception("Invoice is null");
                        }
                        //-------------------------------------------------------------------//
                        //------------------FILE NAME----------------------------------------//
                        String fileName = String.format("%s.edi", Utils.getFilename(invoice));
                        //---------------------------------------------//

                        //-----------------------------------------//
                        //-------Step 1---------------------------//
                        generateEDI.createEDI(invoice, fileName);
                        //-----------------------------------------//
                        //-------Step 2---------------------------//
                        generatePDF.generatePdf(invoice.get_draft_nbr(),fileName);
                        //-----------------------------------------//
                        //-------Step 3---------------------------//


                        //---------------------------------------//
                        JOptionPane.showMessageDialog(null, "Invoice generated correctly",
                                "Invoive Generator", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex){
                        System.out.println("Method btnGenerateEDI " + ex.getMessage());
                    }
                }
            }
        });

        // table select value
        tblView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                set_nbrfinal(tblView.getValueAt(tblView.getSelectedRow(), 1).toString());
            }
        });


    }

    // fill Combox Invoice Type
    private void fillComboxInvoiceType() {
        try {
            dbConnection = new DBConnection(DBConnectionType.BILLING);
            List<InvoiceTypeItem> invoiceTypeItems = dbConnection.getInvoiceTypeItemData();
            for (InvoiceTypeItem item : invoiceTypeItems) {
                cbxInvoiceType.addItem(item);
            }
        } catch (Exception ex) {
            System.out.println("Method fillComboxInvoiceType " + ex.getMessage());
        }

    }

    // fill table view
    private void createTableView() {
        Object[][] data = {
                {"", "", "", "", "", ""},
        };
        //
        tblView.setModel(new DefaultTableModel(null, columnNames));
        model = (DefaultTableModel) tblView.getModel();
        tblView.setFillsViewportHeight(true);
    }

    // load data
    private void loadDataTableView(String invtype) {
        try {
            // clear
            tblView.setModel(new DefaultTableModel(null, columnNames));
            model = (DefaultTableModel) tblView.getModel();

            dbConnection = new DBConnection(DBConnectionType.BILLING);
            List<InvoiceDetails> data = dbConnection.getInvoiceDetails(invtype);

            if (data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    InvoiceDetails item = data.get(i);
                    Vector<Object> cell = new Vector<Object>();

                    cell.addElement(item.get_draft_nbr());
                    cell.addElement(item.get_final_nbr());
                    cell.addElement(item.get_status());
                    cell.addElement(item.get_name());
                    cell.addElement(item.get_finalized_date());
                    cell.addElement(item.get_applied());

                    model.addRow(cell);
                }
                //
                lblRowCount.setText("Count Rows : " + data.size());

            }
        } catch (Exception ex) {
            System.out.println("Method loadDataTableView " + ex.getMessage());
        }

    }

}
