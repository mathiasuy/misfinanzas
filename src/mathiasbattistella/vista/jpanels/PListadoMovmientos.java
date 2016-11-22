/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.vista.jpanels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
import mathiasbattistella.controlador.CMovimientoMensual;
import mathiasbattistella.controlador.CMovimientoRapido;
import mathiasbattistella.dto.Categoria;
import mathiasbattistella.dto.MovimientoMensual;
import mathiasbattistella.dto.MovimientoRapido;
/**
 *
 * @author user6
 */
public class PListadoMovmientos extends javax.swing.JPanel {

    private ArrayList<MovimientoRapido> lista_movimientoRapido = null;
    private ArrayList<MovimientoMensual> lista_movimientoMensual = null;
    private ArrayList<Categoria> lista_categorias;
    private Calendar calendarDesde;
    private Calendar calendarHasta;

    /**
     * Actualiza las tablas
     */
    private void cargarDatos(){
        try{
            //cargo los datos para realizar la busqueda
            int tipo = ddlTipos.getSelectedIndex();//0 todos 1 ingresos 2 egresos
                        java.sql.Date first
                                = new java.sql.Date(dateDesde.getSelectedDate().getTime().getTime());
                java.sql.Date second = new java.sql.Date(dateHasta.getSelectedDate().getTime().getTime());
                String campo = "fecha_pagado";
//                if(ddlFiltro.getSelectedIndex()==0){
//                    campo = "fecha_pagado";
//                }else{
//                    campo = "fecha_de_vencimiento";
//                }
                //relleno las listas con los resultados de busqueda
                if (ddlCategorias.getSelectedItem().toString().equals("Todas")){
                    lista_movimientoMensual = CMovimientoMensual.betweenFechas(campo, first, second,tipo);
                    lista_movimientoRapido = CMovimientoRapido.betweenFechas("fecha_pagado", first, second,tipo);
                }else{
                    lista_movimientoMensual = CMovimientoMensual.betweenFechasYCategoria(campo, first, second,tipo,"categoria",ddlCategorias.getSelectedItem().toString());
                    lista_movimientoRapido = CMovimientoRapido.betweenFechasYCategoria("fecha_pagado", first, second,tipo,"categoria",ddlCategorias.getSelectedItem().toString());                    
                }
                
                //Establezco los nombres de columnas
                DefaultTableModel tbm1 = new DefaultTableModel(new String[]
                    {"Nombre", "Descripcion","Monto","Categoria","Tipo","Fecha Pagado","Pago con multa", "Vence","Multa"}, 0);
                DefaultTableModel tbm2 = new DefaultTableModel(new String[]
                    {"Nombre", "Descripcion","Monto","Categoria","Tipo","Fecha Pagado"}, 0);
                DefaultTableModel tbm3 = new DefaultTableModel(new String[]
                    {"Nombre", "Descripcion","Monto","Categoria","Tipo","Fecha Pagado","Vencido", "Vence","Multa"}, 0);
                
                //Establece la cantiad de filas para cada columna
                tbm1.setRowCount(lista_movimientoMensual.size()+lista_movimientoRapido.size());
                tbm2.setRowCount(lista_movimientoRapido.size());
                tbm3.setRowCount(lista_movimientoMensual.size());
                
                //Aplica los modelos sobre las tablas
                tblMovimientos.setModel(tbm1);
                tblMovimientosRapidos.setModel(tbm2);
                tblMovimientosMensuales.setModel(tbm3);
                
                //Rellena las tablas con las filas
                int fila = 0;
                for (MovimientoMensual m : lista_movimientoMensual){
                    tblMovimientosMensuales.setValueAt(m.getNombre(), fila, 0);
                    tblMovimientosMensuales.setValueAt(m.getDescripcion(), fila, 1);
                    tblMovimientosMensuales.setValueAt(m.getMonto(), fila, 2);
                    tblMovimientosMensuales.setValueAt(m.getCategoria().getNombre(), fila, 3);
                    tblMovimientosMensuales.setValueAt(m.isEs_ingreso()?"Ingreso":"Egreso", fila, 4);
                    tblMovimientosMensuales.setValueAt(m.getFecha_pagado().toString(), fila, 5);
                    tblMovimientosMensuales.setValueAt(m.isVencido()?"Si":"No", fila, 6);
                    tblMovimientosMensuales.setValueAt(m.getFecha_de_vencimiento().toString(), fila, 7);
                    tblMovimientosMensuales.setValueAt(m.getRecargo(), fila, 8);

                    tblMovimientos.setValueAt(m.getNombre(), fila, 0);
                    tblMovimientos.setValueAt(m.getDescripcion(), fila, 1);
                    tblMovimientos.setValueAt(m.getMonto(), fila, 2);
                    tblMovimientos.setValueAt(m.getCategoria().getNombre(), fila, 3);
                    tblMovimientos.setValueAt(m.isEs_ingreso()?"Ingreso":"Egreso", fila, 4);
                    tblMovimientos.setValueAt(m.getFecha_pagado().toString(), fila, 5);
                    tblMovimientos.setValueAt(m.isVencido()?"Si":"No", fila, 6);
                    tblMovimientos.setValueAt(m.getFecha_de_vencimiento().toString(), fila, 7);
                    tblMovimientos.setValueAt(m.getRecargo(), fila, 8);
                    fila++;
                }
                int fila2 = 0;
                for (MovimientoRapido m : lista_movimientoRapido){
                    tblMovimientosRapidos.setValueAt(m.getNombre(), fila2, 0);
                    tblMovimientosRapidos.setValueAt(m.getDescripcion(), fila2, 1);
                    tblMovimientosRapidos.setValueAt(m.getMonto(), fila2, 2);
                    tblMovimientosRapidos.setValueAt(m.getCategoria().getNombre(), fila2, 3);
                    tblMovimientosRapidos.setValueAt(m.isEs_ingreso()?"Ingreso":"Egreso", fila2, 4);
                    tblMovimientosRapidos.setValueAt(m.getFecha_pagado().toString(), fila2, 5);
                    tblMovimientos.setValueAt(m.getNombre(), fila, 0);
                    tblMovimientos.setValueAt(m.getDescripcion(), fila, 1);
                    tblMovimientos.setValueAt(m.getMonto(), fila, 2);
                    tblMovimientos.setValueAt(m.getCategoria().getNombre(), fila, 3);
                    tblMovimientos.setValueAt(m.isEs_ingreso()?"Ingreso":"Egreso", fila, 4);
                    tblMovimientos.setValueAt(m.getFecha_pagado().toString(), fila, 5);
                    fila++;
                    fila2++;
                }
                lblMensaje.setText("La búsqueda arrojó " +fila2+" movimiento(s) rapido(s) y "+(fila-fila2)+" movimiento(s) mensual(es).");
        } catch (Error e) {
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            lblMensaje.setText("Ha ocurrido un error" + e.getMessage());
        }
    }
    
    /**
     * Creates new form ListadosMovmientos
     */
    public PListadoMovmientos() {
        initComponents();
        cargarDatos();
        //Establezco el formato de fecha para los JDataChoose
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy") ;
        //Obtengo la fecha actual e instancio las variables calendario
        calendarDesde = Calendar.getInstance();
        calendarHasta = Calendar.getInstance();
        //Hago que el rango de fechas a mostrar por defecto sea de un mes atrás
        calendarDesde.add(Calendar.MONTH, -1);
        dateDesde.setSelectedDate(calendarDesde);
        dateDesde.setDateFormat(dateFormat);
        dateHasta.setDateFormat(dateFormat);
        tblMovimientos.setDefaultEditor(Object.class, null);
        tblMovimientosMensuales.setDefaultEditor(Object.class, null);
        tblMovimientosRapidos.setDefaultEditor(Object.class, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMovimientos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMovimientosRapidos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimientosMensuales = new javax.swing.JTable();
        dateDesde = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        dateHasta = new datechooser.beans.DateChooserCombo();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        ddlTipos = new javax.swing.JComboBox<>();
        ddlCategorias = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        tblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblMovimientos);

        jTabbedPane1.addTab("Movimientos", jScrollPane4);

        tblMovimientosRapidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblMovimientosRapidos);

        jTabbedPane1.addTab("Movimientos Rápidos", jScrollPane3);

        tblMovimientosMensuales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblMovimientosMensuales);

        jTabbedPane1.addTab("Movimientos Mensuales", jScrollPane1);

        dateDesde.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
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
    dateDesde.setNothingAllowed(false);
    dateDesde.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    dateDesde.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
        public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
            dateDesdeOnSelectionChange(evt);
        }
    });
    dateDesde.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dateDesdeOnCommit(evt);
        }
    });

    jLabel2.setText("Desde:");

    jLabel1.setText("Hasta:");

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel3.setText("Listados de Movimientos por rangos de Fechas");

    jButton1.setText("Actualizar");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    dateHasta.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
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
dateHasta.setNothingAllowed(false);
dateHasta.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
dateHasta.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
    public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
        dateHastaOnSelectionChange(evt);
    }
    });
    dateHasta.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dateHastaOnCommit(evt);
        }
    });

    jButton2.setText("<");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    jButton3.setText(">");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    jButton4.setText("<");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });

    jButton5.setText(">");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    ddlTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Ingresos", "Egresos" }));
    ddlTipos.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            ddlTiposItemStateChanged(evt);
        }
    });

    ddlCategorias.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            ddlCategoriasItemStateChanged(evt);
        }
    });

    jLabel4.setText("Por Categoria:");

    jLabel5.setText("Por Tipo:");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jTabbedPane1)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(dateDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(2, 2, 2)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(40, 40, 40)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ddlCategorias, 0, 120, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(ddlTipos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel5)))
                .addComponent(jLabel3)
                .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(dateDesde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(ddlTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ddlCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
            .addContainerGap())
    );

    lista_categorias = mathiasbattistella.controlador.CCategoria.listarCategorias();
    ddlCategorias.addItem("Todas");
    for (Categoria c : lista_categorias){
        ddlCategorias.addItem(c.getNombre());
    }
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cargarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dateDesdeOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateDesdeOnSelectionChange
                cargarDatos();
    }//GEN-LAST:event_dateDesdeOnSelectionChange

    private void dateDesdeOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateDesdeOnCommit
                cargarDatos();
    }//GEN-LAST:event_dateDesdeOnCommit

    private void dateHastaOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateHastaOnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dateHastaOnSelectionChange

    private void dateHastaOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateHastaOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_dateHastaOnCommit

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        calendarDesde = dateDesde.getSelectedDate();
        calendarDesde.add(Calendar.MONTH, -1);
        dateDesde.setSelectedDate(calendarDesde);
        cargarDatos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        calendarDesde = dateDesde.getSelectedDate();
        calendarDesde.add(Calendar.MONTH, 1);
        dateDesde.setSelectedDate(calendarDesde);
        cargarDatos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        calendarHasta = dateHasta.getSelectedDate();
        calendarHasta.add(Calendar.MONTH, -1);
        dateHasta.setSelectedDate(calendarHasta);
        cargarDatos();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        calendarHasta = dateHasta.getSelectedDate();
        calendarHasta.add(Calendar.MONTH, 1);
        dateHasta.setSelectedDate(calendarHasta);
        cargarDatos();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void ddlTiposItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ddlTiposItemStateChanged
        cargarDatos();
    }//GEN-LAST:event_ddlTiposItemStateChanged

    private void ddlCategoriasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ddlCategoriasItemStateChanged
        cargarDatos();
    }//GEN-LAST:event_ddlCategoriasItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateDesde;
    private datechooser.beans.DateChooserCombo dateHasta;
    private javax.swing.JComboBox<String> ddlCategorias;
    private javax.swing.JComboBox<String> ddlTipos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JTable tblMovimientosMensuales;
    private javax.swing.JTable tblMovimientosRapidos;
    // End of variables declaration//GEN-END:variables
}
