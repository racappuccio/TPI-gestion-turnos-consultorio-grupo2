package Controlador;

import Vista.JFrame_principal;
import Modelo.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {

    private JFrame_principal vista; // La vista (interfaz gráfica)
    private Usuario modelo;         // El modelo (datos y lógica de negocio)

    /**
     * Constructor del controlador.
     * @param vista La instancia del JFrame principal.
     * @param modelo La instancia del modelo Usuario (o una clase de acceso a datos de usuario).
     */
    public LoginController(JFrame_principal vista, Usuario modelo) {
        this.vista = vista;
        this.modelo = modelo;
        // Inicializa la escucha de eventos de la vista
        iniciarControl();
    }

    public LoginController(JFrame_principal vistaLogin) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Configura los ActionListeners para los componentes de la vista.
     */
    public void iniciarControl() {
        // Asigna el controlador como el ActionListener del botón de inicio de sesión
        // Suponemos que el botón se llama 'jButton1' en JFrame_principal
        this.vista.getjButton1().addActionListener(this); 
        
        // Es una buena práctica exponer los componentes privados de la vista
        // a través de métodos "getter" en JFrame_principal si se necesitan.
        // Si no puedes modificar JFrame_principal, puedes pasarlos en el constructor
        // o usar una referencia pública (no recomendado).
        
        // Si quisieras que el controlador manejara también el texto por defecto en los campos:
        // this.vista.getjTextField1().setText("Ingrese Usuario"); 
        // this.vista.getjTextField2().setText("Contraseña"); 
    }

    /**
     * Maneja los eventos de acción de los componentes (por ejemplo, clic del botón).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Verifica si la fuente del evento es el botón de inicio de sesión
        if (e.getSource() == vista.getjButton1()) {
            iniciarSesion();
        }
    }

    /**
     * Lógica de negocio para intentar iniciar sesión.
     */
    private void iniciarSesion() {
        // 1. Obtener datos de la vista (View)
        String usuario = vista.getjTextField1().getText();
        String contrasena = vista.jPasswordField1().getText(); 

        // 2. Interactuar con el modelo (Model)
        // En este ejemplo, el modelo 'Usuario' es estático para la demostración.
        // En una aplicación real, se consultaría una base de datos o un servicio.
        
        // Aquí se simula la validación de credenciales contra el modelo
        if (modelo.getUsername().equals(usuario) && modelo.checkPassword(contrasena)) {
            // Credenciales correctas
            JOptionPane.showMessageDialog(vista, "¡Inicio de Sesión Exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            // Lógica para abrir la siguiente ventana, por ejemplo:
            // vista.dispose(); // Cierra la ventana de login
            // new JFrame_otra_ventana().setVisible(true);
        } else {
            // Credenciales incorrectas
            JOptionPane.showMessageDialog(vista, "Usuario o Contraseña Incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}
