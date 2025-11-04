package Vista;

import javax.swing.*;
import javax.swing.BorderFactory;

public class VistaLogin extends javax.swing.JFrame {

    public VistaLogin() {
        initComponents();

        TextFieldUsuario.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        PasswordFieldContraseña.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        //jTextField1.setBorder(new RoundedBorder(10));
        //jTextField2.setBorder(new RoundedBorder(10));

        /*RoundedTextField txtUsuario = new RoundedTextField(15);
        txtUsuario.setBackground(new Color(239, 239, 232)); 
        txtUsuario.setText("Usuario");
        jPanel1.add(txtUsuario);
        txtUsuario.setBounds(100, 100, 200, 35);
         */
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkbox1 = new java.awt.Checkbox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TextFieldUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        PasswordFieldContraseña = new javax.swing.JPasswordField();

        checkbox1.setLabel("checkbox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(21, 70, 77));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(239, 239, 232));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("D R  .  A M A D O");
        jLabel1.setAlignmentY(1.0F);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(239, 239, 232));
        jLabel2.setText("CONSULTORIO");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(239, 239, 232));
        jLabel3.setText("Bienvenido");

        TextFieldUsuario.setBackground(new java.awt.Color(239, 239, 232));
        TextFieldUsuario.setFont(new java.awt.Font("Yu Gothic UI", 0, 20)); // NOI18N
        TextFieldUsuario.setForeground(new java.awt.Color(166, 166, 166));
        TextFieldUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        TextFieldUsuario.setText("Usuario");
        TextFieldUsuario.setActionCommand("<Not Set>");
        TextFieldUsuario.setBorder(null);
        TextFieldUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TextFieldUsuario.setMargin(new java.awt.Insets(2, 10, 2, 2));
        TextFieldUsuario.setOpaque(true);
        TextFieldUsuario.setSelectedTextColor(new java.awt.Color(166, 166, 166));
        TextFieldUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TextFieldUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TextFieldUsuarioFocusLost(evt);
            }
        });
        TextFieldUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldUsuarioActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(180, 214, 197));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 0, 28)); // NOI18N
        jButton1.setForeground(new java.awt.Color(21, 70, 77));
        jButton1.setText("Iniciar Sesion");
        jButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jButton1ItemStateChanged(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        PasswordFieldContraseña.setBackground(new java.awt.Color(239, 239, 232));
        PasswordFieldContraseña.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 20)); // NOI18N
        PasswordFieldContraseña.setForeground(new java.awt.Color(166, 166, 166));
        PasswordFieldContraseña.setText("Contraseña");
        PasswordFieldContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PasswordFieldContraseñaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PasswordFieldContraseñaFocusLost(evt);
            }
        });
        PasswordFieldContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PasswordFieldContraseña, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                            .addComponent(TextFieldUsuario, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(47, 47, 47))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFieldUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(PasswordFieldContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldUsuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       // ¡DEBE ESTAR VACÍO! El controlador manejará esto.
    // Si tuviera código aquí, tendría prioridad sobre el ActionListener del controlador.
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PasswordFieldContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordFieldContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordFieldContraseñaActionPerformed

    private void TextFieldUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TextFieldUsuarioFocusGained
        //cuando se hace click en el campo 
        if (TextFieldUsuario.getText().equals("Usuario")){
            TextFieldUsuario.setText("");
            TextFieldUsuario.setForeground(new java.awt.Color(0,0,0));
        }
    }//GEN-LAST:event_TextFieldUsuarioFocusGained

    private void TextFieldUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TextFieldUsuarioFocusLost
        //cuando sale del campo
        if (TextFieldUsuario.getText().trim().isEmpty()) {
            TextFieldUsuario.setText("Usuario");
            TextFieldUsuario.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_TextFieldUsuarioFocusLost

    private void PasswordFieldContraseñaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PasswordFieldContraseñaFocusGained
        if (String.valueOf(PasswordFieldContraseña.getPassword()).equals("Contraseña")) {
            PasswordFieldContraseña.setText("");
            PasswordFieldContraseña.setForeground(new java.awt.Color(0,0,0));
            PasswordFieldContraseña.setEchoChar('•');
        }
    }//GEN-LAST:event_PasswordFieldContraseñaFocusGained

    private void PasswordFieldContraseñaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PasswordFieldContraseñaFocusLost
        if (String.valueOf(PasswordFieldContraseña.getPassword()).trim().isEmpty()) {
            PasswordFieldContraseña.setText("Contraseña");
            PasswordFieldContraseña.setForeground(new java.awt.Color(153,153,153));
            PasswordFieldContraseña.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_PasswordFieldContraseñaFocusLost

    private void jButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jButton1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ItemStateChanged

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

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
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PasswordFieldContraseña;
    private javax.swing.JTextField TextFieldUsuario;
    private java.awt.Checkbox checkbox1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
// Dentro de la clase JFrame_principal
// ...

// Métodos Getters para los componentes
    public javax.swing.JButton getjButton1() {
        return jButton1;
    }

    public javax.swing.JTextField getjTextField1() {
        return TextFieldUsuario;
    }

    public javax.swing.JTextField jPasswordField1() {
        return PasswordFieldContraseña;
    }

// ...
}
