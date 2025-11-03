/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaFormulario;
import Vista.VistaMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.VistaListaDeTurnos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuController implements ActionListener {
    // Instancias de las Vistas que va a gestionar
    private final VistaMenuPrincipal vistaMenu;
    private VistaFormulario formulario; // Se inicializa solo si es necesario
    private VistaListaDeTurnos listaTurnos;

    public MenuController(VistaMenuPrincipal vistaMenu) {
        this.vistaMenu = vistaMenu;

        // Asignar el controlador como oyente de los botones del menú
        this.vistaMenu.getBotonAgendarTurno().addActionListener(this);
        this.vistaMenu.getBotonVerAgenda().addActionListener(this);
    }

    // Método para iniciar la aplicación (mostrar la vista inicial)
    public void iniciar() {
        vistaMenu.setTitle("CONSULTORIO DR. AMADO");
        vistaMenu.setLocationRelativeTo(null); // Centrar la ventana
        vistaMenu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Lógica para el botón "Agendar turno"
        if (e.getSource() == vistaMenu.getBotonAgendarTurno()) {

            // 1. Ocultar la ventana de menú
           vistaMenu.setVisible(false);

            // 2. Crear y mostrar la ventana del formulario
            // Nota: Es mejor crear la instancia del Formulario aquí
            // para que los datos sean nuevos cada vez.
            formulario = new VistaFormulario();
            formulario.setLocationRelativeTo(null);
            FormularioController formularioController = new FormularioController(formulario);
            formulario.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Cuando el Formulario se cierra, mostramos el Menú de nuevo.
                vistaMenu.setVisible(true);
            }
        });
            formulario.setVisible(true);

            // Si tuvieras un ControladorFormulario, lo inicializarías aquí
            // ControladorFormulario cf = new ControladorFormulario(formulario);
            // cf.iniciar();
        } // Lógica para el botón "Ver agenda" (si lo implementas más tarde)
        else if (e.getSource() == vistaMenu.getBotonVerAgenda()) {
           // 3.1. Ocultar la ventana de menú
            vistaMenu.setVisible(false);
            
            // 3.2. Crear y mostrar la ventana de la lista de turnos
            // Es mejor crear la instancia aquí para asegurar un nuevo estado
            listaTurnos = new VistaListaDeTurnos();
            listaTurnos.setLocationRelativeTo(null);
            listaTurnos.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Cuando la Lista de Turnos se cierra, mostramos el Menú de nuevo.
                vistaMenu.setVisible(true);
            }
        });
            listaTurnos.setVisible(true);

            // 3.3. Si tuvieras un ControladorListaDeTurnos, lo inicializarías aquí.
            // Esto es crucial en MVC para que la nueva vista tenga su propia lógica.
            // ControladorListaDeTurnos cl = new ControladorListaDeTurnos(listaTurnos, modelo);
            // cl.iniciar();
        }
    }
}
