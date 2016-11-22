/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.dto;

import java.sql.Date;

/**
 *
 * @author user6
 */
public class Movimiento {
    protected int id;
    protected String nombre; //maximo 20 caracteres
    protected String descripcion; // maximo 150 caracteres
    protected double monto;//decimal
    protected Categoria categoria;
    protected boolean es_ingreso; // Describe si el pago es_ingreso o es_egreso
    protected Date fecha_pagado;//Fecha que se realizó el pago

    /**
     * Constructor por defecto, los valores por defecto son:
     *  <br>id = -1;
     *  <br>nombre = "";
     *  <br>descripcion = "";
     *  <br>monto = 0;
     *  <br>categoria = null;
     *  <br>es_ingreso = false;
  <br>fecha_pagado = null;        
     */
    public Movimiento() {
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.monto = 0;
        this.categoria = null;
        this.es_ingreso = false;

        this.fecha_pagado = null;        
    }

    /**
     *  Constructor completo
     * @param id autoincrementado de la BD
     * @param nombre maxmio 20 caracteres, nombre de el moviminto
     * @param descripcion maximo 150 caracteres, breve descripción
     * @param monto decimal
     * @param categoria 
     * @param activo indica si true=es_ingreso o false=es_egreso
     * @param fecha_pagado indica la fecha en que se realizó el pago, en caso que no se haya hecho se indica como "null"
     */
    public Movimiento(int id, String nombre, String descripcion, double monto, Categoria categoria, boolean activo, Date fecha_pagado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.categoria = categoria;
        this.es_ingreso = activo;
        this.fecha_pagado = fecha_pagado;
    }

    
    /**
     * Constructor por copia
     * @param m Movimiento a copiar
     */
    public Movimiento(Movimiento m) {
        this.id = m.id;
        this.nombre = m.nombre;
        this.descripcion = m.descripcion;
        this.monto = m.monto;
        this.categoria = m.categoria;
        this.es_ingreso = m.es_ingreso;
        this.fecha_pagado = m.fecha_pagado;
    }    
    
    /*    EQUAL    */

    /**
     * Metodo para comparar usando los id
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }else{
            return this.id == ((Movimiento)obj).id;
        }
    }
    
    /*   GETTERS   */
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Monto Final del movimiento, con recargo si aplica
     * @return 
     */
    public double getMonto() {
        return monto;
    }

    
    public Categoria getCategoria() {
        return categoria;
    }
    /**
     * Indica si está es_ingreso o anulado
 <br>true=es_ingreso <br>false=anulado
     * @return 
     */
    public boolean isEs_ingreso() {
        return es_ingreso;
    }

    public Date getFecha_pagado() {
        return fecha_pagado;
    }

    
    /*   SETTERS   */
    
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Settear Nombre
     * @param nombre maximo 20 caracteres
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Breve descripción
     * @param descripcion maximo 150 caracteres
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
//    public void setMonto(double monto) {
//        this.monto = monto;
//    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    /**
     * Indica si está es_ingreso o anulado
     * @param es_ingreso true=es_ingreso false=anulado
     */
    public void setEs_ingreso(boolean es_ingreso) {
        this.es_ingreso = es_ingreso;
    }

    /**
     * Fecha pagado, tiene sentido si isPago es true
     * @param fecha_pagado 
     */
    public void setFecha_pagado(Date fecha_pagado) {
        this.fecha_pagado = fecha_pagado;
    }

    
    /**
     * Retorna un tipo String descriptivo de la forma:
     * @return "Movimiento: " + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", monto=" + monto + ", categoria=" + categoria + ", es_ingreso=" + es_ingreso + ", fecha_de_vencimiento=" + fecha_de_vencimiento + ", fecha_pagado=" + fecha_pagado + '}';
     */
    @Override
    public String toString() {
        return "Movimiento: " + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", monto=" + monto + ", categoria=" + categoria + ", activo=" + es_ingreso +  ", fecha_pagado=" + fecha_pagado + '}';
    }
}
