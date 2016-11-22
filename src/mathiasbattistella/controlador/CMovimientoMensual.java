/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.controlador;

import java.util.ArrayList;
import java.util.Date;
import mathiasbattistella.dao.DAOMovimientoMensual;
import mathiasbattistella.dto.MovimientoMensual;

/**
 *
 * @author user6
 */
public class CMovimientoMensual {
    
    /** MENSAJES A MOSTRAR **/

    private static final String OBJETO = " el movimiento";
    private static final int MAX_NOMBRE = 50;
    private static final int MAX_DESCRIPCION = 200;    
    private static final String ERROR_GENERAL  = "Ha ocurrido un error";
    private static final String ERROR_CAMPO_VACIO_NOMBRE  = "El nombre no debe estar vacio";
    private static final String ERROR_YAEXISTE = "El elemento ya existe";
    private static final String ERROR_LIMITE_NOMBRE = "El nombre no puede contener más de "+MAX_NOMBRE+" caracteres";
    private static final String ERROR_LIMITE_DESCRIPCION = "La descripción debe ser de menos de "+MAX_DESCRIPCION+" caracteres";
    private static final String ERROR_LIMITE_FECHA = "La fecha ingresada para el pago aún no ha pasado";
    private static final String ERROR_ALTA = "No se ha podido ingresar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADO = "No se ha encontrado"+OBJETO;
    private static final String ERROR_NO_ELIMINADO = "No se ha podido eliminar"+OBJETO;
    private static final String ERROR_NO_MODIFICADO = "No se ha podido modificar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADOS = "No se han encontrado categorías, deberá agregar al menos una para poder ingresar Movimientos";
    private static final String ERROR_BUSQUEDA_NO_ENCONTRADOS = "No se han encontrado elementos que coincidan con los parámetros ingresados";
    
    /** METODOS Y FUNCIONES **/
    
    /**
     * Inserta el movimiento mensual en la bd
     * @param movimiento 
     */
    public static void insertarMovimientoMensual(MovimientoMensual movimiento){
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
        Date ahora = new Date();
        
        if (movimiento.isPago() && movimiento.getFecha_pagado().compareTo(ahora)>0){
            throw new Error(ERROR_LIMITE_FECHA);
        }
        DAOMovimientoMensual mm = new DAOMovimientoMensual();
        if (!mm.crear(movimiento)){
            throw new Error(ERROR_ALTA);
        }
    }
    
    /**
     * borra el movimeinto mensual de la bd
     * @param id 
     */
    public static void borrarMovimientoMensual(int id){
        DAOMovimientoMensual  mm = new DAOMovimientoMensual();
        MovimientoMensual movimiento = mm.read(id);
        if (movimiento==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        if (!mm.delete(id)){
            throw new Error(ERROR_NO_ELIMINADO);
        }
    }
    
    /**
     * Modifica el movimiento mensual de la bd
     * @param movimiento 
     */
    public static void modificarMovimientoMensual(MovimientoMensual movimiento){
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
        Date ahora = new Date();
        if (movimiento.isPago() && movimiento.getFecha_pagado().compareTo(ahora)>0){
            throw new Error(ERROR_LIMITE_FECHA);
        }
        DAOMovimientoMensual mm = new DAOMovimientoMensual();        
        if (!mm.update(movimiento)){
            throw new Error(ERROR_NO_MODIFICADO);
        }
    }
    
    /**
     * Devuelve el movimiento mensual de la bd con el id indicado
     * @param id
     * @return 
     */
    public static MovimientoMensual obtenerMovimientoMensual(int id){
        DAOMovimientoMensual mm = new DAOMovimientoMensual();
        MovimientoMensual m = mm.read(id);
        if (m==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        return m;
    }
    
    /**
     * Devuelve la lista de todos los movimientos mensuales 
     * @return 
     */
    public static ArrayList<MovimientoMensual> listarMovimientosMensuales(){
        DAOMovimientoMensual mm = new DAOMovimientoMensual();
        ArrayList<MovimientoMensual> lista = mm.readAll();
        if (lista.isEmpty()){
            throw new Error(ERROR_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * devuelve la lista de movimientos acorde a los parametros indicados
     * @param campo campo a filtrar
     * @param filtro filtro a aplicar sobre campo
     * @return 
     */
    public static ArrayList<MovimientoMensual> buscar(String campo, String filtro){
        DAOMovimientoMensual mm = new DAOMovimientoMensual();
        ArrayList<MovimientoMensual> lista = mm.search(campo, filtro);
        if (lista.isEmpty()){
            //throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }    
    
    /**
     *  devuelve los movimientos perteneceintes a el rango desde y hasta aplicado sobre columna_entre de tipo fecha
     * @param columna_entre String, nombre de la columna, DEBE EXISTIR
     * @param desde 
     * @param hasta
     * @param tipo  0 Todos 1 Ingersos 2 Ergesos
     * @return 
     */
    public static ArrayList<MovimientoMensual> betweenFechas(String columna_entre, java.sql.Date desde, java.sql.Date hasta, int tipo){
        DAOMovimientoMensual mrdao = new DAOMovimientoMensual();
        ArrayList<MovimientoMensual> lista = mrdao.betweenByDate(columna_entre, desde, hasta,tipo);
        if (lista.isEmpty()){
           // throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * Devuelve la lista de movimientos que se encuentran entre el rango desde, hasta de la columna columna_entre
     * con el tipo señalado y filtro opcion_columna aplicado sobre la columna_filtro
     * @param columna_entre nombre de columna de fechas sobre la cual aplicar
     * @param desde 
     * @param hasta
     * @param tipo 0 Todos 1 Ingresos, 2 Egresos
     * @param columna_filtro
     * @param opcion_columna
     * @return     
    /**
     * Devuelve la lista de movimientos que se encuentran entre el rango desde, hasta de la columna columna_entre
     * con el tipo señalado y filtro opcion_columna aplicado sobre la columna_filtro
     * @param columna_entre nombre de columna de fechas sobre la cual aplicar
     * @param desde 
     * @param hasta
     * @param tipo 0 Todos 1 Ingresos, 2 Egresos
     * @param columna_filtro
     * @param opcion_columna
     * @return 
     */
    
    
    
    public static ArrayList<MovimientoMensual> betweenFechasYCategoria(String columna_entre, java.sql.Date desde, java.sql.Date hasta, int tipo, String columna_filtro, String opcion_columna){
        DAOMovimientoMensual mrdao = new DAOMovimientoMensual();
        ArrayList<MovimientoMensual> lista = mrdao.betweenByDateAndFilter(columna_entre, desde, hasta,tipo, columna_filtro, opcion_columna);
        if (lista.isEmpty()){
           // throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }  

}
