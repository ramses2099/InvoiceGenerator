import javax.imageio.ImageIO;
import javax.swing.*;
import form.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Start app");
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
