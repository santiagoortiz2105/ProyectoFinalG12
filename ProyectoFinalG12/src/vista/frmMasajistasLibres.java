package vista;

import java.awt.Color;
import Modelo.Masajista;
import Persistencia.MasajistaData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class frmMasajistasLibres extends javax.swing.JInternalFrame {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private MasajistaData masajistaData;
    private DefaultTableModel modelo;

    public frmMasajistasLibres() {
        initComponents();
        this.getContentPane().setBackground(new Color(245, 242, 232));
        cargarMeses();
        cargarFranjas();
        configurarTabla();
        masajistaData = new MasajistaData();
        actualizarTabla(masajistaData.listarMasajistas());
        centrarColumnas();
    }

    private void actualizarTabla(List<Masajista> lista) {
        modelo.setRowCount(0);

        for (Masajista m : lista) {
            String estado = m.isEstado() ? "Activo" : "Inactivo";

            modelo.addRow(new Object[]{
                m.getMatricula(),
                m.getNombre(),
                m.getApellido(),
                m.getTelefono(),
                m.getEspecialidad(),
                estado
            });
        }
    }

    private void configurarTabla() {
        String[] columnas = {"Matrícula", "Nombre", "Apellido", "Teléfono", "Especialidad", "Estado"};

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        jTable1.setModel(modelo);
    }

    private void cargarFranjas() {
        cbFranja.removeAllItems();
        cbFranja.addItem("Seleccione una franja horaria");
        cbFranja.addItem("08:00 - 10:00");
        cbFranja.addItem("11:00 - 13:00");
        cbFranja.addItem("14:00 - 16:00");
        cbFranja.addItem("17:00 - 19:00");
    }

    private void cargarMeses() {
        cbMes.removeAllItems();
        cbMes.addItem("Seleccione un mes");
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

    private void buscarMasajistas() {
        try {
            //valido día
            String diaTxt = txtDia.getText().trim();
            if (!diaTxt.matches("\\d{2}")) {
                JOptionPane.showMessageDialog(this, "El día debe tener dos dígitos (01-31)");
                return;
            }
            int dia = Integer.parseInt(diaTxt);
            if (dia < 1 || dia > 31) {
                JOptionPane.showMessageDialog(this, "Día fuera de rango");
                return;
            }

            //valido año
            String anioTxt = txtAnio.getText().trim();
            if (!anioTxt.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "El año debe ser de 4 dígitos");
                return;
            }
            int anio = Integer.parseInt(anioTxt);

            //valido mes
            if (cbMes.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Elija un mes");
                return;
            }
            int mes = cbMes.getSelectedIndex();

            //proceso la franja
            String franja = cbFranja.getSelectedItem().toString();
            String[] partes = franja.split(" - ");
            LocalTime inicio = LocalTime.parse(partes[0]);
            LocalTime fin = LocalTime.parse(partes[1]);

            LocalDateTime dtInicio = LocalDateTime.of(anio, mes, dia, inicio.getHour(), inicio.getMinute());
            LocalDateTime dtFin = LocalDateTime.of(anio, mes, dia, fin.getHour(), fin.getMinute());

            //valido que no sea una fecha pasada
            LocalDateTime ahora = LocalDateTime.now();

            if (dtInicio.toLocalDate().isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "La fecha no puede ser del pasado.");
                return;
            }

            if (dtInicio.toLocalDate().isEqual(LocalDate.now()) && dtInicio.isBefore(ahora)) {
                JOptionPane.showMessageDialog(this, "La hora seleccionada no puede ser anterior a la actual.");
                return;
            }

            List<Masajista> libres = masajistaData.obtenerMasajistasLibresFranja(dtInicio, dtFin);
            actualizarTabla(libres);

            if (libres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay masajistas libres en esa franja.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void centrarColumnas() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtDia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbMes = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbFranja = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setText("Masajistas libres");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Dia: ");

        btnBuscar.setBackground(new java.awt.Color(143, 191, 159));
        btnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Matricula", "Nombre", "Apellido", "Telefono", "Especialidad", "Estado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        txtDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Mes: ");

        cbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero ", "Febrero", "Marzo ", "Abril ", "Mayo ", "Junio", "Julio", "Agosto ", "Septiembre ", "Octubre ", "Noviembre", "Diciembre" }));
        cbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMesActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Año: ");

        txtAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnioActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Elija franja horaria: ");

        cbFranja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00", "17:00 - 19:00" }));
        cbFranja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFranjaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(btnBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel5)))
                .addGap(82, 82, 82))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbFranja, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(251, 251, 251))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(247, 247, 247))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(246, 246, 246))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(cbMes, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(168, 168, 168)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(cbFranja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarMasajistas();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaActionPerformed

    private void cbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMesActionPerformed

    private void txtAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioActionPerformed

    private void cbFranjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFranjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFranjaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cbFranja;
    private javax.swing.JComboBox<String> cbMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtDia;
    // End of variables declaration//GEN-END:variables
}
