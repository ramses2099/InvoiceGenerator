package form;

import com.dpworld.DBConnection;
import com.dpworld.DBConnectionType;
import com.dpworld.InvoiceTypeItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmMain extends JFrame {

    private JPanel pnlMain;
    private JButton btnGenerateEDI;
    private JButton btnSearch;
    private JComboBox cbxInvoiceType;
    private JTable tblView;
    private JLabel lblInvoiceType;
    private JPanel pnlSearch;
    private JPanel pnlTable;
    private JLabel lblRowCount;
    private JPanel pnlRowCount;
    private JTable table1;
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
    private final Object[] columnNames = { "Draft Nbr", "Final Nbr", "Status", "Name", "Applied" };
    private DefaultTableModel model;

    public FrmMain() {
        setContentPane(pnlMain);
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
                String sfilter = getGkeyInvoiceType();
                if(sfilter == null){
                    JOptionPane.showMessageDialog(null,"For filter is required selected",
                            "Invoice Type",JOptionPane.ERROR_MESSAGE);
                }else{
                    // fill table
                    System.out.println("No");
                }

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
                {"-", "-", "-","-", "-" },
        };
        //
        tblView = new JTable(new DefaultTableModel(data,columnNames));
        model = (DefaultTableModel) tblView.getModel();
        jscPnView = new JScrollPane(tblView);
        tblView.setFillsViewportHeight(true);
    }

}
