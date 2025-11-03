package Controlador;

import Modelo.Turno0;
import Modelo.TurnoManager;
import Vista.VistaListaDeTurnos;
import Vista.VistaFormulario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ListaController {

    private VistaListaDeTurnos vista;
    private LocalDate fechaActual;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListaController(VistaListaDeTurnos vista) {
        this.vista = vista;
        this.fechaActual = LocalDate.now();
        inicializar();
    }

    //
    // Métodos de Inicialización y Carga de Datos
    //
    
    private void inicializar() {
        vista.getjLabel1().setText("Turnos del día - " + fechaActual.format(formatoFecha));
        cargarHorariosDelDia();

        // Inicializa el Editor de Botones
        vista.getjTable1().getColumnModel().getColumn(3).setCellEditor(
            new Vista.EditorBotones(new JCheckBox(), vista.getjTable1(), fechaActual, this)
        );
    }

    public void cargarHorariosDelDia() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getjTable1().getModel();
        modelo.setRowCount(0);

        for (Turno0 turno : TurnoManager.getInstancia().getTurnosPorFecha(fechaActual)) {
            modelo.addRow(new Object[]{
                turno.getHora(),
                turno.getNombre(),
                turno.getMotivo(),
                ""
            });
        }

        modelo.fireTableDataChanged();
    }

    //
    // Métodos de Navegación (Día Anterior/Siguiente)
    //
    
    public void diaSiguiente() {
        fechaActual = fechaActual.plusDays(1);
        actualizarFechaYTurnos();
    }

    public void diaAnterior() {
        fechaActual = fechaActual.minusDays(1);
        actualizarFechaYTurnos();
    }

    private void actualizarFechaYTurnos() {
        vista.getjLabel1().setText("Turnos del día - " + fechaActual.format(formatoFecha));

        // Reconfigura el editor con la nueva fecha
        vista.getjTable1().getColumnModel().getColumn(3).setCellEditor(
            new Vista.EditorBotones(new JCheckBox(), vista.getjTable1(), fechaActual, this)
        );

        cargarHorariosDelDia();
        vista.getjTable1().revalidate();
        vista.getjTable1().repaint();
    }

    //
    // Métodos de Interacción del Usuario (Botones de la Lista)
    //

    // ✅ Abre el formulario para AGENDAR un nuevo turno
    public void abrirFormularioTurno(LocalDate fecha, String hora) {
        VistaFormulario formulario = new VistaFormulario(fecha, hora, this); // Constructor de Agendamiento
        new FormularioController(formulario, this, fecha, hora);
        formulario.setVisible(true);
    }

    // ✅ Abre el formulario para MODIFICAR un turno existente
    public void modificarTurno(LocalDate fecha, int row) {
        String hora = (String) vista.getjTable1().getValueAt(row, 0);
        Turno0 turno = TurnoManager.getInstancia().getTurnoPorFechaYHora(fecha, hora);

        // 1. Verificación
        if (turno == null || turno.getNombre().equalsIgnoreCase("Libre")) {
            JOptionPane.showMessageDialog(vista, "No se puede modificar un turno libre o no encontrado.");
            return;
        }

        // 2. Instanciar la Vista en modo EDICIÓN (Constructor que acepta Turno0)
        VistaFormulario formulario = new VistaFormulario(turno, this); 
        
        // 3. Crear el Controlador en modo EDICIÓN
        // Se asume que FormularioController tiene un constructor: new FormularioController(Vista, ListaController, Turno0)
        new FormularioController(formulario, this, turno); 

        // 4. Mostrar la vista
        formulario.setVisible(true);
    }

    // ✅ Actualiza la lista cuando se agenda un turno (Llamado desde FormularioController)
    public void turnoAgendadoExitosamente() {
        // Actualizar título
        vista.getjLabel1().setText("Turnos del día - " + fechaActual.format(formatoFecha));

        // Reconfigurar el Editor (necesario para que los botones de la fila cambien)
        vista.getjTable1().getColumnModel().getColumn(3).setCellEditor(
            new Vista.EditorBotones(new JCheckBox(), vista.getjTable1(), fechaActual, this)
        );
        
        // Recargar los datos
        cargarHorariosDelDia();
        
        // Forzar el redibujado
        vista.getjTable1().revalidate();
        vista.getjTable1().repaint();

        JOptionPane.showMessageDialog(vista, "Turno agendado exitosamente");
    }

    // ✅ Eliminar turno
    public void eliminarTurno(LocalDate fecha, int row) {
        String nombre = (String) vista.getjTable1().getValueAt(row, 1);
        String hora = (String) vista.getjTable1().getValueAt(row, 0);

        if (JOptionPane.showConfirmDialog(vista,
                "¿Cancelar el turno de " + nombre + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            TurnoManager.getInstancia().eliminarTurno(fecha, hora);
            vista.getjTable1().setValueAt("Libre", row, 1);
            vista.getjTable1().setValueAt("", row, 2);
            JOptionPane.showMessageDialog(vista, "Turno cancelado");
        }
    }

    // ✅ Ver detalles de turno
    public void verDetalles(LocalDate fecha, int row) {
        String hora = (String) vista.getjTable1().getValueAt(row, 0);
        Turno0 turno = TurnoManager.getInstancia().getTurnoPorFechaYHora(fecha, hora);

        if (turno != null) {
            JOptionPane.showMessageDialog(vista,
                "=== DETALLES DEL TURNO ===\n\n" +
                "Hora: " + turno.getHora() + "\n" +
                "Nombre: " + turno.getNombre() + "\n" +
                "DNI: " + turno.getDni() + "\n" +
                "Teléfono: " + turno.getTelefono() + "\n" +
                "Obra social: " + turno.getObraSocial() + "\n" +
                "Motivo: " + turno.getMotivo(),
                "Detalles del turno",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista,
                "No se encontraron detalles para este turno.",
                "Sin datos",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public LocalDate getFechaActual() {
        return fechaActual;
    }
}
