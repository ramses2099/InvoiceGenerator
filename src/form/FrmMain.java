package form;

import com.dpworld.DBConnection;
import com.dpworld.DBConnectionType;
import com.dpworld.InvoiceDetails;
import com.dpworld.InvoiceTypeItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private DBConnection dbConnection;

    public String getGkeyInvoiceType() {
        return gkeyInvoiceType;
    }

    public void setGkeyInvoiceType(String gkeyInvoiceType) {
        this.gkeyInvoiceType = gkeyInvoiceType;
    }

    private String gkeyInvoiceType;

    // draft_nbr, String final_nbr, String status, String name, String applied
    private final Object[] columnNames = { "Draft Nbr", "Final Nbr", "Status", "Peyee", "Finalized Date","Applied" };
    private DefaultTableModel model;

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
                if(invtype == null){
                    JOptionPane.showMessageDialog(null,"For filter is required selected",
                            "Invoice Type",JOptionPane.ERROR_MESSAGE);
                }else{
                    // fill table
                    loadDataTableView(invtype);
                }

            }
        });
        // generate
        btnGenerateEDI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ok");
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
    private void createTableView(){
        Object[][] data = {
                {"", "", "","", "" ,""},
        };
        //
        tblView.setModel(new DefaultTableModel(null,columnNames));
        model = (DefaultTableModel) tblView.getModel();
        tblView.setFillsViewportHeight(true);
    }
    // load data
    private void loadDataTableView(String invtype){
        try{
            // clear
            tblView.setModel(new DefaultTableModel(null,columnNames));
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
        }catch (Exception ex){
            System.out.println("Method loadDataTableView " + ex.getMessage());
        }

    }

}
