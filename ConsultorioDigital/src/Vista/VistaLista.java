package Vista;

import Controlador.LoginController;
import Modelo.Turno0;
import Modelo.TurnoManager;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class VistaLista extends javax.swing.JFrame {

    public VistaLista() {
        initComponents();

        fechaActual = LocalDate.now();
        jLabel1.setText("Turnos del día - " + fechaActual.format(formatoFecha));
        System.out.println("Turnos del dia - " + fechaActual.format(DateTimeFormatter.ofPattern("dd/MM")));

        // --- personalización visual de la tabla ---
        // Se mantiene comentado jScrollPane1.setColumnHeader(null) para que la cabecera se muestre.
        // Aplicar renderer personalizado para colorear (versión inline)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                // NOTA: Revisar si la columna 2 es Disponibilidad o Nombre para colorear.
                // Aquí usamos la Columna 2 (disponibilidad) para mantener la lógica original de color.
                String disponibilidad = (String) table.getValueAt(row, 2);

                if (disponibilidad != null && disponibilidad.equalsIgnoreCase("Disponible")) {
                    cell.setBackground(new Color(220, 240, 220));
                    cell.setForeground(new Color(100, 100, 100));
                } else {
                    cell.setBackground(new Color(239, 239, 232));
                    cell.setForeground(new Color(21, 70, 77));
                }

                setHorizontalAlignment(JLabel.CENTER);
                return cell;
            }
        };

        // Modificado: Ahora 4 columnas. Aplica Renderer a 0 (Hora), 1 (Nombre), 2 (Disponibilidad)
        for (int i = 0; i < 3; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Columna 3 es Acciones (antes era la 2)
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new RenderBotones());
        jTable1.getColumnModel().getColumn(3).setCellEditor(new EditorBotones(new JCheckBox(), jTable1, fechaActual));

        // Ajustar ancho de columnas (Ahora 4 anchos)
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100); // Hora
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(350); // Nombre 
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200); // Disponibilidad (NUEVO)
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(300); // Acciones (nueva columna 3)

        // Ajustar altura de filas
        jTable1.setRowHeight(40);

        // Eliminado: jTable1.setTableHeader(null); para que se muestre.
        jTable1.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 18));
        jTable1.setGridColor(new java.awt.Color(180, 214, 197));
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);

        // --- Cargar horarios predefinidos ---
        cargarHorariosDelDia();

        // --- Detectar clics en columna "Nombre" ---
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int column = jTable1.columnAtPoint(evt.getPoint());

                // Si clic en columna 2 (disponibilidad)
                if (column == 2) {
                    if (fechaActual.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(null,
                                "No es posible gestionar turnos de un día que ya haya transcurrido",
                                "Fecha inválida",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String disponibilidad = (String) jTable1.getValueAt(row, 2);
                    EditorBotones editor = new EditorBotones(new JCheckBox(), jTable1, fechaActual);

                    if (disponibilidad == null || disponibilidad.equalsIgnoreCase("Disponible") || disponibilidad.isEmpty()) {
                        editor.agendarTurno(row);  // Si está libre → agendar
                    } else {
                        editor.modificarTurno(row); // Si tiene paciente → modificar
                    }
                }
            }
        });

        // --- centrado y visibilidad ---
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

// MÉTODO - Cargar horarios del día
    private void cargarHorariosDelDia() {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        // Cargar turnos de la fecha actual desde TurnoManager
        for (Turno0 turno : TurnoManager.getInstancia().getTurnosPorFecha(fechaActual)) {

            String disponibilidad;
            String nombreMostrado;

            // 1. Verificar si el turno está Ocupado usando el campo Nombre
            // La condición de ocupado es: el nombre NO es nulo Y el nombre NO está vacío.
            if (turno.getNombre() != null && !turno.getNombre().trim().isEmpty()) {

                // Turno Ocupado:
                disponibilidad = "Ocupado"; // <- CORRECTO: Columna 2 muestra 'Ocupado'
                nombreMostrado = turno.getNombre(); // <- Columna 1 muestra 'juan peres'

            } else {
                // Turno Disponible:
                disponibilidad = "Disponible";
                nombreMostrado = ""; // <- Columna 1 queda vacía
            }

            modelo.addRow(new Object[]{
                turno.getHora(), // Columna 0: Hora
                nombreMostrado, // Columna 1: Nombre (solo paciente o vacío)
                disponibilidad, // Columna 2: Disponibilidad (Ocupado / Disponible)
                "" // Columna 3: Acciones
            // El campo MOTIVO (turno.getMotivo()) NO SE UTILIZA AQUÍ.
            });
        }
        modelo.fireTableDataChanged();
    }

    private void actualizarFechaYTurnos() {
        // Actualizar título con la fecha
        jLabel1.setText("Turnos del día - " + fechaActual.format(formatoFecha));

        // Actualizar el editor con la nueva fecha (índice 3 para Acciones)
        jTable1.getColumnModel().getColumn(3).setCellEditor(
                new EditorBotones(new JCheckBox(), jTable1, fechaActual)
        );

        // Recargar turnos
        cargarHorariosDelDia();
        jTable1.repaint();
        jTable1.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        // ELIMINADAS: jLabel2, jLabel3, jLabel4, jLabel5 (usamos la cabecera de la tabla)
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BotonCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(21, 70, 77));

        jPanel2.setBackground(new java.awt.Color(239, 239, 232));

        jButton1.setBackground(new java.awt.Color(21, 70, 77));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        jButton1.setText("Día anterior");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(21, 70, 77));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        jButton2.setText("Día siguiente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 70, 77));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Turnos del día");
        jLabel1.setPreferredSize(new java.awt.Dimension(800, 50));

        jSeparator1.setBackground(new java.awt.Color(21, 70, 77));
        jSeparator1.setForeground(new java.awt.Color(21, 70, 77));

        jScrollPane1.setBackground(new java.awt.Color(239, 239, 232));
        jScrollPane1.setForeground(new java.awt.Color(239, 239, 232));

        jTable1.setBackground(new java.awt.Color(239, 239, 232));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null}, // MODIFICADO: 4 columnas
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Hora", "Nombre", "Disponibilidad", "Acciones" // NUEVA COLUMNA Y CABECERAS NATIVAS
                }
        ) {
            // Sobrescribir el método para controlar la edición
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edición SOLO para la columna "Acciones" (índice 3)
                return column == 3;
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jSeparator1)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(293, 293, 293)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(46, 46, 46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(35, Short.MAX_VALUE))
        );

        BotonCerrarSesion.setBackground(new java.awt.Color(128, 185, 156));
        BotonCerrarSesion.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        BotonCerrarSesion.setForeground(new java.awt.Color(0, 51, 51));
        BotonCerrarSesion.setText("Cerrar sesion");
        BotonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        fechaActual = fechaActual.minusDays(1);
        actualizarFechaYTurnos();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        fechaActual = fechaActual.plusDays(1);
        actualizarFechaYTurnos();
    }

    /*
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       
     */
    private void BotonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this, // El componente padre (generalmente la ventana actual)
                "¿Estás seguro de que quieres cerrar la sesión?", // Mensaje de la advertencia
                "Confirmar Cierre de Sesión", // Título de la ventana
                javax.swing.JOptionPane.YES_NO_OPTION, // Botones (Sí/No)
                javax.swing.JOptionPane.WARNING_MESSAGE // Tipo de icono (Advertencia)
        );
        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual
            VistaLogin vistaLogin = new VistaLogin();
            vistaLogin.setVisible(true);
            LoginController loginController = new LoginController(vistaLogin);
            vistaLogin.setVisible(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaLista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    // Eliminadas las referencias a JLabel2, 3, 4, 5 de las declaraciones
    // private javax.swing.JLabel jLabel2;
    // private javax.swing.JLabel jLabel3;
    // private javax.swing.JLabel jLabel4;
    // private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   

    private LocalDate fechaActual;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
/*package Vista;

import Controlador.LoginController;
import Modelo.Turno0;
import Modelo.TurnoManager;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VistaLista extends javax.swing.JFrame {

    public VistaLista() {
        initComponents();

        fechaActual = LocalDate.now();
        jLabel1.setText("Turnos del día - " + fechaActual.format(formatoFecha));
        System.out.println("Turnos del dia - " + fechaActual.format(DateTimeFormatter.ofPattern("dd/MM")));
        // --- personalización visual de la tabla ---
        jScrollPane1.setColumnHeader(null);

        // Aplicar renderer personalizado para colorear (versión inline)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String nombre = (String) table.getValueAt(row, 1);

                if (nombre != null && nombre.equalsIgnoreCase("Disponible")) {
                    cell.setBackground(new Color(220, 240, 220));
                    cell.setForeground(new Color(100, 100, 100));
                } else {
                    cell.setBackground(new Color(239, 239, 232));
                    cell.setForeground(new Color(21, 70, 77));
                }

                setHorizontalAlignment(JLabel.CENTER);
                return cell;
            }
        };

        // Modificado: Ahora solo 3 columnas de datos (0, 1) + 1 de botones (2)
        for (int i = 0; i < 2; i++) { // Aplica Renderer a 0 (Hora), 1 (Nombre)
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

// Columna 2 será la nueva Acciones (antes era la 3)
        jTable1.getColumnModel().getColumn(2).setCellRenderer(new RenderBotones());
        jTable1.getColumnModel().getColumn(2).setCellEditor(new EditorBotones(new JCheckBox(), jTable1, fechaActual));
        // Ajustar ancho de columnas
        // Modificado: 3 anchos (0, 1, 2)
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100); // Hora
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(550); // Nombre (le damos el ancho extra de Motivo)
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(350); // Acciones (nueva columna 2)

        // Ajustar altura de filas
        jTable1.setRowHeight(40);

        jTable1.setTableHeader(null);
        jTable1.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 18));
        jTable1.setGridColor(new java.awt.Color(180, 214, 197));
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);

        // --- Cargar horarios predefinidos ---
        cargarHorariosDelDia();

        // --- Detectar clics en columnas de "Nombre" o "Motivo" ---
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int column = jTable1.columnAtPoint(evt.getPoint());

                // Si clic en columnas 1 (nombre) o 2 (motivo)
                if (column == 1) {
                    String nombre = (String) jTable1.getValueAt(row, 1);
                    EditorBotones editor = new EditorBotones(new JCheckBox(), jTable1, fechaActual);

                    if (nombre == null || nombre.equalsIgnoreCase("Disponible") || nombre.isEmpty()) {
                        editor.agendarTurno(row);  // Si está libre → agendar
                    } else {
                        editor.modificarTurno(row); // Si tiene paciente → modificar
                    }
                }
            }
        });

        // --- centrado y visibilidad ---
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

// NUEVO MÉTODO - Cargar horarios del día
    private void cargarHorariosDelDia() {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        for (Turno0 turno : TurnoManager.getInstancia().getTurnosPorFecha(fechaActual)) {
            modelo.addRow(new Object[]{
                turno.getHora(),
                turno.getNombre(),
                ""
            });
        }
    }

    private void actualizarFechaYTurnos() {
        // Actualizar título con la fecha
        jLabel1.setText("Turnos del día - " + fechaActual.format(formatoFecha));

        // Actualizar el editor con la nueva fecha
        jTable1.getColumnModel().getColumn(3).setCellEditor(
                new EditorBotones(new JCheckBox(), jTable1, fechaActual)
        );

        // Recargar turnos
        cargarHorariosDelDia();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
 */
 /*@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BotonCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(21, 70, 77));

        jPanel2.setBackground(new java.awt.Color(239, 239, 232));

        jButton1.setBackground(new java.awt.Color(21, 70, 77));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        jButton1.setText("Día anterior");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(21, 70, 77));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        jButton2.setText("Día siguiente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 70, 77));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Turnos del día");
        jLabel1.setPreferredSize(new java.awt.Dimension(800, 50));

        jSeparator1.setBackground(new java.awt.Color(21, 70, 77));
        jSeparator1.setForeground(new java.awt.Color(21, 70, 77));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 70, 77));
        jLabel3.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 70, 77));
        jLabel4.setText("Motivo");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(21, 70, 77));
        jLabel5.setText("Acciones");

        jScrollPane1.setBackground(new java.awt.Color(239, 239, 232));
        jScrollPane1.setForeground(new java.awt.Color(239, 239, 232));

        jTable1.setBackground(new java.awt.Color(239, 239, 232));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "hora", "disponibilidad", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jLabel3)
                .addGap(295, 295, 295)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(273, 273, 273))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(293, 293, 293)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        BotonCerrarSesion.setBackground(new java.awt.Color(128, 185, 156));
        BotonCerrarSesion.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        BotonCerrarSesion.setForeground(new java.awt.Color(0, 51, 51));
        BotonCerrarSesion.setText("Cerrar sesion");
        BotonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1339, 1339, 1339)
                .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1075, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        fechaActual = fechaActual.minusDays(1);
        actualizarFechaYTurnos();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fechaActual = fechaActual.plusDays(1);
        actualizarFechaYTurnos();
    }//GEN-LAST:event_jButton2ActionPerformed
    /*
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
*//*
    private void BotonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarSesionActionPerformed

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this, // El componente padre (generalmente la ventana actual)
                "¿Estás seguro de que quieres cerrar la sesión?", // Mensaje de la advertencia
                "Confirmar Cierre de Sesión", // Título de la ventana
                javax.swing.JOptionPane.YES_NO_OPTION, // Botones (Sí/No)
                javax.swing.JOptionPane.WARNING_MESSAGE // Tipo de icono (Advertencia)
        );
        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual
            VistaLogin vistaLogin = new VistaLogin();
            vistaLogin.setVisible(true);
            LoginController loginController = new LoginController(vistaLogin);
            vistaLogin.setVisible(true);
        }
    }//GEN-LAST:event_BotonCerrarSesionActionPerformed

    /**
     * @param args the command line arguments
 *//*
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
 */
 /*  try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaLista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
 /*  java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaLista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private LocalDate fechaActual;
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}*/
