package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import Controlador.ListaController;

public class EditorBotones extends DefaultCellEditor {

    private JPanel panel;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnDetalles;
    private JButton btnAgendar;
    private JTable table;
    private int selectedRow;
    private LocalDate fechaActual;
    private ListaController controller;

    public EditorBotones(JCheckBox checkBox, JTable table, LocalDate fecha, ListaController controller) {
        super(checkBox);
        this.table = table;
        this.fechaActual = fecha;
        this.controller = controller;

        // Panel contenedor
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setBackground(new Color(239, 239, 232));

        // Inicialización de botones
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnDetalles = new JButton("Detalles");
        btnAgendar = new JButton("Agendar Turno");

        // Estilos
        configurarBoton(btnModificar, new Color(70, 130, 180));
        configurarBoton(btnEliminar, new Color(200, 70, 70));
        configurarBoton(btnDetalles, new Color(90, 90, 90));
        configurarBoton(btnAgendar, new Color(60, 150, 60));

        // Acciones
        btnModificar.addActionListener(e -> {
            fireEditingStopped();
            controller.modificarTurno(fechaActual, selectedRow);
        });

        btnEliminar.addActionListener(e -> {
            fireEditingStopped();
            controller.eliminarTurno(fechaActual, selectedRow);
        });

        btnDetalles.addActionListener(e -> {
            fireEditingStopped();
            controller.verDetalles(fechaActual, selectedRow);
        });

        btnAgendar.addActionListener(e -> {
            fireEditingStopped();
            String horaTurno = (String) table.getValueAt(selectedRow, 0);
            controller.abrirFormularioTurno(fechaActual, horaTurno);
        });
    }

    // Configura estilo visual del botón
    private void configurarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
        boton.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        this.selectedRow = row;
        panel.removeAll();

        String nombre = (String) table.getValueAt(row, 1);

        // Si el turno está libre, mostrar solo el botón "Agendar"
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
}
