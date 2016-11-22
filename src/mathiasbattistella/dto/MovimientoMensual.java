/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.dto;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author user6
 */
public class MovimientoMensual extends Movimiento{
    protected Date fecha_de_vencimiento;
    protected boolean pago;//
    protected int recargo;
    protected boolean vencido;
    
    /**
     * Constructor por defecto
     */
    public MovimientoMensual() {
        super(-1, "", "", 0, null, false, null);
        this.pago = false;
        this.fecha_de_vencimiento = null;
        this.recargo = 0;
    }

    /**
     * Constructor completo, el id se asignara desde la bd tras Grabar()
     * @param pago si el movimiento está pago o no
     * @param recargo % de recargo a monto en caso de fecha_pagado>fecha_de_vencimiento o fecha_pagado es null si fecha_actual>fecha_vencimiento
     * @param nombre nombre maximo 20 caracteres
     * @param descripcion breve descripcion maximo 150 caracteres
     * @param monto 
     * @param categoria
     * @param es_ingreso true=es_ingreso false=es_egreso
     * @param fecha_de_vencimiento fecha de vencimiento
     * @param fecha_pagado fecha en que se realizó el pago, si pago es true, de lo contrario, deberá dejarse en null
     */
    public MovimientoMensual(String nombre, String descripcion, double monto, Categoria categoria, 
            boolean es_ingreso, Date fecha_pagado, boolean pago, Date fecha_de_vencimiento,  int recargo) {
        super(-1, nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado);
        this.pago = pago;
        this.fecha_de_vencimiento = fecha_de_vencimiento;
        this.recargo = recargo;
    }
    /**
     * Constructor por copia
     * @param m a copiar
     */
     public MovimientoMensual(MovimientoMensual m) {
        super(m.getId(), m.getNombre(), m.getDescripcion(),m.getMonto(), m.getCategoria(), m.es_ingreso, m.fecha_pagado);
        this.pago = m.pago;
        this.fecha_de_vencimiento = m.fecha_de_vencimiento;
        this.recargo = m.recargo;
    }   
     
     /*   GETTERS   */
     
     /**
      * Retorna si el pago se debita autoaticamente
      * @return true=debito automatico false= pago manual
      */
    public boolean isPago() {
        return pago;
    }
    
    public int getRecargo() {
        return recargo;
    }

    public Date getFecha_de_vencimiento() {
        return fecha_de_vencimiento;
    }

    
    
    /*  SETTERS  */
    
    
    /**
     * true si está pago el movimiento
     * false si no está pago
     * @param pago 
     */
    public void isPago(boolean pago) {
        this.pago = pago;
    }


    /**
     * Recargo a aplicar en caso !is_enFecha()
     * @param recargo 
     */
    public void setRecargo(int recargo) {
        this.recargo = recargo;
    }

    public void setFecha_de_vencimiento(Date fecha_de_vencimiento) {
        this.fecha_de_vencimiento = fecha_de_vencimiento;
    }
    
//date1.compareTo(date2); //date1 < date2, devuelve un valor menor que 0
//date2.compareTo(date1); //date2 > date1, devuelve un valor mayor que 0
//date1.compareTo(date3); //date1 = date3,    
    /**
     * true si el movimiento esta en fecha o false si se venció y no se pagó
     * @return 
     */
    public boolean isVencido(){
        java.util.Date fecha_actual = Calendar.getInstance().getTime();
        java.util.Date calendario = Calendar.getInstance().getTime();
        java.util.Date fecha_vencimiento = new Date(this.fecha_de_vencimiento.getTime());
        //pude solucionar el problema seteando la fecha actual como la de vencimiento y 
        //asignandole dia mes y año actual a fecha_actual 
        //seteando horas, segundos y minutos como 0 no funcionaba
        fecha_actual.setTime(fecha_vencimiento.getTime());
        fecha_actual.setYear(calendario.getYear());
        fecha_actual.setMonth(calendario.getMonth());
        fecha_actual.setDate(calendario.getDate());
    
//        if(this.nombre.equals("asd")){
//            System.out.println(fecha_vencimiento.toLocaleString());
//            System.out.println(fecha_actual.toLocaleString());
//            System.out.println("compare: "+fecha_vencimiento.compareTo(fecha_actual));
//       }
        return  !(
                
                (!this.isPago() && !(fecha_vencimiento.compareTo(fecha_actual)<0))
                
                || (this.isPago() && !(fecha_vencimiento.compareTo(this.fecha_pagado)<0)));
    }
    
    /**
     * Devuelve el monto total, con el recargo si corresponde
     * @return 
     */
    @Override
    public double getMonto() {
        double _monto;
        if (this.isVencido()){
             _monto = super.getMonto() + super.getMonto()*this.getRecargo()/100;
        }else{
            _monto = this.monto;
        }
        return _monto; //To change body of generated methods, choose Tools | Templates.
    }    

    /**
     * Devuelve el monto sin recargos
     * @return 
     */
    public double getMontoSinRercargos(){
        return this.monto;
    }
    
    
    
    
    /**
     * Retorna una cadena de tipo String con el valor siguiente
     * @return super.toString() + " " + "pago=" + pago + ", recargo=" + recargo;
     */
    @Override
    public String toString() {
        return super.toString() + " " +  (pago?" Pago el " + fecha_pagado.toString():" Impago ") + ", recargo=" + recargo + "Fecha de vencimiento: " + fecha_de_vencimiento.toString() ;
    }
    
    
}
