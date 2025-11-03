package Controlador;

import Vista.VistaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
//import Vista.VistaMenuPrincipal;
//import Controlador.MenuController;
import Modelo.RepositorioUsuarios;
import Vista.VistaListaDeTurnos;
public class LoginController implements ActionListener {

    private VistaLogin vista; // La vista (interfaz gráfica)
    /**
     * Constructor del controlador.
     * @param vista La instancia del JFrame principal.
     * @param modelo La instancia del modelo Usuario (o una clase de acceso a datos de usuario).
     */
    public LoginController(VistaLogin vista) {
        this.vista = vista;
      // this.modelo = modelo;
        // Inicializa la escucha de eventos de la vista
        iniciarControl();
    }


    
     // Configura los ActionListeners para los componentes de la vista.
     
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

       /// 1. Crear una instancia de la ventana de la lista de turnos (LA VISTA)
        VistaListaDeTurnos vistaLista = new VistaListaDeTurnos();
        vistaLista.setLocationRelativeTo(null);
        
        // 2. Crear el Controlador de la Lista y CONECTARLO CON LA VISTA
        // (Debes crear esta clase ListaDeTurnosController si no existe)
       // ListaDeTurnosController listaController = new ListaDeTurnosController(vistaLista);
        
        // 3. Iniciar el Controlador (Muestra la vista y ACTIVA los listeners)
       // listaController.iniciar(); // Asumiendo que tienes un método iniciar()
        
        // 4. Cerrar la ventana de login actual (DEBE SER LO ÚLTIMO)
        vista.dispose();;
        
    } else {
        // Credenciales incorrectas
        JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
    }

}
}

