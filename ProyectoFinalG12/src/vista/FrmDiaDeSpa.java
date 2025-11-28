package vista;

import Modelo.DiadeSpa;
import Modelo.Cliente;
import Persistencia.DiadeSpaData;
import Persistencia.ClienteData;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmDiaDeSpa extends javax.swing.JInternalFrame {

    private DiadeSpaData diaData = new DiadeSpaData();
    private ClienteData clienteData = new ClienteData();
    private DefaultTableModel modelo = new DefaultTableModel();

    public FrmDiaDeSpa() {
        initComponents();
        cargarClientes();
        armarTabla();
        cargarTabla();
        cargarMeses();
        cargarHorarios();
        this.getContentPane().setBackground(new Color(245, 242, 232));
        centrarColumnas();
    }

    private void cargarClientes() {
        cbCliente.removeAllItems();
        List<Cliente> lista = clienteData.listarClientes();
        for (Cliente c : lista) {
            cbCliente.addItem(c);
        };
    }

    private void armarTabla() {
        modelo.addColumn("Codigo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Preferencias");
        modelo.addColumn("Cliente");
        modelo.addColumn("Monto");
        modelo.addColumn("Estado");
        jTable1.setModel(modelo);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (DiadeSpa d : diaData.listarDiasDeSpa()) {
            modelo.addRow(new Object[]{
                d.getCodPack(),
                d.getFechaHoraInicio(),
                d.getFechaHoraFin(),
                d.getPreferencias(),
                d.getCliente().getNombreCompleto(),
                d.getMonto(),
                d.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void limpiarCampos() {
        jTextField1.setText("");
        tfDia.setText("");
        taPref.setText("");
        tfMonto.setText("");
        checkEstado.setSelected(false);
    }
    
    private boolean validarCampos() {

    //Dia
    if (tfDia.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar un día.");
        return false;
    }
    try {
        int dia = Integer.parseInt(tfDia.getText());
        if (dia < 1 || dia > 31) {
            JOptionPane.showMessageDialog(this, "El día debe estar entre 1 y 31.");
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El día debe ser un número válido.");
        return false;
    }

    //Año
    String textoAnio = tfAnio.getText().trim();

if (textoAnio.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Debe ingresar un año.");
    return false;
}

// Debe tener 4 dígitos
if (!textoAnio.matches("\\d{4}")) {
    JOptionPane.showMessageDialog(this, "El año debe tener exactamente 4 dígitos.");
    return false;
}

try {
    int anio = Integer.parseInt(textoAnio);

    // No puede ser menor a 2025
    if (anio < 2025) {
        JOptionPane.showMessageDialog(this, "El año no puede ser menor a 2025.");
        return false;
    }

    // No puede ser mayor a 2030
    if (anio > 2030) {
        JOptionPane.showMessageDialog(this, "El año no puede superar 2030.");
        return false;
    }

} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "El año debe ser un número.");
    return false;
    }

    //Horario
    if (cbFranja.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una franja horaria.");
        return false;
    }

    //Preferencias
    if (taPref.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar preferencias.");
        return false;
    }

    //Cliente
    if (cbCliente.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
        return false;
    }

    //Monto
    if (tfMonto.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar un monto.");
        return false;
    }
    try {
        double monto = Double.parseDouble(tfMonto.getText());
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El monto debe ser numérico.");
        return false;
    }

    return true;
}

    @SuppressWarnings("unchecked")

    private LocalDateTime convertirFecha() {
        try {
            return LocalDateTime.parse(tfDia.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use: dd/MM/yyyy HH:mm");
            return null;
        }
    }

    private void cargarMeses() {
        cbMes.removeAllItems();
        cbMes.addItem("Enero");
        cbMes.addItem("Febrero");
        cbMes.addItem("Marzo");
        cbMes.addItem("Abril");
        cbMes.addItem("Mayo");
        cbMes.addItem("Junio");
        cbMes.addItem("Julio");
        cbMes.addItem("Agosto");
        cbMes.addItem("Septiembre");
        cbMes.addItem("Octubre");
        cbMes.addItem("Noviembre");
        cbMes.addItem("Diciembre");
    }
    
    private void cargarHorarios() {
        cbFranja.removeAllItems();
        cbFranja.addItem("08:00 - 10:00");
        cbFranja.addItem("11:00 - 13:00");
        cbFranja.addItem("14:00 - 16:00");
        cbFranja.addItem("17:00 - 19:00");
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfDia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taPref = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        cbCliente = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        tfMonto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        checkEstado = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbFranja = new javax.swing.JComboBox<>();
        tfAnio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbMes = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        jLabel2.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Dia de Spa");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Dia:");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Preferencias:");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        taPref.setColumns(20);
        taPref.setRows(5);
        taPref.setPreferredSize(new java.awt.Dimension(234, 84));
        jScrollPane1.setViewportView(taPref);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Cliente:");

        cbCliente.setPreferredSize(new java.awt.Dimension(72, 26));
        cbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClienteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Monto:");

        tfMonto.setMinimumSize(new java.awt.Dimension(64, 26));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Estado");

        checkEstado.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        checkEstado.setText("Activo");
        checkEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton1.setBackground(new java.awt.Color(33, 150, 243));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/saveas_5165.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(143, 191, 159));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/create_edit_modify_icon_176960.png"))); // NOI18N
        jButton2.setText("Modificar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(143, 191, 159));
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-new-page_icon-icons.com_71788.png"))); // NOI18N
        jButton4.setText("Nuevo");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(245, 76, 64));
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash_bin_icon-icons.com_67981 2.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(143, 191, 159));
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_icon_125165.png"))); // NOI18N
        jButton5.setText("Buscar");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Horario Inicio", "Horario Fin", "Preferencias", "Cliente", "Monto", "Estado"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Mes:");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("Año:");

        cbFranja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tfAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAnioActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("Horario:");

        cbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(21, 21, 21)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton3))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                                    .addComponent(tfAnio, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbFranja, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(tfDia)
                                                    .addComponent(cbMes, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(2, 2, 2))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(tfMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel6))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(48, 48, 48)
                                                        .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(115, 115, 115)
                                                        .addComponent(checkEstado)))))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFranja, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //boton modificar
        if (!validarCampos()) {
         return;
        }
        if (jTextField1.getText().isEmpty()) {
            return;
        }
        FechaSpa resultado = obtenerFechasSpa();
        if (resultado == null) {
            return;
        }
        DiadeSpa d = new DiadeSpa();
        d.setCodPack(Integer.parseInt(jTextField1.getText()));
        d.setFechaHoraInicio(resultado.inicio);
        d.setFechaHoraFin(resultado.fin);
        d.setPreferencias(taPref.getText());
        d.setCliente((Cliente) cbCliente.getSelectedItem());
        d.setMonto(Double.parseDouble(tfMonto.getText()));
        d.setEstado(checkEstado.isSelected());

        diaData.editarDiaDeSpa(d);
        cargarTabla();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //boton guardar
        if (!validarCampos()) {
         return;
         }
        try {
            //validacion de fecha y horario
            FechaSpa resultado = obtenerFechasSpa();
            if (resultado == null) {
                return;
            }
            DiadeSpa d = new DiadeSpa();
            d.setFechaHoraInicio(resultado.inicio);
            d.setFechaHoraFin(resultado.fin);
            d.setPreferencias(taPref.getText());
            d.setCliente((Cliente) cbCliente.getSelectedItem());
            d.setMonto(Double.parseDouble(tfMonto.getText()));
            d.setEstado(checkEstado.isSelected());

            diaData.guardarDiaDeSpa(d);
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (jTextField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar o seleccionar un Día de Spa para eliminar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            int codPack = Integer.parseInt(jTextField1.getText().trim());
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea ELIMINAR el Día de Spa con código " + codPack + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                diaData.deshabilitarDiaDeSpa(codPack);
                JOptionPane.showMessageDialog(
                        this,
                        "Día de Spa eliminado correctamente.",
                        "Eliminación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarTabla();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Código inválido. Debe ingresar un número.",
                    "Error de Entrada",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el Día de Spa. Revise la consola.",
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //boton buscar
        if (jTextField1.getText().isEmpty()) {
            return;
        }
        DiadeSpa d = diaData.buscarPorId(Integer.parseInt(jTextField1.getText()));
        if (d != null) {
            //fechas inicio y fin
            LocalDateTime inicio = d.getFechaHoraInicio();
            LocalDateTime fin = d.getFechaHoraFin();

            //dia
            tfDia.setText(String.format("%02d", inicio.getDayOfMonth()));

            //mes
            cbMes.setSelectedIndex(inicio.getMonthValue() - 1);

            //año
            tfAnio.setText(String.valueOf(inicio.getYear()));

            //franja horaria
            //convertimos la franja a string, igual al formato del combobox HH:mm - HH:mm
            String franja = inicio.toLocalTime().toString() + " - " + fin.toLocalTime().toString();

            //buscamos en el combobox y seleccionamos la que coincida exactactamente
            for (int i = 0; i < cbFranja.getItemCount(); i++) {
                if (cbFranja.getItemAt(i).equals(franja)) {
                    cbFranja.setSelectedIndex(i);
                    break;
                }
            }

            //los demas campos
            taPref.setText(d.getPreferencias());
            tfMonto.setText(String.valueOf(d.getMonto()));
            checkEstado.setSelected(d.isEstado());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tfAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAnioActionPerformed

    private void cbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbClienteActionPerformed

    private void centrarColumnas() {
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private FechaSpa obtenerFechasSpa() {
        try {
            int dia = Integer.parseInt(tfDia.getText().trim());
            int mes = cbMes.getSelectedIndex() + 1;
            int anio = Integer.parseInt(tfAnio.getText().trim());

            LocalDate fecha = LocalDate.of(anio, mes, dia);

            String franja = (String) cbFranja.getSelectedItem();
            String[] partes = franja.split(" - ");

            LocalTime horaInicio = LocalTime.parse(partes[0].trim());
            LocalTime horaFin = LocalTime.parse(partes[1].trim());

            LocalDateTime inicio = LocalDateTime.of(fecha, horaInicio);
            LocalDateTime fin = LocalDateTime.of(fecha, horaFin);
            return new FechaSpa(inicio, fin);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos de fecha u horario inválidos");
            return null;
        }
    }

    private static class FechaSpa {
        LocalDateTime inicio;
        LocalDateTime fin;
        FechaSpa(LocalDateTime i, LocalDateTime f) {
            inicio = i;
            fin = f;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Cliente> cbCliente;
    private javax.swing.JComboBox<String> cbFranja;
    private javax.swing.JComboBox<String> cbMes;
    private javax.swing.JCheckBox checkEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea taPref;
    private javax.swing.JTextField tfAnio;
    private javax.swing.JTextField tfDia;
    private javax.swing.JTextField tfMonto;
    // End of variables declaration//GEN-END:variables
}
