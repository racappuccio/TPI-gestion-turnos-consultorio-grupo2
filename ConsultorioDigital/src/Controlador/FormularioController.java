/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

// Dentro de tu Controlador.FormularioController.java

import Vista.VistaFormulario; // Asegúrate de tener la importación
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormularioController implements ActionListener {

    private final VistaFormulario vistaFormulario;
    // ... otros atributos (Modelo)

    public FormularioController(VistaFormulario vistaFormulario) {
        this.vistaFormulario = vistaFormulario;
        
        // Asignar listeners
        this.vistaFormulario.getBotonAtras().addActionListener(this);
        this.vistaFormulario.getBotonCancelar().addActionListener(this);
        // ... otros botones
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ... Lógica para Guardar (si la hay)

        // Lógica para los botones Atrás y Cancelar
        if (e.getSource() == vistaFormulario.getBotonAtras() || 
            e.getSource() == vistaFormulario.getBotonCancelar()) {
            
            // Simplemente cierra la ventana del formulario
            vistaFormulario.dispose();
            // La ventana anterior (MenuPrincipal o ListaDeTurnos) debe reaparecer
            // automáticamente si se dejó solo OCULTA (setVisible(false)) al abrir el formulario.
        }
    }
 
}
