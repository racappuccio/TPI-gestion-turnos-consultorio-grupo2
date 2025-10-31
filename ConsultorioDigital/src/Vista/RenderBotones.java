package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class RenderBotones extends JPanel implements TableCellRenderer {
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JButton btnAgendar;
    
    public RenderBotones() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        setOpaque(true);
        
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
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        removeAll();
        String nombre = (String) table.getValueAt(row, 1);
        
        if (nombre == null || nombre.trim().isEmpty() || nombre.equalsIgnoreCase("Libre")) {
            setBackground(new Color(220, 240, 220));
            add(btnAgendar);
        } else {
            setBackground(new Color(239, 239, 232));
            add(btnModificar);
            add(btnEliminar);
            add(btnDetalles);
        }
        
        revalidate();
        repaint();
        return this;
    }
}