/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Modelo.Usuario;
import Vista.JFrame_principal;

/**
 *
 * @author adriel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    
    // 1. Crear el Modelo (Datos/Lógica)
    // Aquí se inicializa un usuario de ejemplo. En la realidad, estos datos vendrían de un login o una base de datos.
    Usuario modeloUsuario = new Usuario("admin", "12345"); // Usuario de ejemplo
  

    // 2. Crear la Vista (GUI)
    JFrame_principal vistaLogin = new JFrame_principal();
    
    // 3. Crear el Controlador y conectarlo con la Vista y el Modelo
    Controlador.LoginController controlador = new Controlador.LoginController(vistaLogin, modeloUsuario);

    // 4. Hacer visible la Vista
    vistaLogin.setVisible(true);

    /* El resto del código de Look and Feel que tenías en tu main */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        // Manejar la excepción si la clase del L&F no se encuentra
        java.util.logging.Logger.getLogger(JFrame_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        // Manejar la excepción si no se puede crear la instancia del L&F
        java.util.logging.Logger.getLogger(JFrame_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        // Manejar la excepción si el acceso al constructor es ilegal
        java.util.logging.Logger.getLogger(JFrame_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        // Manejar la excepción si el L&F no es soportado por el sistema
        java.util.logging.Logger.getLogger(JFrame_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    
    // Comenta o elimina la parte anterior que tenías en el main, 
    // ya que el manejo de la visibilidad y el Look and Feel debe 
    // hacerse de forma controlada una sola vez.

}
    
}
