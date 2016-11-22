/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.dto;

import java.util.Objects;


/**
 *
 * @author user6
 */
public class Categoria {
    private String nombre;//nombre de la categoría, será lo que la identificará, maximo 20 caracteres
    private String descripcion;// una breve descripción
    private double ingresos_totales;
    private double egresos_totales;

    /**
     * Constructor por defecto
     * <br>Valores por defecto en ""
     */
    public Categoria() {
        this.nombre = "";
        this.descripcion = "";
        this.egresos_totales = 0;
        this.ingresos_totales = 0;
    }    
    /**
     * Constructor completo
     * @param nombre (Máx 20 caracteres) nombre de la categoría, será lo que la identificará
     * @param descripcion (Máx 200 carácteres) una breve descripción
     */
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.egresos_totales = 0;
        this.ingresos_totales = 0;
    }
    /**
     * Constructor por copia
     * @param c Categoría a copiar
     */
    public Categoria(Categoria c) {
        this.nombre = c.nombre;
        this.descripcion = c.descripcion;
        this.egresos_totales = c.egresos_totales;
        this.ingresos_totales = c.ingresos_totales;
    }    

    
    /*   GETTERS   */
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    
    /*   SETTERS   */
   /**
    * nombre de la categoría, será lo que la identificará (Máx 20 caracteres)
    * @param nombre 
    */ 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * una breve descripción (Máx 200 carácteres) 
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getIngresos_totales() {
        return ingresos_totales;
    }

    public double getEgresos_totales() {
        return egresos_totales;
    }

    public void setIngresos_totales(double ingresos_totales) {
        this.ingresos_totales = ingresos_totales;
    }

    public void setEgresos_totales(double egresos_totales) {
        this.egresos_totales = egresos_totales;
    }

        
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        return hash;
    }

    /**
     * Compara en base al nombre de la categoria, si son iguales entonces las categorias son las mismas
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        return ((Categoria)obj).getNombre() == ((Categoria)obj).getNombre();
    }

    /**
     * Suma al ingreso el monto indicado
     * @param monto 
     */
    public void sumarIngreso(double monto){
        this.ingresos_totales += monto;
    }
    
    /**
     * Suma al egreso el monto indiacdo 
     * @param monto
     */
    public void sumarEgreso(double monto){
        this.egresos_totales += monto;
    }
    /**
     * Retorna los datos completos de la categoria
     * @return nombre + ": " + descripcion + " egresos: " + egresos_totales + " ingresos: "+ ingresos_totales;
     */
    public String aTexto() {
        return nombre + ": " + descripcion + " egresos: " + egresos_totales + " ingresos: "+ ingresos_totales;
    }
    /**
     * Retorna un String con: "nombre: descripcion"
     * @return nombre
     */
    @Override
    public String toString() {
        return nombre;
    }
    
}
