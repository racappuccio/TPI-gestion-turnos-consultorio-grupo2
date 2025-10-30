
package Vista;
import javax.swing.*;

public class MenuPrincipalDemo extends JFrame {
    public MenuPrincipalDemo(){
        setTitle("Menu Principal - Demo");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lbl = new JLabel("Bienvenid@ - Login OK", SwingConstants.CENTER);
        add(lbl);
    }
}
