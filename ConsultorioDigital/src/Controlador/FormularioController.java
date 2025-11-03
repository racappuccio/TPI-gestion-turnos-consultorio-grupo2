/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaFormulario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Modelo.Turno0;
import Modelo.TurnoManager;
import java.time.LocalDate;
import java.awt.TextArea; // Necesario para el campo de Motivo
import javax.swing.JTextField;

public class FormularioController implements ActionListener {

    private final VistaFormulario vistaFormulario;
    private final ListaController listaController;
    private final LocalDate fechaSeleccionada;
    private final String horaSeleccionada;
    private final Turno0 turnoAEditar; // Objeto a editar (null si es nuevo)

    /*
     * CONSTRUCTOR PRINCIPAL: Soporta Agendamiento y Modificación
     */
    public FormularioController(VistaFormulario vistaFormulario, ListaController listaController, 
                                LocalDate fecha, String hora, Turno0 turnoAEditar) {
        this.vistaFormulario = vistaFormulario;
        this.listaController = listaController;
        this.fechaSeleccionada = fecha;
        this.horaSeleccionada = hora;
        this.turnoAEditar = turnoAEditar;
        
        // Conexión de Listeners
        this.vistaFormulario.getBotonAtras().addActionListener(this);
        this.vistaFormulario.getBotonCancelar().addActionListener(this);
        this.vistaFormulario.getBotonGuardar().addActionListener(this);
    }
    
    /*
     * CONSTRUCTOR AUXILIAR 1: Para MODO AGENDAMIENTO (4 argumentos)
     * Llamado desde ListaController.abrirFormularioTurno(fecha, hora)
     */
    public FormularioController(VistaFormulario vistaFormulario, ListaController listaController, 
                                LocalDate fecha, String hora) {
        // Llama al constructor principal pasando null para el objeto a editar
        this(vistaFormulario, listaController, fecha, hora, null); 
    }

    /*
     * 🟢 CONSTRUCTOR AUXILIAR 2: Para MODO MODIFICACIÓN (3 argumentos)
     * Llamado desde ListaController.modificarTurno(turno, this)
     */
    public FormularioController(VistaFormulario vistaFormulario, ListaController listaController, Turno0 turno) {
    
    // 🟢 La llamada a this() DEBE SER la primera línea ejecutable
    this(vistaFormulario, 
         listaController, 
         listaController.getFechaActual(), // 🟢 Se llama al método directamente aquí
         turno.getHora(),                  // 🟢 Se llama al método directamente aquí
         turno);
}


    // -------------------------------------------------------------------------
    // ACCIÓN PRINCIPAL
    // -------------------------------------------------------------------------

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == this.vistaFormulario.getBotonAtras()) {
            this.accionAtras();
        } else if (e.getSource() == this.vistaFormulario.getBotonCancelar()) {
            this.accionCancelar();
        } else if (e.getSource() == this.vistaFormulario.getBotonGuardar()) {
            this.accionGuardar();
        }
    }

    // -------------------------------------------------------------------------
    // LÓGICA DE BOTONES
    // -------------------------------------------------------------------------
    
    private void accionAtras() {
        this.accionCancelar();
    }

    private void accionCancelar() {
        int confirmacion = JOptionPane.showConfirmDialog(
            vistaFormulario, 
            "¿Está seguro de que desea cancelar? Se perderán los datos.", 
            "Confirmar Cancelación", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirmacion == JOptionPane.YES_OPTION) {
            this.vistaFormulario.dispose(); 
        }
    }

    private void accionGuardar() {
        try {
            // 1. Recolectar datos con CASTING CORRECTO
            String nombre = ((JTextField) vistaFormulario.getCampoNombre()).getText().trim();
            String apellido = ((JTextField) vistaFormulario.getCampoApellido()).getText().trim();
            String dni = ((JTextField) vistaFormulario.getCampoDNI()).getText().trim();
            String telefono = ((JTextField) vistaFormulario.getCampoTelefono()).getText().trim();
            String obraSocial = ((JTextField) vistaFormulario.getCampoObraSocial()).getText().trim();
            
            // 💡 Usamos java.awt.TextArea para el campo motivo
            String motivo = ((TextArea) vistaFormulario.getCampoMotivo()).getText().trim(); 

            // 2. Validación
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(vistaFormulario, "Nombre, Apellido y DNI son campos obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Lógica de Negocio: AGENDAMIENTO vs. MODIFICACIÓN
            if (this.turnoAEditar != null) {
                // MODO MODIFICACIÓN: Actualizar el objeto existente
                this.turnoAEditar.setNombre(nombre + " " + apellido);
                this.turnoAEditar.setDni(dni);
                this.turnoAEditar.setTelefono(telefono);
                this.turnoAEditar.setObraSocial(obraSocial);
                this.turnoAEditar.setMotivo(motivo);
            } else {
                // MODO AGENDAMIENTO: Crear nuevo registro
                TurnoManager.getInstancia().agregarTurno(
                    this.fechaSeleccionada, 
                    this.horaSeleccionada, 
                    nombre + " " + apellido,
                    dni, 
                    telefono, 
                    obraSocial, 
                    motivo
                );
            }
            
            // 4. Notificar a la lista principal y cerrar
            listaController.turnoAgendadoExitosamente();
            this.vistaFormulario.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaFormulario, "Error al guardar el turno: " + e.getMessage(), "Error Fatal", JOptionPane.ERROR_MESSAGE);
        }
    }
}