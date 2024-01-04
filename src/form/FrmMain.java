package form;

import com.dpworld.DBConnection;
import com.dpworld.DBConnectionType;
import com.dpworld.InvoiceTypeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmMain extends JFrame {

    private JPanel pnlMain;
    private JButton btnGenerateEDI;
    private JButton btnSearch;
    private JComboBox cbxInvoiceType;
    private JTable table1;
    private JLabel lblRowCount;
    private JLabel lblInvoiceType;
    private JPanel pnlSearch;

    private DBConnection dbConnection;

    public String getGkeyInvoiceType() {
        return gkeyInvoiceType;
    }

    public void setGkeyInvoiceType(String gkeyInvoiceType) {
        this.gkeyInvoiceType = gkeyInvoiceType;
    }

    private String gkeyInvoiceType;



    public FrmMain() {
        setContentPane(pnlMain);
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

    //
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


}
