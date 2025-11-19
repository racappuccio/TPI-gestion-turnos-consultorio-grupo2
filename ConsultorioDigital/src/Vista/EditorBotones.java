package Vista;

import Modelo.Turno0;
import Modelo.TurnoManager;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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
    public Object getCellEditorValue() {
        return "";
    }

    // ========================= VALIDACIÓN REUTILIZABLE =========================
    private boolean validarCampos(String nombre, String dni, String telefono, String motivo) {

        // Usamos StringBuilder para acumular todos los errores encontrados
        StringBuilder errores = new StringBuilder();

        // 1. Validar Nombre
        if (nombre.isEmpty()) {
            errores.append("- Nombre y apellido es obligatorio.\n");
        } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            errores.append("- Ingrese un nombre válido (solo letras).\n");
        }

        // 2. Validar DNI
        if (dni.isEmpty()) {
            errores.append("- DNI es obligatorio.\n");
        } else if (!dni.matches("\\d{7,8}")) {
            errores.append("- Ingrese un DNI válido (7 u 8 dígitos numéricos).\n");
        }

        // 3. Validar Teléfono
        if (telefono.isEmpty()) {
            errores.append("- Teléfono es obligatorio.\n");
        } else if (!telefono.matches("\\d{6,15}")) {
            // El mensaje de error que se veía en la imagen (solo números, 6-15 dígitos)
            errores.append("- Ingrese un teléfono válido (solo números, 6-15 dígitos).\n");
        }

        // 4. Validar Motivo
        if (motivo.isEmpty()) {
            errores.append("- Motivo de consulta es obligatorio.\n");
        }

        // 5. Verificar si hay errores acumulados
        if (errores.length() > 0) {
            // Si hay errores, mostrar un mensaje resumen con todos ellos
            JOptionPane.showMessageDialog(panel,
                    "Por favor, complete los siguientes datos:\n\n" + errores.toString(),
                    "DATOS OBLIGATORIOS",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Indica que la validación falló
        }

        return true; // La validación fue exitosa
    }

    // ========================= AGENDAR TURNO =========================
    // ========================= AGENDAR TURNO MODIFICADO =========================
    public void agendarTurno(int row) {
        if (fechaActual.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(panel,
                    "No es posible agendar un turno en un día que ya haya transcurrido",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String hora = (String) table.getValueAt(row, 0);
        boolean validacionExitosa = false;

        // Declaramos los campos de texto aquí, fuera del bucle, para que conserven su valor
        JTextField txtNombre = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtObraSocial = new JTextField();
        JTextArea areaMotivo = new JTextArea(4, 30); // 4 filas, 30 columnas
        areaMotivo.setLineWrap(true);      // Habilita el salto de línea visual
    areaMotivo.setWrapStyleWord(true); // Asegura que el salto sea por palabra
        ((AbstractDocument) areaMotivo.getDocument()).setDocumentFilter(new LimitDocumentFilter(250));
areaMotivo.getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "none");
        
        JScrollPane scrollMotivo = new JScrollPane(areaMotivo);
        
        // El mensaje y los campos (objetos) se definen una sola vez
        Object[] mensaje = {
            "Horario:", hora,
            "Nombre y apellido del paciente:", txtNombre,
            "DNI del paciente:", txtDni,
            "Teléfono del paciente:", txtTelefono,
            "Obra social:", txtObraSocial,
            "Motivo de consulta:", scrollMotivo
        };

        // Bucle para reabrir el formulario hasta que se valide o se cancele
        do {
            // Mostramos el diálogo. Los JTextFields conservan su contenido.
            int opcion = JOptionPane.showConfirmDialog(panel, mensaje, "Agendar Turno",
                    JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {
                // El usuario presionó OK. Extraemos y limpiamos los valores.
                String nombre = txtNombre.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String obraSocial = txtObraSocial.getText().trim();
                String motivo = areaMotivo.getText().trim();
                // 1. Intentamos validar.
                if (validarCampos(nombre, dni, telefono, motivo)) {
                    // 2. Si la validación es exitosa:
                    TurnoManager.getInstancia().agregarTurno(fechaActual, hora, nombre, dni, telefono, obraSocial, motivo);
                    table.setValueAt(nombre, row, 1);
                    table.setValueAt("Ocupado", row, 2);
                    table.repaint();
                    JOptionPane.showMessageDialog(panel, "Turno agendado exitosamente");
                    validacionExitosa = true; // Salimos del bucle
                }
                // 3. Si la validación falla (validador muestra el error y retorna false), 
                // la bandera 'validacionExitosa' sigue siendo false y el bucle se repite, 
                // mostrando de nuevo el formulario con los datos ya cargados.

            } else {
                // El usuario hizo clic en Cancelar o cerró el diálogo. Salimos del bucle.
                validacionExitosa = true;
            }

        } while (!validacionExitosa);
    }

    // ========================= MODIFICAR TURNO =========================
    public void modificarTurno(int row) {
        if (fechaActual.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(panel,
                    "No es posible modificar turnos de un día que ya haya transcurrido",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
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
JTextArea areaMotivo = new JTextArea(turno.getMotivo(), 4, 30); // Inicializar con el motivo
    areaMotivo.setLineWrap(true);
    ((AbstractDocument) areaMotivo.getDocument()).setDocumentFilter(new LimitDocumentFilter(250));
   areaMotivo.getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "none");
    areaMotivo.setWrapStyleWord(true);JScrollPane scrollMotivo = new JScrollPane(areaMotivo);

        Object[] mensaje = {
            "Horario:", hora,
            "Nombre y apellido del paciente:", txtNombre,
            "DNI del paciente:", txtDni,
            "Teléfono del paciente:", txtTelefono,
            "Obra social:", txtObraSocial,
"Motivo de consulta:", scrollMotivo        };

        int opcion = JOptionPane.showConfirmDialog(panel, mensaje, "Modificar Turno",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoDni = txtDni.getText().trim();
            String nuevoTelefono = txtTelefono.getText().trim();
            String nuevaObraSocial = txtObraSocial.getText().trim();
           String nuevoMotivo = areaMotivo.getText().trim();

            if (!validarCampos(nuevoNombre, nuevoDni, nuevoTelefono, nuevoMotivo)) {
                return;
            }

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
        if (fechaActual.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(panel,
                    "No es posible eliminar turnos de un día que ya haya transcurrido",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
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
            // *** MODIFICADO: Usamos un JTextArea para que el texto se ajuste (word wrap) ***
            JTextArea textArea = new JTextArea(10, 30); // 10 filas, 30 columnas
            textArea.setText(
                    "Hora: " + turno.getHora() + "\n"
                    + "Nombre: " + turno.getNombre() + "\n"
                    + "DNI: " + turno.getDni() + "\n"
                    + "Teléfono: " + turno.getTelefono() + "\n"
                    + "Obra social: " + turno.getObraSocial() + "\n"
                    + "Motivo: " + turno.getMotivo()
            );
            textArea.setEditable(false);
            textArea.setLineWrap(true); // Habilita el ajuste de línea
            textArea.setWrapStyleWord(true); // Ajusta la línea por palabra

            // Añadimos el JTextArea a un JScrollPane en caso de que el texto sea muy largo
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Reemplazamos el diálogo de texto plano por el JScrollPane con el JTextArea
            JOptionPane.showMessageDialog(panel,
                    scrollPane, // Pasamos el JScrollPane
                    "=== DETALLES DEL TURNO ===", // Título
                    JOptionPane.INFORMATION_MESSAGE);
            // *** FIN DE MODIFICACIÓN ***

        } else {
            // ... (código para "No se encontraron detalles")
        }
    }
    // Clase interna simple dentro de EditorBotones
private class LimitDocumentFilter extends DocumentFilter {
    private int limite;

    public LimitDocumentFilter(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        if ((fb.getDocument().getLength() + string.length()) <= limite) {
            super.insertString(fb, offset, string, attr);
        } else {
            // Opcional: Mostrar un mensaje de advertencia
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        if ((fb.getDocument().getLength() + text.length() - length) <= limite) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            // Opcional: Mostrar un mensaje de advertencia
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
}
