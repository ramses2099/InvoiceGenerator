import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){
        setTitle("Invoice Generator V0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(300,200));
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}