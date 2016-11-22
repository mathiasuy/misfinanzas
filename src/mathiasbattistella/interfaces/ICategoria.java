/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.interfaces;

import java.util.ArrayList;
import mathiasbattistella.dto.Movimiento;

/**
 *
 * @author user6
 * @param <Objeto>
 */
public interface ICategoria <Objeto> {
    /**
     * Inserta la categoria en la bd
     * @param c Categoria
     * @return true si se hizo con exito false en otro caso
     */
    public boolean crear(Objeto c);
    
    /**
     * Borra la categoria de la bd
     * @param key tipo String, el nombre de la categoria
     * @return true si se borro, false de lo contrario
     */
    public boolean delete(Object key);
    
    /**
     * Modifica la categoria de la bd
     * @param c Categoria
     * @return true en caso de exito, false en otro caso
     */
    public boolean update(Objeto c);

    /**
     * Devuelve la categoria de la bd que tiene el id de c
     * @param key Categoria
     * @return 
     */
    public Objeto read(Object key);
    
    /**
     * Devuelve la lista de todas la categorias insertadas en la bd
     * @return 
     */
    public ArrayList<Objeto> readAll();
    
    /**
     * Devuelve los resultados de la busqueda en la que se aplico filter sobre campo en el orden orden
     * @param campo nombre de columna de la bd
     * @param filter filtro a aplicar sobre la columna campo
     * @param orden columna sobre la que se realiza el orden de la lista
     * @return 
     */
    public ArrayList<Objeto> search(String campo, String filter, String orden);
    
    /**
     * Devuelve la lista de tipos Movimiento que corresponden a la categoria con id id
     * @param id nombre de la categoria, tipo String
     * @return 
     */
    public ArrayList<Movimiento> obtenerMovimientosPorCategoria(String id);
}
