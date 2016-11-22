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
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import mathiasbattistella.controlador.*;
import mathiasbattistella.dto.*;

/**
 *
 * @author user6
 */
public final class PMovimientoRapido extends javax.swing.JPanel {
    private final String FORMAT_DATE = "dd/MM/yyyy";
    private final String ERROR_CATEGORIA_NO_MARCADA = "No se han encontrado categorias seleccionadas, necesita seleccionar una para ingresar un movimiento";
    private MovimientoRapido movimiento_rapido;
    private ArrayList<Categoria> lista_categorias_por_nombre = null;
    private ArrayList<Categoria> lista_categorias_por_descripcion = null;
    private String nombre;
    private String descripcion;
    private double monto;
    private Categoria categoria;
    private boolean es_ingreso;
    private Date fecha_pagado;
    private boolean encontrado;
    private DefaultListModel lstm;
    
    /**
     * para limpiar
     */
    private void limpiar(){
        txtNombre.requestFocus();
        txtNombre.setText("");
        txtCategoria.setText("");
        txtDescripcion.setText("");
        txtMonto.setText("0");
        lblMensaje.setText("");
        dateFechaPagado.setSelectedDate(Calendar.getInstance());
        cargarListaCategorias();
    }


    /**
     * para cargar la lista con las categorias
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
            }
        }catch(Error e){
            lblMensaje.setText(e.getMessage());
        }catch(Exception e){
            lblMensaje.setText("Ocurrió un error desconocido");
        }
        return true;
    }    
    
    /**
     * procedimiento de búsqueda
     */
    private void buscar(){
        try {
            encontrado = false;
            int id = Integer.parseInt(spId.getValue().toString());
            movimiento_rapido = CMovimientoRapido.obtenerMovimientoRapido(id);
            txtNombre.setText(movimiento_rapido.getNombre());
            txtDescripcion.setText(movimiento_rapido.getDescripcion());
            txtMonto.setText(movimiento_rapido.getMonto() + "");
            txtCategoria.setText(movimiento_rapido.getCategoria().getNombre());
            cargarListaCategorias();
            chkIngreso.setSelected(movimiento_rapido.isEs_ingreso());
            Calendar calendar =new GregorianCalendar();
            calendar.setTime(movimiento_rapido.getFecha_pagado());
            dateFechaPagado.setSelectedDate(calendar);
            encontrado = true;
            lblMensaje.setText("Encontrado");
        }catch(Error e){
            limpiar();
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"busqueda\" debe ingresar solo números positivos o el 0");
        }catch(Exception e){
            limpiar();
            lblMensaje.setText("Ocurrió un error desconocido");
        }        
    }

    /**
     * Creates new form MovimientosRapidos
     */
    public PMovimientoRapido() {
        initComponents();
        btnBaja1.setEnabled(false);
        btnEditar1.setEnabled(false);
        cargarListaCategorias();
        //Establezco el formato de fecha para los JDataChoose
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE) ;
        dateFechaPagado.setDateFormat(dateFormat);
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
        jPanel5 = new javax.swing.JPanel();
        btnAltaIngreso = new javax.swing.JToggleButton();
        btnBaja1 = new javax.swing.JToggleButton();
        btnEditar1 = new javax.swing.JToggleButton();
        chkIngreso = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstCategoria = new javax.swing.JList<>();
        txtCategoria = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        dateFechaPagado = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JToggleButton();
        spId = new javax.swing.JSpinner();
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

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane4.setViewportView(txtDescripcion);

        jLabel10.setText("Descripción:");

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
                .addGap(26, 26, 26)
                .addComponent(btnAltaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBaja1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAltaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBaja1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        chkIngreso.setText("Es Ingreso");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtMonto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkIngreso)
                        .addGap(10, 10, 10))
                    .addComponent(txtNombre)))
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
                    .addComponent(chkIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    jLabel5.setText("Fecha Pagado:");

    btnBuscar.setText("Buscar por id");
    btnBuscar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnBuscarActionPerformed(evt);
        }
    });

    spId.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            spIdStateChanged(evt);
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
            .addComponent(spId, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(31, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(spId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnBuscar))
    );

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
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateFechaPagado, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnHoy)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jScrollPane3)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txtCategoria)))
            .addGap(0, 0, 0))
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(dateFechaPagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel5)
                .addComponent(btnHoy))
            .addGap(17, 17, 17)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtCategoria)
                .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(15, 15, 15)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
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
            .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAltaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaIngresoActionPerformed
        try {
            nombre = txtNombre.getText();
            descripcion = txtDescripcion.getText();
            monto = Double.parseDouble(txtMonto.getText());
            if (monto < 0){
                throw new Error("El monto debe ser mayor o igual a 0");
            }
            es_ingreso = chkIngreso.isSelected();
            fecha_pagado = new Date(dateFechaPagado.getSelectedDate().getTime().getTime());
            if (cargarListaCategorias()){
                categoria = ((Categoria)lstm.getElementAt(lstCategoria.getSelectedIndex()));
                movimiento_rapido = new MovimientoRapido(nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado);
                CMovimientoRapido.insertarMovimientoRapido(movimiento_rapido);
                spId.setValue(movimiento_rapido.getId());
                limpiar();
                lblMensaje.setText("El movimiento se ha ingresado con éxito con el id: "+movimiento_rapido.getId());      
                btnBaja1.setEnabled(false);
                btnEditar1.setEnabled(false);
            }
        }catch(Error e){
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"monto\" debe ingresar solo números positivos o el 0");
        }catch(Exception e){
            lblMensaje.setText("Ocurrió un error desconocido" + e.getMessage());
        }
    }//GEN-LAST:event_btnAltaIngresoActionPerformed

    private void btnBaja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaja1ActionPerformed
        try{
            int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar?", ":o", 0);
            if (opcion == 0){
                CMovimientoRapido.borrarMovimientoRapido(Integer.parseInt(spId.getValue().toString()));
                limpiar();
                lblMensaje.setText("El movimiento se elimino correctamente");
                btnBaja1.setEnabled(false);
                btnEditar1.setEnabled(false);                
            }else{
                lblMensaje.setText("");    
            }
                        
        }catch(Error e){
            limpiar();
            lblMensaje.setText(e.getMessage());
        }catch(Exception e){
            limpiar();
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
            fecha_pagado = new Date(dateFechaPagado.getSelectedDate().getTime().getTime());
            if (cargarListaCategorias()){
                categoria = ((Categoria)lstm.getElementAt(lstCategoria.getSelectedIndex()));
                movimiento_rapido = new MovimientoRapido(nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado);
                movimiento_rapido.setId(Integer.parseInt(spId.getValue().toString()));
                CMovimientoRapido.modificarMovimientoRapido(movimiento_rapido);
                lblMensaje.setText("El movimiento se ha modificado con éxito");
            }
        }catch(Error e){
            lblMensaje.setText(e.getMessage());
        }catch(NumberFormatException ex){
            lblMensaje.setText("En el campo \"monto\" debe ingresar solo números positivos o el 0");
        }catch(Exception ex){
            lblMensaje.setText("Ocurrió un error desconocido" + ex.getMessage());            
        }
        
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
        btnEditar1.setEnabled(encontrado);
        btnBaja1.setEnabled(encontrado);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriaKeyPressed
        lblMensaje.setText("");
        cargarListaCategorias();
    }//GEN-LAST:event_txtCategoriaKeyPressed

    private void spIdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spIdStateChanged
        if (Integer.parseInt(spId.getValue().toString())<0){
            spId.setValue(0);
        }
        buscar();
        btnEditar1.setEnabled(encontrado);
        btnBaja1.setEnabled(encontrado);
    }//GEN-LAST:event_spIdStateChanged

    private void txtMontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMontoFocusGained
        txtMonto.selectAll();
    }//GEN-LAST:event_txtMontoFocusGained

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
    private datechooser.beans.DateChooserCombo dateFechaPagado;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JList<String> lstCategoria;
    private javax.swing.JSpinner spId;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}