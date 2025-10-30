
package Main;

import Vista.JFrame_principal;

/**
 *
 * @author adriel
 */
public class NewMain {

    public static void main(String args[]) {
    
        // 1. Crear la Vista (GUI)
        JFrame_principal vistaLogin = new JFrame_principal();
        
        // 2. Crear el Controlador y conectarlo con la Vista
        // ¡¡NOTA: Ya no le pasamos un usuario!!
        Controlador.LoginController controlador = new Controlador.LoginController(vistaLogin);

        // 3. Hacer visible la Vista
        vistaLogin.setVisible(true);

        // 4. Aplicar el Look and Feel (esto está bien)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) { // Captura genérica simplificada
            java.util.logging.Logger.getLogger(JFrame_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
