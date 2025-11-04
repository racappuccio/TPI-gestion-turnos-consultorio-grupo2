package Controlador;

import Vista.VistaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Vista.VistaLista;

public class LoginController implements ActionListener {

    private VistaLogin vista; // La vista (interfaz gráfica)
    
    public LoginController(VistaLogin vista) {
        this.vista = vista;
        //  this.modelo = modelo;
        // Inicializa la escucha de eventos de la vista
        iniciarControl();
    }

    /**
     * Configura los ActionListeners para los componentes de la vista.
     */
    public void iniciarControl() {
        // Asigna el controlador como el ActionListener del botón de inicio de sesión
        // Suponemos que el botón se llama 'jButton1' en JFrame_principal
        this.vista.getjButton1().addActionListener(this);
    }

    /**
     * Maneja los eventos de acción de los componentes (por ejemplo, clic del
     * botón).
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
    /**
     * Lógica de negocio para intentar iniciar sesión.
     */
    private void iniciarSesion() {
        // 1. Obtener datos de la vista (View)
        String usuario = vista.getjTextField1().getText().trim();
        String contrasena = vista.jPasswordField1().getText();

        // 2. Validación de campos vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete usuario y contraseña", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Buscar y validar credenciales
        Modelo.Usuario user = Modelo.RepositorioUsuarios.buscarUsuario(usuario);

        if (user != null && user.checkPassword(contrasena)) {
            // Credenciales correctas
            JOptionPane.showMessageDialog(vista, "¡Inicio de Sesión Exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);

            // 🚀 Lógica de Navegación (CORREGIDA) 🚀
            // 1. Crear una instancia de la ventana de la lista de turnos
            VistaLista listaTurnos = new VistaLista();

            // 2. Hacer visible la nueva ventana
            listaTurnos.setVisible(true);

            // 3. Cerrar la ventana de login actual
            vista.dispose();

        } else {
            // Credenciales incorrectas
            JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}
