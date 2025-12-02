package vista;

import Modelo.DiadeSpa;
import Modelo.Instalacion;
import Modelo.Sesion;
import Persistencia.DiadeSpaData;
import Persistencia.SesionData;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

public class frmInforme extends javax.swing.JInternalFrame {

    private DiadeSpaData diaSpaData = new DiadeSpaData();
    private SesionData sesionData = new SesionData();
    private DefaultTableModel modelo;

    public frmInforme() {
        initComponents();
        this.getContentPane().setBackground(new Color(245, 242, 232));
        modelo = (DefaultTableModel) jTable1.getModel();
        limpiarTabla();
        cargarFechasDisponibles();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBotonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        lbTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Informe Dia de Spa    ");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Fecha: ");

        jBotonBuscar.setBackground(new java.awt.Color(143, 191, 159));
        jBotonBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jBotonBuscar.setForeground(new java.awt.Color(0, 0, 0));
        jBotonBuscar.setText("Buscar");
        jBotonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonBuscarActionPerformed(evt);
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
                "Fecha", "Horario", "Instalacion", "Tratamiento", "Masajista", "Consultorio", "Monto por sesión"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        lbTotal.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        lbTotal.setText("Total");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(256, 256, 256)
                            .addComponent(jLabel2)
                            .addGap(43, 43, 43)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(42, 42, 42)
                            .addComponent(jBotonBuscar))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonBuscar))
                .addGap(47, 47, 47)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarClientesPorFecha(LocalDate fechaBuscada) {
        jComboBox2.removeAllItems();
        List<DiadeSpa> dias = diaSpaData.obtenerDiasPorFecha(fechaBuscada);
        jComboBox2.addItem("Seleccione un Dia de Spa");
        for (DiadeSpa dia : dias) {
            //verificamos si el Dia de Spa tiene sesiones asociadas
            List<Sesion> sesiones = sesionData.listarSesionesPorDiaSpa(dia.getCodPack());
            if (!sesiones.isEmpty()) {
                String item = "Día de Spa Cod. " + dia.getCodPack() + " - " + dia.getCliente().getNombreCompleto();
                jComboBox2.addItem(item);
            }
        }
    }
    private void jBotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonBuscarActionPerformed
        if (jComboBox1.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String texto = (String) jComboBox1.getSelectedItem();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaBuscada = LocalDate.parse(texto, formato);

            cargarClientesPorFecha(fechaBuscada);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(this, "Formato inválido. Use dd-MM-yyyy");
        }
    }

    private void limpiarTabla() {
        modelo.setRowCount(0);
    }

    private void cargarFechasDisponibles() {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Seleccione una fecha");
        List<DiadeSpa> lista = diaSpaData.listarDiasDeSpa();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (DiadeSpa d : lista) {
            String fecha = d.getFechaHoraInicio().toLocalDate().format(formatter);
            if (!contieneFecha(jComboBox1, fecha)) {
                jComboBox1.addItem(fecha);
            }
        }
    }

    //metodo auxiliar para verificar si una fecha ya está en el combobox
    private boolean contieneFecha(JComboBox<String> comboBox, String fecha) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(fecha)) {
                return true;
            }
        }
        return false;
    }

    private void cargarTablaPorFecha(LocalDate fechaBuscada) {
        limpiarTabla();
        List<DiadeSpa> dias = diaSpaData.obtenerDiasPorFecha(fechaBuscada);
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        double totalAcumulado = 0;

        for (DiadeSpa dia : dias) {
            List<Sesion> sesiones = sesionData.listarSesionesPorDiaSpa(dia.getCodPack());
            for (Sesion s : sesiones) {
                String tratamiento = (s.getTratamiento() != null) ? s.getTratamiento().getNombre() : "-";
                List<Instalacion> insts = sesionData.obtenerInstalacionesDeSesion(s.getCodSesion());
                String instalaciones = insts.stream()
                        .map(Instalacion::getNombre)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("-");
                String horario = s.getFechaHoraInicio().format(horaFormatter) + " - " + s.getFechaHoraFin().format(horaFormatter);
                double montoSesion = insts.stream().mapToDouble(Instalacion::getPrecio30m).sum() + s.getTratamiento().getCosto();
                modelo.addRow(new Object[]{
                    s.getFechaHoraInicio().toLocalDate(),
                    horario,
                    instalaciones,
                    tratamiento,
                    s.getMasajista().getNombre(),
                    s.getConsultorio().getNroConsultorio(),
                    "$" + montoSesion
                });
                totalAcumulado += montoSesion + 40;
            }
        }
        lbTotal.setText("TOTAL = $" + totalAcumulado);
    }//GEN-LAST:event_jBotonBuscarActionPerformed

    private void cargarTablaPorCod(String cod) {
        limpiarTabla();
        List<Sesion> sesiones = sesionData.listarSesionesPorDiaSpa(Integer.parseInt(cod));
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        double totalAcumulado = 0;

        for (Sesion s : sesiones) {
            //obtenemos tratamiento
            String tratamiento = (s.getTratamiento() != null) ? s.getTratamiento().getNombre() : "-";
            //obtenemos instalaciones
            List<Instalacion> insts = sesionData.obtenerInstalacionesDeSesion(s.getCodSesion());
            String instalaciones = insts.stream()
                    .map(Instalacion::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("-");
            //obtenemos horario
            String horario = s.getFechaHoraInicio().format(horaFormatter) + " - " + s.getFechaHoraFin().format(horaFormatter);
            //calculamos monto por sesión (instalaciones + tratamiento)
            double montoSesion = insts.stream().mapToDouble(Instalacion::getPrecio30m).sum() + s.getTratamiento().getCosto();
            //agregamos fila a la tabla
            modelo.addRow(new Object[]{
                s.getFechaHoraInicio().toLocalDate(), //fecha
                horario, //horario
                instalaciones, //instalacion
                tratamiento, //tratamiento
                s.getMasajista().getNombre(), //masajista
                s.getConsultorio().getNroConsultorio(), //consultorio
                "$" + montoSesion //monto por sesion (instalaciones + tratamiento)
            });
            DiadeSpa dia = diaSpaData.buscarPorId(Integer.parseInt(cod));
            totalAcumulado = dia.getMonto();
        }
        //asignar el total acumulado al lbTotal
        lbTotal.setText("TOTAL = $" + totalAcumulado);
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if (jComboBox2.getSelectedIndex() <= 0) {
            return; //no procesa el Seleccione un dia de spa
        }

        String texto = jComboBox2.getSelectedItem().toString();

        //verifico que contiene "Cod." porque lo uso para extraer el numero
        if (!texto.contains("Cod.")) {
            return;
        }

        String cod = texto.split("Cod\\.")[1]
                .split("-")[0]
                .trim();

        cargarTablaPorCod(cod);
    }//GEN-LAST:event_jComboBox2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonBuscar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbTotal;
    // End of variables declaration//GEN-END:variables
}
