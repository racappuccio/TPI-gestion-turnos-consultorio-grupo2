package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class EditorBotones extends DefaultCellEditor {
    private JPanel panel;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JButton btnAgendar;
    private JTable table;
    private int selectedRow;
    
    public EditorBotones(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setBackground(new Color(239, 239, 232));
        
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnDetalles = new JButton("Detalles");
        btnAgendar = new JButton("Agendar Turno");
        
        btnModificar.setBackground(new Color(70, 130, 180));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFocusPainted(false);
        btnModificar.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
        
        btnEliminar.setBackground(new Color(200, 70, 70));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
        
        btnDetalles.setBackground(new Color(21, 70, 77));
        btnDetalles.setForeground(Color.WHITE);
        btnDetalles.setFocusPainted(false);
        btnDetalles.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
        
        btnAgendar.setBackground(new Color(100, 180, 100));
        btnAgendar.setForeground(Color.WHITE);
        btnAgendar.setFocusPainted(false);
        btnAgendar.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
        
        btnModificar.addActionListener(e -> {
            fireEditingStopped();
            modificarTurno(selectedRow);
        });
        
        btnEliminar.addActionListener(e -> {
            fireEditingStopped();
            eliminarTurno(selectedRow);
        });
        
        btnDetalles.addActionListener(e -> {
            fireEditingStopped();
            verDetalles(selectedRow);
        });
        
        btnAgendar.addActionListener(e -> {
            fireEditingStopped();
            agendarTurno(selectedRow);
        });
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.selectedRow = row;
        panel.removeAll();
        
        String nombre = (String) table.getValueAt(row, 1);
        
        if (nombre == null || nombre.trim().isEmpty() || nombre.equalsIgnoreCase("Libre")) {
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
    public Object getCellEditorValue() {
        return "";
    }
    
    private void agendarTurno(int row) {
        String hora = (String) table.getValueAt(row, 0);
        
        JTextField txtNombre = new JTextField();
        JTextField txtMotivo = new JTextField();
        
        Object[] mensaje = {
            "Horario:", hora,
            "Nombre del paciente:", txtNombre,
            "Motivo de consulta:", txtMotivo
        };
        
        int opcion = JOptionPane.showConfirmDialog(panel, mensaje, "Agendar Turno", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String motivo = txtMotivo.getText().trim();
            
            if (!nombre.isEmpty()) {
                table.setValueAt(nombre, row, 1);
                table.setValueAt(motivo, row, 2);
                JOptionPane.showMessageDialog(panel, "Turno agendado exitosamente para " + nombre);
            } else {
                JOptionPane.showMessageDialog(panel, "El nombre del paciente es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void modificarTurno(int row) {
        String hora = (String) table.getValueAt(row, 0);
        String nombre = (String) table.getValueAt(row, 1);
        String motivo = (String) table.getValueAt(row, 2);
        
        JTextField txtNombre = new JTextField(nombre);
        JTextField txtMotivo = new JTextField(motivo);
        
        Object[] mensaje = {
            "Horario:", hora,
            "Nombre del paciente:", txtNombre,
            "Motivo de consulta:", txtMotivo
        };
        
        int opcion = JOptionPane.showConfirmDialog(panel, mensaje, "Modificar Turno", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoMotivo = txtMotivo.getText().trim();
            
            if (!nuevoNombre.isEmpty()) {
                table.setValueAt(nuevoNombre, row, 1);
                table.setValueAt(nuevoMotivo, row, 2);
                JOptionPane.showMessageDialog(panel, "Turno modificado exitosamente");
            }
        }
    }
    
    private void eliminarTurno(int row) {
        String nombre = (String) table.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(panel,
            "¿Cancelar el turno de " + nombre + "?",
            "Confirmar cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            table.setValueAt("Libre", row, 1);
            table.setValueAt("", row, 2);
            JOptionPane.showMessageDialog(panel, "Turno cancelado. El horario quedó disponible.");
        }
    }
    
    private void verDetalles(int row) {
        String hora = (String) table.getValueAt(row, 0);
        String nombre = (String) table.getValueAt(row, 1);
        String motivo = (String) table.getValueAt(row, 2);
        
        JOptionPane.showMessageDialog(panel,
            "=== DETALLES DEL TURNO ===\n\n" +
            "Hora: " + hora + "\n" +
            "Paciente: " + nombre + "\n" +
            "Motivo: " + motivo);
    }
}