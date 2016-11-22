/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.interfaces;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author user6
 * @param <Objeto>
 */
public interface IMovimiento<Objeto> {

    /**
     * Crea el objeto en la base de datos
     * @param c true=exito, false=error
     * @return 
     */    
    public boolean crear(Objeto c);

    /**
     * borra el objeto de la bd
     * @param key identificador tipo int
     * @return 
     */    
    public boolean delete(Object key);

    /**
     *  actualiza el objeto
     * @param c  true=exito, false=error
     * @return 
     */    
    public boolean update(Objeto c);

    /**
     * Obtiene el objeto de la bd
     * @param key identificador, tipo int
     * @return 
     */    
    public Objeto read(Object key);

    /**
     * Devuelve todos los elementos de la bd de el tipo indicado
     * @return 
     */    
    public ArrayList<Objeto> readAll();

     /**
     * Devuelve todos los elementos de la bd que contengan filter y sean de el tipo indicado
     * @param campo columna de la bd, DEBE EXISTIR
     * @param filter filtro a aplicar
     * @return lista acorde
     */
    public ArrayList<Objeto> search(String campo, String filter);

    /**
     * Devuelve la lista de elementos de la busqueda between sobre columna columna_entre y con el filtro tipo con los objetos del tipo indicado
     * @param columna_entre columna, tipo Date, de la bd, de la que se hace la busqueda
     * @param desde fecha desde
     * @param hasta fecha hasta
     * @param tipo  0 Todos 1 Ingersos 2 Ergesos
     * @return la lista
     */    
    public ArrayList<Objeto> betweenByDate(String columna_entre, Date desde, Date hasta, int tipo);

    /**
     * Devuelve la lsita de objetos del tipo adecuado, igual que betweenByDate, pero ademas filtra por columna_filtro
     * @param columna_entre columna, tipo Date, en la que se aplica desde hasta
     * @param desde inicio del rango a aplicar sobre columna_entre
     * @param hasta fin del rango a aplicar sobre columna_entre
     * @param tipo 0 Todos, 1, Ingreso, 2, Egreso
     * @param columna_filtro columna a aplicar el filtro opcion_columna
     * @param opcion_columna filtro a aplicar sobre columna_filtro
     * @return 
     */    
    public ArrayList<Objeto> betweenByDateAndFilter(String columna_entre, Date desde, Date hasta, int tipo, String columna_filtro,String opcion_columna);
}
