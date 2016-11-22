/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.vista.jpanels;

import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import mathiasbattistella.controlador.*;
import mathiasbattistella.dto.*;

/**
 *
 * @author user6
 */
public final class PBalances extends javax.swing.JPanel {

    private DefaultTableModel tbm;
    private ArrayList<Categoria> lista_categorias;
    private ArrayList<MovimientoMensual> lista_movimientoMensual;
    private ArrayList<MovimientoRapido> lista_movimientoRapido;
    private Calendar calDesde;
    private Calendar calHasta;

    /**
     * Crea una lista de Categorias a las cuales les asigna, en memoria, los ingresos y egresos totales
     * y crea otra "Total", al final, que tiene la suma de ingresos y egresos de todas las categorías
     * Se filtra por fecha tipo Date aplicando between desde hasta
     * La lista sirve para representar dinamicamente los datos de las categorias en la tabla
     * @param desde
     * @param hasta
     * @return 
     */
    private ArrayList<Categoria> obtenerListaCategoriasConMovimientosTotales(Date desde, Date hasta){
        try {
            int cant_movimientos_mensuales = 0;
            int cant_movimientos_rapidos = 0;
            lista_categorias = CCategoria.listarCategorias();
            double ingreso=0, egreso=0;
//            System.out.println("lista categorias: "+lista_categorias.size() + " "+lista_categorias.toString());
            for (Categoria c : lista_categorias){
//                System.out.println("cate="+c.getNombre());
                lista_movimientoMensual = CMovimientoMensual.betweenFechasYCategoria("fecha_pagado", desde, hasta, 0, "categoria", c.getNombre());
//                System.out.println("lista_movimientoMensual ");
                for (MovimientoMensual m : lista_movimientoMensual){
//                    System.out.println("-"+m.toString());
                    if (m.isEs_ingreso()){
//                        System.out.println(" ing: "+m.getMonto());
                        c.sumarIngreso(m.getMonto());
//                        System.out.println(" ing grabado: "+c.getIngresos_totales());                        
                        ingreso +=m.getMonto();
                    }else{
//                        System.out.println(" eg: "+m.getMonto());
                        c.sumarEgreso(m.getMonto());
//                        System.out.println(" eg grabado: "+c.getEgresos_totales());
                        egreso += m.getMonto();
                    }
                }
                
                lista_movimientoRapido = CMovimientoRapido.betweenFechasYCategoria("fecha_pagado", desde, hasta, 0, "categoria", c.getNombre());
//                System.out.println("lista_movimientoRapido: "+ lista_movimientoRapido.size());
                for (MovimientoRapido m : lista_movimientoRapido){
//                    System.out.println("-"+m.toString());
                    if (m.isEs_ingreso()){
//                        System.out.println(" ing grabado: "+c.getIngresos_totales());
                        c.sumarIngreso(m.getMonto());
                        ingreso +=m.getMonto();
                    }else{
//                        System.out.println(" eg grabado: "+c.getEgresos_totales());
                        c.sumarEgreso(m.getMonto());
                        egreso += m.getMonto();
                    }
                }
                cant_movimientos_mensuales += lista_movimientoMensual.size();
                cant_movimientos_rapidos += lista_movimientoRapido.size();
//for (Categoria b : lista_categorias)                {
//    System.out.println("c: "+b.aTexto());
//}
            }
                //Categoria con los totales
                Categoria total = new Categoria("Total", "Movimiento(s) mensuale(s): " + cant_movimientos_mensuales + ", Movimiento(s) Rapido(s): " + cant_movimientos_rapidos);
                total.setEgresos_totales(egreso);
                total.setIngresos_totales(ingreso);
                lista_categorias.add(total);
                
                lista_categorias.stream().forEach((b) -> {
//                    System.out.println("c: "+b.aTexto());
            });

        } catch (Error ex) {
//            System.out.println("ERROR " + this.getClass().getName() + ex.getMessage());
            lblMensaje.setText(ex.getMessage());
        }catch (Exception ex) {
//            System.out.println("ERROR " + this.getClass().getName()  + ex.getMessage() + "obtenerListaCategoriasConMovimientosTotales");
            lblMensaje.setText("Ocurrio un error inesperado " + ex.getMessage());
        }
        return lista_categorias;
    }    
    
    /**
     * Actualiza la tabla
     */
    public void cargarDatos(){
        try{
            //Obtengo los datos
            java.sql.Date desde = new java.sql.Date(dateDesde.getSelectedDate().getTime().getTime());
            java.sql.Date hasta = new java.sql.Date(dateHasta.getSelectedDate().getTime().getTime());
            lista_categorias = obtenerListaCategoriasConMovimientosTotales(desde, hasta);
//            System.out.println(lista_categorias==null?"es null":"no es null");
//            System.out.println(lista_categorias.isEmpty()?"es vacia":"no es vacia");
            //Creo el modelo por defecto
            tbm = new DefaultTableModel(new String[]{},0);
            lista_categorias.stream().forEach((c) -> {
                tbm.addColumn(c.getNombre());
            });
//            System.out.println("ds: "+ lista_categorias.size());
            //Establezco la cantidad de filas para la tabla, seran 3, 
            //una para ingresos, otra para egresos, y otra para total
            tbm.setNumRows(3);
            //Seteo el modelo a la tabla
            tabla.setModel(tbm);
            //Armo la tabla con los calculos
                for (int j = 0; j < lista_categorias.size(); j++){
                    double ingresos_categoria = lista_categorias.get(j).getIngresos_totales();
                    double egresos_categoria = lista_categorias.get(j).getEgresos_totales();
                    tabla.setValueAt(ingresos_categoria, 0, j);
                    tabla.setValueAt(egresos_categoria, 1, j);
                    tabla.setValueAt(ingresos_categoria-egresos_categoria, 2, j);
                }              
            //Establezco un renderizado por defecto, coloreará en rojo los negativos
            tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
                                    Color originalColor = null;
                                    @Override
                                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                                        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                                        if (originalColor == null) {
                                            originalColor = getForeground();
                                        }
                                        if (value == null) {
                                            renderer.setText("");
                                        } else {
                                            renderer.setText(value.toString());
                                        }

                                        if ((double)value<0.0) {
                                            renderer.setForeground(Color.RED);
                                        } else {
                                            renderer.setForeground(originalColor); // Retore original color
                                        }
                                         return renderer;
                                    }            
            
            });
            lblMensaje.setText("El balance incluye " + lista_categorias.get(lista_categorias.size()-1).getDescripcion());
        }catch(Error ex){
//            System.out.println("ERROR " + this.getClass().getName() + ex.getMessage() + "cargarDatos");
            lblMensaje.setText(ex.getMessage());
        }catch(Exception ex){
//            System.out.println("ERROR " + this.getClass().getName() + ex.getMessage() + "cargarDatos");
            lblMensaje.setText("Ocurrió un error desconocido");
        }
    }
    
    public PBalances() {
        this.calHasta = Calendar.getInstance();
        this.calDesde = Calendar.getInstance();
        initComponents();
        //Establezco el formato de fecha para los JDataChoose
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Hago que el rango de fechas a mostrar por defecto sea de un mes atrás
        calDesde.add(Calendar.MONTH, -1);
        dateDesde.setSelectedDate(calDesde);
        dateHasta.setSelectedDate(calHasta);
        dateDesde.setDateFormat(sdf);
        dateHasta.setDateFormat(sdf);
        cargarDatos();       
        tabla.setDefaultEditor(Object.class, null);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        dateDesde = new datechooser.beans.DateChooserCombo();
        dateHasta = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnHoy = new javax.swing.JButton();
        btnUltimoMes = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Balance");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblMensaje.setText("    ");

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

    jLabel2.setText("Hasta:");

    jLabel3.setText("Desde:");

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

    jButton4.setText(">");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });

    jButton5.setText("<");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    btnHoy.setText("HOY");
    btnHoy.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnHoyActionPerformed(evt);
        }
    });

    btnUltimoMes.setText("ÚLTIMO MES");
    btnUltimoMes.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnUltimoMesActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1)
            .addContainerGap())
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                        .addComponent(dateHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnUltimoMes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHoy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 291, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel1)
                    .addGap(12, 12, 12))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(dateDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(btnHoy)
                            .addComponent(jButton5)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(btnUltimoMes)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lblMensaje)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cargarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dateDesdeOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateDesdeOnSelectionChange
        cargarDatos();
    }//GEN-LAST:event_dateDesdeOnSelectionChange

    private void dateHastaOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateHastaOnSelectionChange
        cargarDatos();
    }//GEN-LAST:event_dateHastaOnSelectionChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        calHasta = dateHasta.getSelectedDate();
        calHasta.add(Calendar.MONTH, -1);
        dateHasta.setSelectedDate(calHasta);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        calHasta = dateHasta.getSelectedDate();
        calHasta.add(Calendar.MONTH, 1);
        dateHasta.setSelectedDate(calHasta);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        calDesde = dateDesde.getSelectedDate();
        calDesde.add(Calendar.MONTH, 1);
        dateDesde.setSelectedDate(calDesde);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        calDesde = dateDesde.getSelectedDate();
        calDesde.add(Calendar.MONTH, -1);
        dateDesde.setSelectedDate(calDesde);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoyActionPerformed
        calDesde = Calendar.getInstance();
        dateDesde.setSelectedDate(calDesde);
        calHasta = Calendar.getInstance();
        dateHasta.setSelectedDate(calHasta);
    }//GEN-LAST:event_btnHoyActionPerformed

    private void btnUltimoMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoMesActionPerformed
        calDesde = Calendar.getInstance();
        calDesde.add(Calendar.MONTH, -1);
        dateDesde.setSelectedDate(calDesde);
        calHasta = Calendar.getInstance();
        dateHasta.setSelectedDate(calHasta);
    }//GEN-LAST:event_btnUltimoMesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHoy;
    private javax.swing.JButton btnUltimoMes;
    private datechooser.beans.DateChooserCombo dateDesde;
    private datechooser.beans.DateChooserCombo dateHasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
