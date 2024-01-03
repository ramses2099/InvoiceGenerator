import javax.imageio.ImageIO;
import javax.swing.*;
import form.*;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        FrmMain frmMain = new FrmMain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmMain.setVisible(true);
                frmMain.setTitle("Invoice Generator");
                frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frmMain.setResizable(false);
                frmMain.setLocationRelativeTo(null);
                frmMain.setBounds(100, 100, 931, 702);

                try {
                    frmMain.setIconImage(ImageIO.read(getClass().getResourceAsStream ("/images/Zoom.png")));
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
}
