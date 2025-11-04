package Vista;
 
import Modelo.Turno0;
import Modelo.TurnoManager;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
 
public class EditorBotones extends DefaultCellEditor {
    private JPanel panel;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JButton btnAgendar;
    private JTable table;
    private int selectedRow;
    private LocalDate fechaActual;
 
    public EditorBotones(JCheckBox checkBox, JTable table, LocalDate fecha) {
        super(checkBox);
        this.table = table;
        this.fechaActual = fecha;
 
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setBackground(new Color(239, 239, 232));
 
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnDetalles = new JButton("Detalles");
        btnAgendar = new JButton("Agendar Turno");
 
        configurarBoton(btnModificar, new Color(70, 130, 180));
        configurarBoton(btnEliminar, new Color(200, 70, 70));
        configurarBoton(btnDetalles, new Color(21, 70, 77));
        configurarBoton(btnAgendar, new Color(100, 180, 100));
 
        btnModificar.addActionListener(e -> { fireEditingStopped(); modificarTurno(selectedRow); });
        btnEliminar.addActionListener(e -> { fireEditingStopped(); eliminarTurno(selectedRow); });
        btnDetalles.addActionListener(e -> { fireEditingStopped(); verDetalles(selectedRow); });
        btnAgendar.addActionListener(e -> { fireEditingStopped(); agendarTurno(selectedRow); });
    }
 
    private void configurarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.selectedRow = row;
        panel.removeAll();
 
        String disponibilidad = (String) table.getValueAt(row, 2);
 
        if (disponibilidad == null || disponibilidad.trim().isEmpty() || disponibilidad.equalsIgnoreCase("Disponible")) {
            panel.setBackground(new Color(220, 240, 220));
            panel.add(btnAgendar);
        } else {
            panel.setBackground(new Color(239, 239, 232));
            panel.add(btnModificar);
            panel.add(btnEliminar);
            panel.add(btnDetalles);
        }
 
        panel.revalidate();
        panel.repaint();
        return panel;
    }
 
    @Override
    public Object getCellEditorValue() { return ""; }
 
    // ========================= VALIDACIÓN REUTILIZABLE =========================
    private boolean validarCampos(String nombre, String dni, String telefono, String motivo) {
        if (nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(panel, "Ingrese un nombre válido (solo letras).");
            return false;
        }
        if (!dni.matches("\\d{7,8}")) {
            JOptionPane.showMessageDialog(panel, "Ingrese un DNI válido (7 u 8 dígitos numéricos).");
            return false;
        }
        if (!telefono.matches("\\d{6,15}")) {
            JOptionPane.showMessageDialog(panel, "Ingrese un teléfono válido (solo números, 6-15 dígitos).");
            return false;
        }
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Debe ingresar el motivo de consulta.");
            return false;
        }
        return true;
    }
 
    // ========================= AGENDAR TURNO =========================
    public void agendarTurno(int row) {
        String hora = (String) table.getValueAt(row, 0);
 
        JTextField txtNombre = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtObraSocial = new JTextField();
        JTextField txtMotivo = new JTextField();
 
        Object[] mensaje = {
                "Horario:", hora,
                "Nombre y apellido del paciente:", txtNombre,
                "DNI del paciente:", txtDni,
                "Teléfono del paciente:", txtTelefono,
                "Obra social:", txtObraSocial,
                "Motivo de consulta:", txtMotivo
        };
 
        if (JOptionPane.showConfirmDialog(panel, mensaje, "Agendar Turno",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
 
            String nombre = txtNombre.getText().trim();
            String dni = txtDni.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String obraSocial = txtObraSocial.getText().trim();
            String motivo = txtMotivo.getText().trim();
 
            if (!validarCampos(nombre, dni, telefono, motivo)) return;
 
            TurnoManager.getInstancia().agregarTurno(fechaActual, hora, nombre, dni, telefono, obraSocial, motivo);
            table.setValueAt(nombre, row, 1);
            table.setValueAt("Ocupado", row, 2);
            
            table.repaint();
 
            JOptionPane.showMessageDialog(panel, "Turno agendado exitosamente");
        }
    }
 
    // ========================= MODIFICAR TURNO =========================
    public void modificarTurno(int row) {
        String hora = (String) table.getValueAt(row, 0);
        Turno0 turno = TurnoManager.getInstancia().getTurnoPorFechaYHora(fechaActual, hora);
 
        if (turno == null) {
            JOptionPane.showMessageDialog(panel, "No se encontró el turno seleccionado.");
            return;
        }
 
        JTextField txtNombre = new JTextField(turno.getNombre());
        JTextField txtDni = new JTextField(turno.getDni());
        JTextField txtTelefono = new JTextField(turno.getTelefono());
        JTextField txtObraSocial = new JTextField(turno.getObraSocial());
        JTextField txtMotivo = new JTextField(turno.getMotivo());
 
        Object[] mensaje = {
                "Horario:", hora,
                "Nombre y apellido del paciente:", txtNombre,
                "DNI del paciente:", txtDni,
                "Teléfono del paciente:", txtTelefono,
                "Obra social:", txtObraSocial,
                "Motivo de consulta:", txtMotivo
        };
 
        int opcion = JOptionPane.showConfirmDialog(panel, mensaje, "Modificar Turno",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
 
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoDni = txtDni.getText().trim();
            String nuevoTelefono = txtTelefono.getText().trim();
            String nuevaObraSocial = txtObraSocial.getText().trim();
            String nuevoMotivo = txtMotivo.getText().trim();
 
            if (!validarCampos(nuevoNombre, nuevoDni, nuevoTelefono, nuevoMotivo)) return;
 
            turno.setNombre(nuevoNombre);
            turno.setDni(nuevoDni);
            turno.setTelefono(nuevoTelefono);
            turno.setObraSocial(nuevaObraSocial);
            turno.setMotivo(nuevoMotivo);
 
            table.setValueAt(nuevoNombre, row, 1);
            table.setValueAt("Ocupado", row, 2);
            
            table.repaint();
 
            JOptionPane.showMessageDialog(panel, "Turno modificado exitosamente");
        }
    }
 
    // ========================= ELIMINAR Y DETALLES =========================
    private void eliminarTurno(int row) {
        String nombre = (String) table.getValueAt(row, 1);
        String hora = (String) table.getValueAt(row, 0);
 
        if (JOptionPane.showConfirmDialog(panel,
                "¿Cancelar el turno de " + nombre + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
 
            TurnoManager.getInstancia().eliminarTurno(fechaActual, hora);
            table.setValueAt("Disponible", row, 1);
            table.setValueAt("", row, 1);
            table.setValueAt("Disponible", row, 2);
 
            table.repaint();
            
            JOptionPane.showMessageDialog(panel, "Turno cancelado");
        }
    }
 
    private void verDetalles(int row) {
        String hora = (String) table.getValueAt(row, 0);
        Turno0 turno = TurnoManager.getInstancia().getTurnoPorFechaYHora(fechaActual, hora);
 
        if (turno != null) {
            JOptionPane.showMessageDialog(panel,
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
            JOptionPane.showMessageDialog(panel,
                    "No se encontraron detalles para este turno.",
                    "Sin datos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
