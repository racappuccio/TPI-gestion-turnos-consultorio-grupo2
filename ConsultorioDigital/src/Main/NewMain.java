package Main;

import Vista.VistaLogin;
import Controlador.LoginController; // Asegúrate de importar el controlador

/**
 *
 * @author adriel
 */
public class NewMain {

    public static void main(String args[]) {

        // 1. Aplicar el Look and Feel (Debe ir PRIMERO)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) { 
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // 2. Iniciar la aplicación en el hilo de despacho de eventos (EDT)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                // 3. Crear la Vista (GUI del Login)
                VistaLogin vistaLogin = new VistaLogin();
                
                // 4. Crear el Controlador y conectarlo con la Vista.
                // El Controlador accede directamente al RepositorioUsuarios (Modelo).
                LoginController controlador = new LoginController(vistaLogin);

                // 5. Hacer visible la Vista
                vistaLogin.setVisible(true);
            }
        });
    }
}