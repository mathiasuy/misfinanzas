/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.controlador;

import java.util.ArrayList;
import mathiasbattistella.dao.DAOMovimientoRapido;
import mathiasbattistella.dto.MovimientoRapido;

/**
 *
 * @author user6
 */
public class CMovimientoRapido {
    
    /** MENSAJES A MOSTRAR **/

    private static final String OBJETO = " el movimiento";    
    private static final int MAX_NOMBRE = 50;
    private static final int MAX_DESCRIPCION = 200;    
    private static final String ERROR_GENERAL  = "Ha ocurrido un error";
    private static final String ERROR_YAEXISTE = "El elemento ya existe";
    private static final String ERROR_CAMPO_VACIO_NOMBRE  = "El nombre no debe estar vacio";
    private static final String ERROR_LIMITE_NOMBRE = "El nombre no puede contener más de "+MAX_NOMBRE+" caracteres";
    private static final String ERROR_LIMITE_DESCRIPCION = "La descripción debe ser de menos de "+MAX_DESCRIPCION+" caracteres";
    private static final String ERROR_LIMITE_FECHA = "La fecha ingresada para el pago aún no ha pasado";
    private static final String ERROR_ALTA = "No se ha podido ingresar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADO = "No se ha encontrado"+OBJETO;
    private static final String ERROR_NO_ELIMINADO = "No se ha podido eliminar"+OBJETO;
    private static final String ERROR_NO_MODIFICADO = "No se ha podido modificar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADOS = "No se han encontrado categorías, deberá agregar al menos una para poder ingresar Movimientos";
    private static final String ERROR_BUSQUEDA_NO_ENCONTRADOS = "No se han encontrado elementos que coincidan con los parámetros ingresados";
    
    /*** METODOS Y FUNCIONES ***/
    
    
    /**
     * Inserta movimiento en la bd
     * @param movimiento 
     */
    public static void insertarMovimientoRapido(MovimientoRapido movimiento){
        movimiento.setNombre(movimiento.getNombre().trim());

        if (movimiento.getId() >= 1){
            throw new Error(ERROR_YAEXISTE);
        }
        if (movimiento.getNombre().isEmpty()){
            throw new Error(ERROR_CAMPO_VACIO_NOMBRE);
        }        
        if (movimiento.getNombre().length() >MAX_NOMBRE){
            throw new Error(ERROR_LIMITE_NOMBRE);
        }
        if (movimiento.getDescripcion().length() > MAX_DESCRIPCION){
            throw new Error(ERROR_LIMITE_DESCRIPCION);
        }
        java.util.Date ahora = new java.util.Date();
        if (movimiento.getFecha_pagado().compareTo(ahora)>0){
            throw new Error(ERROR_LIMITE_FECHA);
        }
        DAOMovimientoRapido mr = new DAOMovimientoRapido();
        
        if (!mr.crear(movimiento)){
            throw new Error(ERROR_ALTA);
        }
    }
    
    /**
     * Borra el movimiento con identificador id de la bd
     * @param id 
     */
    public static void borrarMovimientoRapido(int id){
        DAOMovimientoRapido  mr = new DAOMovimientoRapido();
        MovimientoRapido movimiento = mr.read(id);
        if (movimiento==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        if (!mr.delete(id)){
            throw new Error(ERROR_NO_ELIMINADO);
        }
    }
    
    /**
     * Modifica el movimiento de la bd
     * @param movimiento 
     */
    public static void modificarMovimientoRapido(MovimientoRapido movimiento){
        movimiento.setNombre(movimiento.getNombre().trim());
        if (movimiento.getNombre().isEmpty()){
            throw new Error(ERROR_CAMPO_VACIO_NOMBRE);
        }                
        if (movimiento.getNombre().length() >MAX_NOMBRE){
            throw new Error(ERROR_LIMITE_NOMBRE);
        }
        if (movimiento.getDescripcion().length() > MAX_DESCRIPCION){
            throw new Error(ERROR_LIMITE_DESCRIPCION);
        }
        java.util.Date ahora = new java.util.Date();
        if (movimiento.getFecha_pagado().compareTo(ahora)>0){
            throw new Error(ERROR_LIMITE_FECHA);
        }
        DAOMovimientoRapido mr = new DAOMovimientoRapido();        
        if (!mr.update(movimiento)){
            throw new Error(ERROR_NO_MODIFICADO);
        }
    }
    
    /**
     * Devuelve el movimiento de la bd con el identificador id
     * @param id
     * @return 
     */
    public static MovimientoRapido obtenerMovimientoRapido(int id){
        DAOMovimientoRapido mr = new DAOMovimientoRapido();
        MovimientoRapido m = mr.read(id);
        if (m==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        return m;
    }
    
    /**
     * Lista los movimientos rapidos de la bd
     * @return 
     */
    public static ArrayList<MovimientoRapido> listarMovimientosRapidos(){
        DAOMovimientoRapido mr = new DAOMovimientoRapido();
        ArrayList<MovimientoRapido> lista = mr.readAll();
        if (lista.isEmpty()){
            throw new Error(ERROR_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * devuelve la lista con los resultados de la busqueda filtro aplicada sobre campo
     * @param campo nombre de columna de la bd
     * @param filtro filtro a aplicar sobre campo
     * @return 
     */
    public static ArrayList<MovimientoRapido> buscar(String campo, String filtro){
        DAOMovimientoRapido mr = new DAOMovimientoRapido();
        ArrayList<MovimientoRapido> lista = mr.search(campo, filtro);
        if (lista.isEmpty()){
            //throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }    
    
    /**
     *  Devuelve la lista co los resultados de busqueda desde hasta de la columna columna_entre de tipo Date con 
     * el tipo señalado
     * @param columna_entre
     * @param desde
     * @param hasta
     * @param tipo 0 Todos 1 Ingersos 2 Ergesos
     * @return 
     */
    public static ArrayList<MovimientoRapido> betweenFechas(String columna_entre, java.sql.Date desde, java.sql.Date hasta, int tipo){
        DAOMovimientoRapido mrdao = new DAOMovimientoRapido();
        ArrayList<MovimientoRapido> lista = mrdao.betweenByDate(columna_entre, desde, hasta,tipo);
        if (lista.isEmpty()){
            //throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * Devuelve la lista co los resultados de busqueda desde hasta de la columna columna_entre de tipo Date con 
     * el tipo señalado y opcion_columna aplicado sobre columna_filtro
     * @param columna_entre nombre de la columna a aplicar between desde hasta de tipos Date
     * @param desde
     * @param hasta
     * @param tipo 0 Todos 1 Ingresos 2 Egresos
     * @param columna_filtro nombre de columna sobre la que se aplica filtro
     * @param opcion_columna filtro
     * @return 
     */
    public static ArrayList<MovimientoRapido> betweenFechasYCategoria(String columna_entre, java.sql.Date desde, java.sql.Date hasta, int tipo, String columna_filtro, String opcion_columna){
        DAOMovimientoRapido mrdao = new DAOMovimientoRapido();
        ArrayList<MovimientoRapido> lista = mrdao.betweenByDateAndFilter(columna_entre, desde, hasta,tipo,columna_filtro,opcion_columna);
        if (lista.isEmpty()){
            //throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }    
}
