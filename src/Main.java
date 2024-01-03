import javax.swing.*;
import form.*;

public class Main {

    public static void main(String[] args) {

        FrmMain frmMain = new FrmMain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmMain.setVisible(true);
                frmMain.setTitle("Invoice Generator");
                frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frmMain.setBounds(100, 100, 931, 702);
            }
        });
    }
}
