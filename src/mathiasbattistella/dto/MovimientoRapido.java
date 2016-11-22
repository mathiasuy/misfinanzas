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
public class MovimientoRapido extends Movimiento{

    /**
     * Constructor por defecto
     */
    public MovimientoRapido() {
        super(-1, "", "", 0, null, false, null);
    }

    /**
     * Constructor completo
     * @param nombre maximo 20 caracteres, nombre
     * @param descripcion maximo 150 caracteres, breve descripcion
     * @param monto monto
     * @param categoria categoria 
     * @param es_ingreso true=es_ingreso false=es_egreso
     * @param fecha_pagado fecha en que se realizó el pago
     */
    public MovimientoRapido(String nombre, String descripcion, double monto, Categoria categoria, boolean es_ingreso, Date fecha_pagado) {
        super(-1, nombre, descripcion, monto, categoria, es_ingreso, fecha_pagado);
    }
    /**
     * Constructor por copia
     * @param m movimiento a copiar
     */
    public MovimientoRapido(MovimientoRapido m) {
        super(m.id, m.nombre, m.descripcion, m.monto, m.categoria, m.es_ingreso, m.fecha_pagado);
    }

    /**
     * Setea el monto final
     * @param monto 
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

}
