/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.vista.jpanels;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import mathiasbattistella.controlador.*;
import mathiasbattistella.dto.*;

/**
 *
 * @author user6
 */
public final class PMovimientoMensual extends javax.swing.JPanel {

    private final String FORMAT_DATE = "dd/MM/yyyy";
    private final String ERROR_CATEGORIA_NO_MARCADA = "No se han encontrado categorias seleccionadas, necesita seleccionar una para ingresar un movimiento";
    private MovimientoMensual movimiento_mensual;
    private DefaultListModel lstm;    
    private ArrayList<Categoria> lista_categorias_por_nombre = null;
    private ArrayList<Categoria> lista_categorias_por_descripcion = null;    
    private String nombre;
    private double monto;
    private String descripcion;
    private java.sql.Date fecha_de_vencimiento;
    private java.sql.Date fecha_pagado;
    private boolean es_ingreso;
    private boolean pago;
    private int recargo;
    private Categoria categoria;
    private String id;
    private boolean encontrado;
    //Obtengo la fecha actual e instancio las variables calendario
    private final Calendar calendario_fecha_pago = Calendar.getInstance();
    private final Calendar calendario_fecha_vencimiento;
    
    /**
     * para borrar
     */
    private void limpiar(){
        txtCategoria.setText("");
        txtDescripcion.setText("");
        txtMonto.setText("0");
        txtNombre.setText("");
        lblMensaje.setText("");
        txtMontoTotal.setText("0.0");
        spRecargo.setValue(0);
        dateFechaPagado.setSelectedDate(calendario_fecha_pago);
        dateVencimiento.setSelectedDate(calendario_fecha_vencimiento);
        cargarListaCategorias();
    }
    
    /**
     * método de búsqueda
     */    
    private void buscar(){
        try {
            encontrado = false;
            id = spinnerId.getValue().toString();
            movimiento_mensual = CMovimientoMensual.obtenerMovimientoMensual(Integer.parseInt(id));
            txtNombre.setText(movimiento_mensual.getNombre());
            txtDescripcion.setText(movimiento_mensual.getDescripcion());
            txtMonto.setText(movimiento_mensual.getMontoSinRercargos()+ "");
            chkPago.setSelected(movimiento_mensual.isPago());
            if (movimiento_mensual.isPago()){
                dateFechaPagado.setEnabled(true);                
                btnHoy.setEnabled(true);
                chkPago.setSelected(true);
                calendario_fecha_pago.setTime(movimiento_mensual.getFecha_pagado());
                dateFechaPagado.setSelectedDate(calendario_fecha_pago);
            }else {
                //lo dejo en hoy, por si se desea marcar pagado, ya se marque el dia actual por defecto
                dateFechaPagado.setSelectedDate(Calendar.getInstance());
                dateFechaPagado.setEnabled(false);
                btnHoy.setEnabled(false);
                chkPago.setSelected(false);
            }            
            chkPago.setSelected(movimiento_mensual.isPago());
            spRecargo.setValue(movimiento_mensual.getRecargo());
            txtCategoria.setText(movimiento_mensual.getCategoria().getNombre());
            cargarListaCategorias();
            chkIngreso.setSelected(movimiento_mensual.isEs_ingreso());
            calendario_fecha_vencimiento.setTime(movimiento_mensual.getFecha_de_vencimiento());
            dateVencimiento.setSelectedDate(calendario_fecha_vencimiento);
            txtMontoTotal.setText(movimiento_mensual.getMonto() + "");
            lblMensaje.setText("Encontrado");
            encontrado = true;
        }catch(Error e){
            limpiar();
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"busqueda\" debe ingresar solo números positivos o el 0");
        }catch(Exception e){
            limpiar();
            lblMensaje.setText("Ocurrió un error desconocido"+e.getMessage() + e.getClass().getName());
        }        
    }
    
    /**
     * Para cargar la lista con las categorias
     * @return 
     */
    private boolean cargarListaCategorias(){
        try{
            String dato = txtCategoria.getText();
            lista_categorias_por_nombre = CCategoria.buscarCategorias("nombre", dato,"nombre");
            lista_categorias_por_descripcion = CCategoria.buscarCategorias("descripcion", dato,"nombre");
            lista_categorias_por_descripcion.stream().filter((c) -> (!lista_categorias_por_nombre.contains(c))).forEach((c) -> {
                lista_categorias_por_nombre.add(c);
            });
            lstm = new DefaultListModel();
            lista_categorias_por_nombre.stream().forEach((c) -> {
                lstm.addElement(c);
            });
            lstCategoria.setModel(lstm);
            lstCategoria.setSelectedIndex(0);

            if (lstm.size() == 0){
                lblMensaje.setText(ERROR_CATEGORIA_NO_MARCADA);
                return false;
            }else{
                lblMensaje.setText("");
            }
            
        } catch (Error e) {
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            lblMensaje.setText("Ha ocurrido un error " + e.getMessage());
        }
        return true;
    }    
    
//    private String
    /**
     * Creates new form MovimientoMensual
     */
    public PMovimientoMensual() {
        this.calendario_fecha_vencimiento = Calendar.getInstance();
        
        initComponents();
        txtMontoTotal.setEditable(false);
        chkPago.setSelected(true);
        btnEditar1.setEnabled(false);
        btnBaja1.setEnabled(false);        
        cargarListaCategorias();
        //Establezco el formato de fecha para los JDataChoose
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE) ;
        dateFechaPagado.setDateFormat(dateFormat);
        dateVencimiento.setDateFormat(dateFormat);
        limpiar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dateVencimiento = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnAltaIngreso = new javax.swing.JToggleButton();
        btnBaja1 = new javax.swing.JToggleButton();
        btnEditar1 = new javax.swing.JToggleButton();
        chkIngreso = new javax.swing.JCheckBox();
        txtMontoTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstCategoria = new javax.swing.JList<>();
        txtCategoria = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        dateFechaPagado = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JToggleButton();
        spinnerId = new javax.swing.JSpinner();
        chkPago = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        spRecargo = new javax.swing.JSpinner();
        btnHoy = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();

        jPanel6.setForeground(new java.awt.Color(153, 153, 153));

        jLabel7.setText("Nombre:");

        jLabel8.setText("Monto:");

        txtMonto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMontoFocusGained(evt);
            }
        });
        txtMonto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMontoMouseClicked(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane4.setViewportView(txtDescripcion);

        jLabel10.setText("Descripción:");

        dateVencimiento.setNothingAllowed(false);
        dateVencimiento.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel1.setText("Fecha de Vencimiento:");

        btnAltaIngreso.setText("Alta");
        btnAltaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaIngresoActionPerformed(evt);
            }
        });

        btnBaja1.setText("Baja");
        btnBaja1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaja1ActionPerformed(evt);
            }
        });

        btnEditar1.setText("Editar");
        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnAltaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBaja1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAltaIngreso)
                    .addComponent(btnBaja1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(dateVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dateVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        chkIngreso.setText("Es ingreso");

        jLabel3.setText("Total:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMontoTotal)
                                .addGap(6, 6, 6)
                                .addComponent(chkIngreso))
                            .addComponent(jScrollPane4)
                            .addComponent(txtNombre))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMonto)
                    .addComponent(chkIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane3.setViewportView(lstCategoria);

        txtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCategoriaKeyPressed(evt);
            }
        });

        jLabel9.setText("Categoría:");

        dateFechaPagado.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateFechaPagado.setNothingAllowed(false);
    dateFechaPagado.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    dateFechaPagado.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
        public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
            dateFechaPagadoOnSelectionChange(evt);
        }
    });
    dateFechaPagado.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dateFechaPagadoOnCommit(evt);
        }
    });

    jLabel5.setText("Fecha Pagado:");

    btnBuscar.setText("Buscar por id");
    btnBuscar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnBuscarActionPerformed(evt);
        }
    });

    spinnerId.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            spinnerIdStateChanged(evt);
        }
    });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(spinnerId, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(btnBuscar)
            .addComponent(spinnerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    chkPago.setText("Pago");
    chkPago.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            chkPagoActionPerformed(evt);
        }
    });

    jLabel2.setText("Multa (%)");

    spRecargo.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            spRecargoStateChanged(evt);
        }
    });

    btnHoy.setText("HOY");
    btnHoy.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnHoyActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(26, 26, 26))
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dateFechaPagado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtCategoria))
                .addComponent(jScrollPane3)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chkPago)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(spRecargo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnHoy)
                    .addGap(0, 8, Short.MAX_VALUE)))
            .addContainerGap())
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(chkPago)
                .addComponent(spRecargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2)
                .addComponent(btnHoy))
            .addGap(12, 12, 12)
            .addComponent(dateFechaPagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtCategoria)
                .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane3)
            .addGap(1, 1, 1)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGap(0, 0, 0)
            .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void chkPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPagoActionPerformed
        dateFechaPagado.setEnabled(chkPago.isSelected());
        btnHoy.setEnabled(chkPago.isSelected());
    }//GEN-LAST:event_chkPagoActionPerformed

    private void btnAltaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaIngresoActionPerformed
        try{
            nombre = txtNombre.getText();
            descripcion = txtDescripcion.getText();
            monto = Double.parseDouble(txtMonto.getText());
            if (monto < 0){
                throw new Error("El monto debe ser mayor o igual a 0");
            }                        
            es_ingreso = chkIngreso.isSelected();
            if (chkPago.isSelected()){
                fecha_pagado = new Date(dateFechaPagado.getSelectedDate().getTime().getTime());
                pago = true;
            }else {
                fecha_pagado = null;
                pago = false;
            }
            fecha_de_vencimiento =  new Date(dateVencimiento.getSelectedDate().getTime().getTime());
            recargo = Integer.parseInt(spRecargo.getValue().toString());
            if (cargarListaCategorias()){
                categoria = ((Categoria)lstm.getElementAt(lstCategoria.getSelectedIndex()));
                movimiento_mensual = new MovimientoMensual(nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado, pago, fecha_de_vencimiento, recargo);
                CMovimientoMensual.insertarMovimientoMensual(movimiento_mensual);
                spinnerId.setValue(movimiento_mensual.getId());
                limpiar();                
                txtMontoTotal.setText(movimiento_mensual.getMonto() + "");                
                lblMensaje.setText("El movimiento se ha ingresado con éxito con el id: "+movimiento_mensual.getId());   
                btnBaja1.setEnabled(false);
                btnEditar1.setEnabled(false);                
            }
        } catch (Error e) {
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"monto\" debe ingresar solo números positivos o el 0");
        } catch (Exception ex) {
            lblMensaje.setText("Ha ocurrido un error " + ex.getMessage());
        }      
    }//GEN-LAST:event_btnAltaIngresoActionPerformed

    private void txtCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriaKeyPressed
        cargarListaCategorias();
//        lblMensaje.setText("");
    }//GEN-LAST:event_txtCategoriaKeyPressed

    private void btnBaja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaja1ActionPerformed
        try{
            int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar?", ":o", 0);
            if (opcion == 0){            
                CMovimientoMensual.borrarMovimientoMensual(Integer.parseInt(spinnerId.getValue().toString()));
                limpiar();
                lblMensaje.setText("El movimiento se elimino correctamente");
                btnEditar1.setEnabled(false);
                btnBaja1.setEnabled(false);               
            }else{
                lblMensaje.setText("");
            }
        }catch(Error e){
            lblMensaje.setText(e.getMessage());
        }catch(Exception e){
            lblMensaje.setText("Ocurrió un error desconocido");
        }
    }//GEN-LAST:event_btnBaja1ActionPerformed

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        try {
            nombre = txtNombre.getText();
            descripcion = txtDescripcion.getText();
            monto = Double.parseDouble(txtMonto.getText());
            if (monto < 0){
                throw new Error("El monto debe ser mayor o igual a 0");
            }                        
            es_ingreso = chkIngreso.isSelected();
            if (chkPago.isSelected()){
                fecha_pagado = new Date(dateFechaPagado.getSelectedDate().getTime().getTime());
                pago = true;
            }else {
                fecha_pagado = null;
                pago = false;
            }
            fecha_de_vencimiento =  new Date(dateVencimiento.getSelectedDate().getTime().getTime());
            recargo = Integer.parseInt(spRecargo.getValue().toString());
            pago = chkPago.isSelected();
            if (cargarListaCategorias()){
                categoria = ((Categoria)lstm.getElementAt(lstCategoria.getSelectedIndex()));
                movimiento_mensual = new MovimientoMensual(nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado, pago, fecha_de_vencimiento, recargo);
                movimiento_mensual.setId(Integer.parseInt(spinnerId.getValue().toString()));
                CMovimientoMensual.modificarMovimientoMensual(movimiento_mensual);            
                txtMontoTotal.setText(movimiento_mensual.getMonto()+ "");
                lblMensaje.setText("El movimiento se ha modificado con éxito");
            }
        }catch(Error e){
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"monto\" debe ingresar solo números positivos o el 0");
        }catch(Exception e){
            lblMensaje.setText("Ocurrió un error desconocido");
        }
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
//        lblMensaje.setText("");
            buscar();
            btnEditar1.setEnabled(encontrado);
            btnBaja1.setEnabled(encontrado);        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void dateFechaPagadoOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateFechaPagadoOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFechaPagadoOnCommit

    private void dateFechaPagadoOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateFechaPagadoOnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFechaPagadoOnSelectionChange

    private void spinnerIdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerIdStateChanged
            if (Integer.parseInt(spinnerId.getValue().toString())<0){
                spinnerId.setValue(0);
            }
            buscar();
        
            btnEditar1.setEnabled(encontrado);
            btnBaja1.setEnabled(encontrado);
            
    }//GEN-LAST:event_spinnerIdStateChanged

    private void spRecargoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spRecargoStateChanged
        try {
                if (Integer.parseInt(spRecargo.getValue().toString()) < 0){
                    spRecargo.setValue(0);
                }            
        } catch (Exception e) {
            lblMensaje.setText("El recargo debe ser un número entero.");
        }
    }//GEN-LAST:event_spRecargoStateChanged

    private void txtMontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoFocusGained
        txtMonto.selectAll();
    }//GEN-LAST:event_txtMontoFocusGained

    private void txtMontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMontoMouseClicked
        txtMonto.selectAll();
    }//GEN-LAST:event_txtMontoMouseClicked

    private void btnHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoyActionPerformed
        dateFechaPagado.setSelectedDate(Calendar.getInstance());
    }//GEN-LAST:event_btnHoyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAltaIngreso;
    private javax.swing.JToggleButton btnBaja1;
    private javax.swing.JToggleButton btnBuscar;
    private javax.swing.JToggleButton btnEditar1;
    private javax.swing.JButton btnHoy;
    private javax.swing.JCheckBox chkIngreso;
    private javax.swing.JCheckBox chkPago;
    private datechooser.beans.DateChooserCombo dateFechaPagado;
    private datechooser.beans.DateChooserCombo dateVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JList<String> lstCategoria;
    private javax.swing.JSpinner spRecargo;
    private javax.swing.JSpinner spinnerId;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtMontoTotal;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
