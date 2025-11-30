/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import Modelo.Sesion;
import Modelo.Tratamiento;
import Modelo.Consultorio;
import Modelo.Masajista;
import Modelo.DiadeSpa;
import Modelo.Instalacion;
import Persistencia.SesionData;
import Persistencia.TratamientoData;
import Persistencia.ConsultorioData;
import Persistencia.MasajistaData;
import Persistencia.DiadeSpaData;
import Persistencia.InstalacionData;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author santi
 */
public class FrmSesion extends javax.swing.JInternalFrame {

    private class CheckListRenderer extends JCheckBox implements ListCellRenderer<Object> {

        public CheckListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            setText(value.toString());
            setSelected(list.isSelectedIndex(index));

            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            return this;
        }
    }
    /**
     * Creates new form FrmSesion
     */
    private SesionData sesionData;
    private TratamientoData tratData;
    private ConsultorioData consulData;
    private MasajistaData masData;
    private DiadeSpaData diaSpaData;
    private InstalacionData instalacionData;
    private DefaultTableModel modelo;

    public FrmSesion() {
        initComponents();
        sesionData = new SesionData();
        tratData = new TratamientoData();
        consulData = new ConsultorioData();
        masData = new MasajistaData();
        diaSpaData = new DiadeSpaData();
        instalacionData = new InstalacionData();
        modelo = new DefaultTableModel();
        jTable1.setModel(modelo);
        armarTabla();
        cargarCombos();
        cargarListaInstalaciones();
        cargarTabla();
        this.getContentPane().setBackground(new Color(245, 242, 232));
        centrarColumnas();
        soloFechaHora(jTextField2);
        soloFechaHora(jTextField3);
    }

    private String instalacionesToString(List<Instalacion> lista) {
        String texto = "";
        boolean primero = true;
        boolean tieneElementos = false;
        double total = 0;

        try {

            for (Instalacion inst : lista) {

                tieneElementos = true;

                if (!primero) {
                    texto = texto + ", ";
                }

                // AGREGO EL CÓDIGO DE LA INSTALACIÓN
                texto = texto + inst.getCodInstal();

                primero = false;

                // SUMAMOS EL PRECIO REAL
                total = total + inst.getPrecio30m();
            }

            // Lista vacía
            if (!tieneElementos) {
                return "-";
            }

            // DEVUELVE CÓDIGOS + TOTAL
            return texto + " | Total: $" + total;

        } catch (Exception e) {
            return "-";
        }
    }

    private double calcularMontoSesion(DiadeSpa dia, List<Instalacion> instalaciones) {
        double total = 0.0;
        //instalaciones
        if (instalaciones != null) {
            for (Instalacion inst : instalaciones) {
                total = dia.getMonto() + inst.getPrecio30m();
            }
        }
        diaSpaData.editarDiaDeSpaMonto(dia, total);
        return total;
    }

    private void armarTabla() {
        modelo.addColumn("Código");
        modelo.addColumn("Inicio");
        modelo.addColumn("Fin");
        modelo.addColumn("Tratamiento");
        modelo.addColumn("Consultorio");
        modelo.addColumn("Masajista");
        modelo.addColumn("Pack Spa");
        modelo.addColumn("Estado");
        modelo.addColumn("Instalaciones");
    }

    private void limpiarCampos() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jCheckBox1.setSelected(false);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        listaInstalaciones.clearSelection();
    }

    private void cargarCombos() {

        jComboBox1.removeAllItems();
        jComboBox1.addItem("Seleccione un tratamiento");
        for (Tratamiento t : tratData.listarTratamientos()) {
            jComboBox1.addItem(t.getNombre());
        }

        jComboBox2.removeAllItems();
        jComboBox2.addItem("Seleccione un consultorio");
        for (Consultorio c : consulData.listarConsultorios()) {
            jComboBox2.addItem(String.valueOf(c.getNroConsultorio()));
        }

        jComboBox3.removeAllItems();
        jComboBox3.addItem("Seleccione un masajista");
        for (Masajista m : masData.listarMasajistas()) {
            if (m.isEstado()) {
                jComboBox3.addItem(m.getNombre());
            }
        }

        jComboBox4.removeAllItems();
        jComboBox4.addItem("Seleccione un dia de spa");
        for (DiadeSpa d : diaSpaData.listarDiasDeSpa()) {
            jComboBox4.addItem(String.valueOf(d.getCodPack()));
        }
    }

    private boolean validarCampos(boolean esModificacion) {

        // Si es MODIFICAR, debemos validar el ID
        if (esModificacion) {
            if (jTextField1.getText().trim().isEmpty()) {
                mostrarError("Debe ingresar el código de sesión para modificar.");
                jTextField1.requestFocus();
                return false;
            }
        }

        // VALIDAR FECHAS
        if (jTextField2.getText().trim().isEmpty()) {
            mostrarError("Debe ingresar la fecha y hora de inicio.");
            jTextField2.requestFocus();
            return false;
        }

        if (jTextField3.getText().trim().isEmpty()) {
            mostrarError("Debe ingresar la fecha y hora de fin.");
            jTextField3.requestFocus();
            return false;
        }

        // TRATAMIENTO
        if (jComboBox1.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un tratamiento.");
            return false;
        }

        // CONSULTORIO
        if (jComboBox2.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un consultorio.");
            return false;
        }

        // DÍA DE SPA
        if (jComboBox4.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar el día de Spa.");
            return false;
        }

        // INSTALACIONES
        if (listaInstalaciones.getSelectedValuesList().isEmpty()) {
            mostrarError("Debe seleccionar al menos una instalación.");
            return false;
        }

        // MASAJISTA
        System.out.println("MASAJISTAAAAAA: " + jComboBox3);
        System.out.println("MASAJISTAAAAAA INDEX: " + jComboBox3.getSelectedIndex());
        System.out.println("MASAJISTAAAAAA ITEM: " + jComboBox3.getSelectedItem());
        if (jComboBox3.getSelectedIndex() == 0) {
            mostrarError("Debe seleccionar un masajista.");
            return false;
        }

        return true;
    }

    private void limpiarTabla() {
        int filas = modelo.getRowCount() - 1;
        for (; filas >= 0; filas--) {
            modelo.removeRow(filas);
        }
    }

    private void cargarTabla() {
        limpiarTabla();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Sesion s : sesionData.listarSesiones()) {
            modelo.addRow(new Object[]{
                s.getCodSesion(),
                s.getFechaHoraInicio().format(formatter),
                s.getFechaHoraFin().format(formatter),
                s.getTratamiento().getNombre(),
                s.getConsultorio().getNroConsultorio(),
                s.getMasajista().getNombre(),
                s.getDiadeSpa().getCodPack(),
                s.isEstado() ? "Activo" : "Inactivo",
                instalacionesToString(s.getInstalaciones())
            });
        }
        centrarColumnas();
    }

    private void centrarColumnas() {
        javax.swing.table.DefaultTableCellRenderer centerRenderer
                = new javax.swing.table.DefaultTableCellRenderer();
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

        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jBotonGuardar = new javax.swing.JButton();
        jBotonModificar = new javax.swing.JButton();
        jBotonEliminar = new javax.swing.JButton();
        jBotonNuevo = new javax.swing.JButton();
        jBotonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaInstalaciones = new javax.swing.JList<>();

        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setText("Buscar");

        setBackground(new java.awt.Color(0, 0, 0));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sesión");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Código:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Fecha:");

        jTextField2.setEditable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Horario:");

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Tratamiento:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Consultorio:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Masajista:");

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Día de Spa:");

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("Estado:");

        jCheckBox1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Activo");
        jCheckBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Inicio", "Fin", "Tratamiento", "Consultorio", "Masajista", "Dia de Spa", "Estado", "Instalaciones", "Monto Total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Sesión");

        jLabel11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel11.setText("Codigo:");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel12.setText("Hora Inicio:");

        jLabel13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel13.setText("Hora Fin:");

        jLabel14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel14.setText("Tratamiento:");

        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel15.setText("Consultorio:");

        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel16.setText("Masajista:");

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel17.setText("Dia de Spa:");

        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel18.setText("Estado");

        jCheckBox2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jCheckBox2.setText("Activo");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/saveas_5165.png"))); // NOI18N
        jButton8.setText("Guardar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/create_edit_modify_icon_176960.png"))); // NOI18N
        jButton9.setText("Modificar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash_bin_icon-icons.com_67981 2.png"))); // NOI18N
        jButton10.setText("Eliminar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add-new-page_icon-icons.com_71788.png"))); // NOI18N
        jButton11.setText("Nuevo");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search_icon_125165.png"))); // NOI18N
        jButton12.setText("Buscar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Inicio", "Fin", "Tratamiento", "Consultorio", "Masajista", "Dia de Spa", "Estado"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18))
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addComponent(jCheckBox2))))
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(162, 162, 162)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16))
                                        .addGap(27, 27, 27)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9)
                                .addGap(18, 18, 18)
                                .addComponent(jButton10)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton11)
                            .addComponent(jButton12))
                        .addGap(28, 28, 28)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(238, 238, 238))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jCheckBox2))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel19.setText("Instalaciones:");

        listaInstalaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(listaInstalaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel19))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jCheckBox1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jBotonBuscar))
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(213, 213, 213))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBotonGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBotonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBotonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBotonEliminar))
                            .addComponent(jLabel7))
                        .addContainerGap())))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 358, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 358, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBotonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonGuardar)
                    .addComponent(jBotonModificar)
                    .addComponent(jBotonNuevo)
                    .addComponent(jBotonEliminar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 425, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 426, Short.MAX_VALUE)))
        );

        jTextField1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBotonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonModificarActionPerformed
        try {

            // 1. Validar código
            int codigo = Integer.parseInt(jTextField1.getText().trim());
            Sesion sesionExistente = sesionData.buscarPorCodSesion(codigo);

            if (sesionExistente == null) {
                mostrarError("No se encontró una sesión con el código: " + codigo);
                return;
            }

            // 2. Validar campos obligatorios
            if (!validarCampos(true)) {
                return;
            }

            // 3. Obtener objetos seleccionados
            Tratamiento tratamiento = obtenerTratamientoSeleccionado();
            Consultorio consultorio = obtenerConsultorioSeleccionado();
            Masajista masajista = obtenerMasajistaSeleccionado();
            DiadeSpa diadeSpa = obtenerDiadeSpaSeleccionado();

            // 4. Validar fechas
            String inicioStr = jTextField2.getText().trim();
            String finStr = jTextField3.getText().trim();

            LocalDateTime[] fechas = parsearFechas(inicioStr, finStr);
            LocalDateTime inicio = fechas[0];
            LocalDateTime fin = fechas[1];

            if (inicio.isAfter(fin)) {
                mostrarError("La fecha/hora de inicio debe ser anterior a la de fin.");
                return;
            }

            // 5. validaciones de horarios 
            if (sesionData.estaOcupadoMasajistaExcepto(codigo, masajista.getMatricula(), inicio, fin)) {
                mostrarError("El masajista seleccionado ya está ocupado en esa franja horaria.");
                return;
            }

            if (sesionData.estaOcupadoConsultorioExcepto(codigo, consultorio.getNroConsultorio(), inicio, fin)) {
                mostrarError("El consultorio seleccionado ya está ocupado en esa franja horaria.");
                return;
            }

            if (sesionData.estaOcupadoDiaSpaExcepto(codigo, diadeSpa.getCodPack(), inicio, fin)) {
                mostrarError("El Día de Spa seleccionado ya está ocupado en esa franja horaria.");
                return;
            }

            for (Instalacion ins : obtenerInstalacionesSeleccionadas()) {
                if (sesionData.estaOcupadaInstalacionExcepto(codigo, ins.getCodInstal(), inicio, fin)) {
                    mostrarError("La instalación " + ins.getNombre() + " está ocupada en esa franja horaria.");
                    return;
                }
            }

            // 6. Actualizar valores de la sesión
            sesionExistente.setFechaHoraInicio(inicio);
            sesionExistente.setFechaHoraFin(fin);
            sesionExistente.setTratamiento(tratamiento);
            sesionExistente.setConsultorio(consultorio);
            sesionExistente.setMasajista(masajista);
            sesionExistente.setDiadeSpa(diadeSpa);
            sesionExistente.setInstalaciones(obtenerInstalacionesSeleccionadas());
            sesionExistente.setEstado(jCheckBox1.isSelected());

            double total = calcularMontoSesion(diadeSpa, obtenerInstalacionesSeleccionadas());
            JOptionPane.showMessageDialog(this, "Monto total calculado: $" + total);

            // reasignar el día de spa a la sesión
            sesionExistente.setDiadeSpa(diadeSpa);

            // 7. Guardar cambios
            sesionData.editarSesion(sesionExistente);
            mostrarMensaje("Sesión con código " + codigo + " modificada exitosamente.", "Modificación Exitosa");

            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException e) {
            mostrarError("Debe ingresar un código de sesión válido.");
        } catch (DateTimeParseException e) {
            mostrarError("Formato de fecha/hora inválido. Use dd-MM-yyyy HH:mm");
        } catch (Exception e) {
            mostrarError("Error al modificar la sesión: " + e.getMessage());
        }
    }//GEN-LAST:event_jBotonModificarActionPerformed

    private void jBotonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonGuardarActionPerformed
        if (!validarCampos(false)) {
            return;
        }

        Tratamiento tratamiento = obtenerTratamientoSeleccionado();
        Consultorio consultorio = obtenerConsultorioSeleccionado();
        Masajista masajista = obtenerMasajistaSeleccionado();
        System.out.println("MASAJISTA SELECCIONADO: " + masajista);
        DiadeSpa diadeSpa = obtenerDiadeSpaSeleccionado();

        String inicioStr = jTextField2.getText().trim();
        String finStr = jTextField3.getText().trim();

        LocalDateTime[] fechas;
        try {
            fechas = parsearFechas(inicioStr, finStr);
        } catch (DateTimeParseException ex) {
            mostrarError("Formato de fecha incorrecto.");
            return;
        }

        LocalDateTime inicio = fechas[0];
        LocalDateTime fin = fechas[1];

        // Validar disponibilidad del masajista
        if (sesionData.estaOcupadoMasajista(masajista.getMatricula(), inicio, fin)) {
            mostrarError("El masajista seleccionado ya tiene una sesión en esta franja horaria.");
            return;
        }

        //validar disponibilidad del consultorio
        if (sesionData.estaOcupadoConsultorio(consultorio.getNroConsultorio(), inicio, fin)) {
            mostrarError("El consultorio seleccionado está ocupado en esta franja horaria.");
            return;
        }
        // validar disponibilidad del día de spa
        if (sesionData.estaOcupadoDiaSpa(diadeSpa.getCodPack(), inicio, fin)) {
            mostrarError("El Día de Spa seleccionado ya está asignado a otra sesión en ese horario.");
            return;
        }

        for (Instalacion ins : obtenerInstalacionesSeleccionadas()) {
            if (sesionData.estaOcupadaInstalacion(ins.getCodInstal(), inicio, fin)) {
                mostrarError("La instalación " + ins.getNombre() + " está ocupada en esa franja horaria.");
                return;
            }
        }

        // crear sesion
        Sesion nuevaSesion = new Sesion(
                inicio,
                fin,
                tratamiento,
                consultorio,
                masajista,
                diadeSpa,
                jCheckBox1.isSelected()
        );

        // ASIGNAR instalaciones seleccionadas antes de guardar
        nuevaSesion.setInstalaciones(obtenerInstalacionesSeleccionadas());

        double total = calcularMontoSesion(diadeSpa, obtenerInstalacionesSeleccionadas());
        JOptionPane.showMessageDialog(this, "Monto total calculado: $" + total);

        sesionData.guardarSesion(nuevaSesion);
        mostrarMensaje("Sesión registrada correctamente.", "Éxito");
        cargarTabla();
        limpiarCampos();
    }//GEN-LAST:event_jBotonGuardarActionPerformed

    private void jBotonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonEliminarActionPerformed
        try {
            int codigo = Integer.parseInt(jTextField1.getText().trim());

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea ELIMINAR la sesión con código " + codigo + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            sesionData.borrarSesion(codigo);
            JOptionPane.showMessageDialog(this, "Sesión eliminada correctamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarTabla();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el código de la sesión a eliminar.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la sesión. Revise la consola por el detalle SQL.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBotonEliminarActionPerformed

    private void jBotonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonNuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jBotonNuevoActionPerformed

    private void jBotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonBuscarActionPerformed
        try {
            String codigoStr = jTextField1.getText().trim();
            if (codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un código para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo = Integer.parseInt(codigoStr);
            Sesion s = sesionData.buscarPorCodSesion(codigo);

            if (s != null) {
                // Cargar los datos simples
                jTextField2.setText(s.getFechaHoraInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                jTextField3.setText(s.getFechaHoraFin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                jCheckBox1.setSelected(s.isEstado());
                jComboBox1.setSelectedItem(s.getTratamiento().getNombre());
                jComboBox2.setSelectedItem(String.valueOf(s.getConsultorio().getNroConsultorio()));
                jComboBox3.setSelectedItem(s.getMasajista().getNombre());
                jComboBox4.setSelectedItem(String.valueOf(s.getDiadeSpa().getCodPack()));

                DefaultListModel<Instalacion> modeloLista
                        = (DefaultListModel<Instalacion>) listaInstalaciones.getModel();

                // Traemos desde la BD las instalaciones de la sesión
                List<Instalacion> instalacionesSesion = instalacionData.obtenerInstalacionesPorSesion(s.getCodSesion());

                // Sacamos cualquier selección previa
                listaInstalaciones.clearSelection();

                // Marcar en la JList las instalaciones que pertenecen a la sesión
                for (int i = 0; i < modeloLista.getSize(); i++) {
                    Instalacion instLista = modeloLista.getElementAt(i);

                    for (Instalacion instSesion : instalacionesSesion) {
                        if (instLista.getCodInstal() == instSesion.getCodInstal()) {
                            listaInstalaciones.addSelectionInterval(i, i); // seleccionar
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna sesión con el código: " + codigo, "Búsqueda Fallida", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código de sesión válido (número).", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBotonBuscarActionPerformed

    private Tratamiento obtenerTratamientoSeleccionado() {
        String nombreTratamiento = (String) jComboBox1.getSelectedItem();
        return tratData.listarTratamientos().stream()
                .filter(t -> t.getNombre().equals(nombreTratamiento))
                .findFirst().orElse(null);
    }

    private Consultorio obtenerConsultorioSeleccionado() {
        String nroConsultorioStr = (String) jComboBox2.getSelectedItem();
        if (nroConsultorioStr == null || nroConsultorioStr.isEmpty()) {
            return null;
        }
        try {
            int nroConsultorio = Integer.parseInt(nroConsultorioStr);
            return consulData.listarConsultorios().stream()
                    .filter(c -> c.getNroConsultorio() == nroConsultorio)
                    .findFirst().orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Masajista obtenerMasajistaSeleccionado() {
        String nombreMasajista = (String) jComboBox3.getSelectedItem();
        return masData.listarMasajistas().stream()
                .filter(m -> m.getNombre().equals(nombreMasajista))
                .findFirst().orElse(null);
    }

    private DiadeSpa obtenerDiadeSpaSeleccionado() {
        String codPackStr = (String) jComboBox4.getSelectedItem();
        if (codPackStr == null || codPackStr.isEmpty()) {
            return null;
        }
        try {
            int codPack = Integer.parseInt(codPackStr);
            return diaSpaData.listarDiasDeSpa().stream()
                    .filter(d -> d.getCodPack() == codPack)
                    .findFirst().orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<Instalacion> obtenerInstalacionesSeleccionadas() {
        return listaInstalaciones.getSelectedValuesList();
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDateTime[] parsearFechas(String inicioStr, String finStr) throws DateTimeParseException {
        LocalDateTime inicio = LocalDateTime.parse(inicioStr, FORMATTER);
        LocalDateTime fin = LocalDateTime.parse(finStr, FORMATTER);
        return new LocalDateTime[]{inicio, fin};
    }

    private void cargarListaInstalaciones() {
        DefaultListModel<Instalacion> lm = new DefaultListModel<>();
        for (Instalacion inst : instalacionData.listarInstalaciones()) {
            if (inst.isEstado()) {
                lm.addElement(inst);
            }
        }
        listaInstalaciones.setModel(lm);

        // Modo de selección (no usar MULTIPLE_INTERVAL_SELECTION)
        listaInstalaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //SELECTION MODEL que hace toggle con un solo click
        listaInstalaciones.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0 == index1) {
                    // Toggle: si está seleccionado, lo saco; si no, lo marco
                    if (isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index0);
                    } else {
                        super.addSelectionInterval(index0, index0);
                    }
                } else {
                    // permite seleccionar varios si arrastra (opcional)
                    super.setSelectionInterval(index0, index1);
                }
            }
        });

        //NECESARIO para que no robe foco y no active selección rara
        listaInstalaciones.setFocusable(false);

        //RENDERER con checkboxes — SIN ESTO NO FUNCIONA
        listaInstalaciones.setCellRenderer(new CheckListRenderer());
    }

    private void soloFechaHora(javax.swing.JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                // Solo números, guiones, slash, espacio y dos puntos
                if (!Character.isDigit(c) && c != '-' && c != '/' && c != ':' && c != ' ') {
                    evt.consume(); // Bloquea el carácter
                }
            }
        });
    }

    private void cargarFechasPorCod(int cod){
        DiadeSpa dia = diaSpaData.buscarPorId(cod);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        jTextField2.setText(dia.getFechaHoraInicio().toLocalDate().format(f)+"");
        jTextField3.setText(dia.getFechaHoraInicio().toLocalTime()+" - "+dia.getFechaHoraFin().toLocalTime());
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        //DIA DE SPA
        if (jComboBox4.getSelectedIndex() <= 0) {
            return; //no procesa el Seleccione un dia de spa
        }

        String cod = jComboBox4.getSelectedItem().toString();

        cargarFechasPorCod(Integer.parseInt(cod));
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBotonBuscar;
    private javax.swing.JButton jBotonEliminar;
    private javax.swing.JButton jBotonGuardar;
    private javax.swing.JButton jBotonModificar;
    private javax.swing.JButton jBotonNuevo;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JList<Instalacion> listaInstalaciones;
    // End of variables declaration//GEN-END:variables
}
