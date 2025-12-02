package vista;

import Modelo.Instalacion;
import Persistencia.InstalacionData;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class frmInstalacion extends javax.swing.JInternalFrame {

    private InstalacionData instalacionData = new InstalacionData();
    private DefaultTableModel modeloTabla;

    public frmInstalacion() {
        initComponents();
        modeloTabla = (DefaultTableModel) jTable1.getModel();
        jTextField1.setEnabled(true);
        jTextField1.setEditable(true);
        this.getContentPane().setBackground(new Color(245, 242, 232));
        cargarTabla();
        centrarColumnas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBoxEstado = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBotonGuardar = new javax.swing.JButton();
        jBotonModificar = new javax.swing.JButton();
        jBotonEliminar = new javax.swing.JButton();
        jBotonNuevo = new javax.swing.JButton();
        jBotonBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel2.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Instalación ");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        jTextField1.setForeground(new java.awt.Color(0, 0, 0));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Nombre:");

        jTextField2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Detalle de uso: ");

        jScrollPane1.setHorizontalScrollBar(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Precio: ");

        jTextField3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Estado: ");

        jCheckBoxEstado.setText("Activo");
        jCheckBoxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCheckBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEstadoActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Detalle de uso", "Precio", "Estado"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jBotonGuardar.setBackground(new java.awt.Color(33, 150, 243));
        jBotonGuardar.setForeground(new java.awt.Color(0, 0, 0));
        jBotonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/saveas_5165.png"))); // NOI18N
        jBotonGuardar.setText("Guardar");
        jBotonGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBotonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonGuardarActionPerformed(evt);
            }
        });

        jBotonModificar.setBackground(new java.awt.Color(143, 191, 159));
        jBotonModificar.setForeground(new java.awt.Color(0, 0, 0));
        jBotonModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/create_edit_modify_icon_176960.png"))); // NOI18N
        jBotonModificar.setText("Modificar");
        jBotonModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBotonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonModificarActionPerformed(evt);
            }
        });

        jBotonEliminar.setBackground(new java.awt.Color(245, 76, 64));
        jBotonEliminar.setForeground(new java.awt.Color(0, 0, 0));
        jBotonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash_bin_icon-icons.com_67981 2.png"))); // NOI18N
        jBotonEliminar.setText("Eliminar");
        jBotonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBotonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonEliminarActionPerformed(evt);
            }
        });

        jBotonNuevo.setBackground(new java.awt.Color(143, 191, 159));
        jBotonNuevo.setForeground(new java.awt.Color(0, 0, 0));
        jBotonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-new-page_icon-icons.com_71788.png"))); // NOI18N
        jBotonNuevo.setText("Nuevo");
        jBotonNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBotonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonNuevoActionPerformed(evt);
            }
        });

        jBotonBuscar.setBackground(new java.awt.Color(143, 191, 159));
        jBotonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jBotonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_icon_125165.png"))); // NOI18N
        jBotonBuscar.setText("Buscar");
        jBotonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBotonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(jCheckBoxEstado)))
                                .addGap(44, 44, 44)
                                .addComponent(jBotonBuscar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBotonGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(jBotonModificar)
                                .addGap(18, 18, 18)
                                .addComponent(jBotonNuevo)
                                .addGap(18, 18, 18)
                                .addComponent(jBotonEliminar)))
                        .addGap(42, 42, 42))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBotonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxEstado)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonGuardar)
                    .addComponent(jBotonModificar)
                    .addComponent(jBotonNuevo)
                    .addComponent(jBotonEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEstadoActionPerformed

    }//GEN-LAST:event_jCheckBoxEstadoActionPerformed

    private void jBotonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonGuardarActionPerformed
        try {
            Instalacion i = validarYHacerInstalacion();
            if (i == null) {
                return;
            }

            instalacionData.guardarInstalacion(i);

            jTextField1.setText(String.valueOf(i.getCodInstal()));
            JOptionPane.showMessageDialog(this, "Instalación guardada correctamente.");
            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la instalación: " + e.getMessage());
        }
    }//GEN-LAST:event_jBotonGuardarActionPerformed

    private void jBotonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificarActionPerformed
        try {
            int codigo = Integer.parseInt(jTextField1.getText().trim());
            String nombre = jTextField2.getText().trim();
            String detalle = jTextArea1.getText().trim();
            double precio = Double.parseDouble(jTextField3.getText().trim());
            boolean estado = jCheckBoxEstado.isSelected();

            //armo de cero la instalacion con lo que está en los campos
            Instalacion i = new Instalacion();
            i.setCodInstal(codigo);
            i.setNombre(nombre);
            i.setdetalledeuso(detalle);
            i.setPrecio30m(precio);
            i.setEstado(estado);

            instalacionData.editarInstalacion(i);

            JOptionPane.showMessageDialog(this, "Instalación modificada correctamente.");
            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
        }
    }//GEN-LAST:event_jBotonModificarActionPerformed

    private void jBotonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonEliminarActionPerformed
        try {
            String texto = jTextField1.getText().trim();

            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese o busque un código antes de eliminar.");
                return;
            }

            int codigo = Integer.parseInt(texto);

            //confirmacion
            int opc = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quiere eliminar la instalación con código: " + codigo + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (opc != JOptionPane.YES_OPTION) {
                return;
            }

            instalacionData.deshabilitarInstalacion(codigo);

            JOptionPane.showMessageDialog(this, "Instalación eliminada correctamente.");
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }//GEN-LAST:event_jBotonEliminarActionPerformed

    private Instalacion validarYHacerInstalacion() {
        String nombre = jTextField2.getText().trim();
        String detalle = jTextArea1.getText().trim();
        String precioTxt = jTextField3.getText().trim();
        boolean estado = jCheckBoxEstado.isSelected();

        //campos vacios
        if (nombre.isEmpty() || detalle.isEmpty() || precioTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return null;
        }

        //campo nombre
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
            return null;
        }

        //campo precio
        double precio;
        try {
            precio = Double.parseDouble(precioTxt);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.");
            return null;
        }

        //si esta bien devuelvo el objeto listo
        return new Instalacion(nombre, detalle, precio, estado);
    }

    private void jBotonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonNuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jBotonNuevoActionPerformed

    private void jBotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonBuscarActionPerformed
        try {
            String textoCodigo = jTextField1.getText().trim();
            String nombreTexto = jTextField2.getText().trim();
            Instalacion i = null;

            if (!textoCodigo.isEmpty()) {
                //buscamos por codigo si se escribio uno
                int codigo = Integer.parseInt(textoCodigo);
                i = instalacionData.buscarPorCodigo(codigo);
            } else if (!nombreTexto.isEmpty()) {
                //si no hay codigo, busca por nombre
                i = instalacionData.buscarPorNombre(nombreTexto);
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un código o nombre para buscar.");
                return;
            }

            if (i == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una instalación con esos datos.");
                return;
            }

            jTextField1.setText(String.valueOf(i.getCodInstal()));
            jTextField2.setText(i.getNombre());
            jTextArea1.setText(i.getdetalledeuso());
            jTextField3.setText(String.valueOf(i.getPrecio30m()));
            jCheckBoxEstado.setSelected(i.isEstado());
            if (!i.isEstado()) {
                JOptionPane.showMessageDialog(this, "La instalación existe pero está desactivada.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar instalación: " + e.getMessage());
        }
    }//GEN-LAST:event_jBotonBuscarActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void cargarTabla() {
        List<Instalacion> lista = instalacionData.listarInstalaciones();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (Instalacion i : lista) {
            modelo.addRow(new Object[]{
                i.getCodInstal(),
                i.getNombre(),
                i.getdetalledeuso(),
                i.getPrecio30m(),
                i.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void limpiarCampos() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextArea1.setText("");
        jTextField3.setText("");
        jCheckBoxEstado.setSelected(true);
    }

    private void centrarColumnas() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonBuscar;
    private javax.swing.JButton jBotonEliminar;
    private javax.swing.JButton jBotonGuardar;
    private javax.swing.JButton jBotonModificar;
    private javax.swing.JButton jBotonNuevo;
    private javax.swing.JCheckBox jCheckBoxEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
