package mathiasbattistella.controlador;

import java.util.ArrayList;
import mathiasbattistella.dao.DAOCategoria;
import mathiasbattistella.dao.DAOMovimientoMensual;
import mathiasbattistella.dao.DAOMovimientoRapido;
import mathiasbattistella.dto.Categoria;
import mathiasbattistella.dto.*;

/**
 *
 * @author user6
 */
public class CCategoria {
    
    /** MENSAJES A DEVOLVER */
    private static final String OBJETO = " la categoría";
    private static final int MAX_NOMBRE = 50;
    private static final int MAX_DESCRIPCION = 200;        
    private static final String ERROR_GENERAL  = "Ha ocurrido un error";
    private static final String ERROR_CAMPO_VACIO_NOMBRE  = "El nombre no debe estar vacio";    
    private static final String ERROR_YAEXISTE = "El elemento ya existe";
    private static final String ERROR_LIMITE_NOMBRE = "El nombre no puede contener más de "+MAX_NOMBRE+" caracteres";
    private static final String ERROR_LIMITE_DESCRIPCION = "La descripción debe ser de menos de "+MAX_DESCRIPCION+" caracteres";
    private static final String ERROR_ALTA = "No se ha podido ingresar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADO = "No se ha encontrado"+OBJETO;
    private static final String ERROR_NO_ELIMINADO = "No se ha podido eliminar"+OBJETO;
    private static final String ERROR_NO_MODIFICADO = "No se ha podido modificar"+OBJETO;
    private static final String ERROR_NO_ENCONTRADOS = "No se han encontrado categorías, deberá agregar al menos una para poder ingresar Movimientos";
    private static final String ERROR_BUSQUEDA_NO_ENCONTRADOS = "No se han encontrado elementos que coincidan con los parámetros ingresados";
    private static final String ERROR_CATEGORIA_LIGADA_MRAPIDO = "La categoría está asignada a al menos un Movimiento Rápido, no se borrará, si lo desea puede modificarla o eliminar los elementos a los que está ligada";
    private static final String ERROR_CATEGORIA_LIGADA_MMENSUAL = "La categoría está asignada a al menos un Movimiento Mensual, no se borrará, si lo desea puede modificarla o eliminar los elementos a los que está ligada";
    private static final String ERROR_YA_EXISTE_CATEGORIA_NOMBRE = "¡Ya existe una categoría con ese nombre!";
    
    
    /* METODOS Y FUNCIONES */
    
    /**
     * Inserta la categoria a la bd
     * @param categoria 
     */
    public static void insertarCategoria(Categoria categoria){
        categoria.setNombre(categoria.getNombre().trim());
        if (categoria.getNombre().isEmpty()){
            throw new Error(ERROR_CAMPO_VACIO_NOMBRE);
        }
        if (categoria.getNombre().length() >MAX_NOMBRE){
            throw new Error(ERROR_LIMITE_NOMBRE);
        }
        if (categoria.getDescripcion().length() > MAX_DESCRIPCION){
            throw new Error(ERROR_LIMITE_DESCRIPCION);
        }
        DAOCategoria cdao = new DAOCategoria();
        if (cdao.read(categoria.getNombre())!=null){
            throw new Error(ERROR_YAEXISTE);
        }
        if (!cdao.crear(categoria)){
            throw new Error(ERROR_ALTA);
        }
    }
    
    /**
     * elimina la categoria de la bd
     * @param id identificador tipo String
     */
    public static void borrarCategoria(String id){

        DAOCategoria cdao = new DAOCategoria();
        
        DAOMovimientoRapido  mr = new DAOMovimientoRapido();
        DAOMovimientoMensual mm = new DAOMovimientoMensual();
        
        if (id.trim().compareTo("")==0){
            throw new Error(ERROR_CAMPO_VACIO_NOMBRE);
        }
        Categoria categoria = cdao.read(id.trim());

        if (categoria==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
//        ArrayList<MovimientoRapido> l = mr.search("categoria", categoria.getNombre());
//        System.out.println("lista "+ l.toString()+ (l==null?"es null":"no es null")+ (l.isEmpty()?"vacia":"no vacia"));
        if (!mr.search("categoria", categoria.getNombre()).isEmpty()){
            throw new Error(ERROR_CATEGORIA_LIGADA_MRAPIDO);
        }        
        if (!mm.search("categoria", categoria.getNombre() ).isEmpty()){
            throw new Error(ERROR_CATEGORIA_LIGADA_MMENSUAL);
        }                
        if (!cdao.delete(id)){
            throw new Error(ERROR_NO_ELIMINADO);
        }
    }
    
    /**
     * Modifica la cateogría de la bd
     * @param categoria  
     */
    public static void modificarCategoria(Categoria categoria){
        categoria.setNombre(categoria.getNombre().trim());
        if (categoria.getNombre().compareTo("")==0){
            throw new Error(ERROR_CAMPO_VACIO_NOMBRE);
        }        
        
        if (categoria.getNombre().length() >MAX_NOMBRE){
            throw new Error(ERROR_LIMITE_NOMBRE);
        }
        if (categoria.getDescripcion().length() > MAX_DESCRIPCION){
            throw new Error(ERROR_LIMITE_DESCRIPCION);
        }
        DAOCategoria cdao = new DAOCategoria();
        if (cdao.read(categoria.getNombre())==null){
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        if (!cdao.update(categoria)){
            throw new Error(ERROR_NO_MODIFICADO);
        }
    }
    
    /**
     * Devuelve la categoría correspondiente al identificador id en la bd
     * @param id String
     * @return 
     */
    public static Categoria obtenerCategoria(String id){
        if (id.compareTo("")==0){
            throw new Error("Debe ingresar el nombre de la categoria a mdoificar");
        }
        DAOCategoria cdao = new DAOCategoria();
        Categoria categoria = cdao.read(id.trim());
        if (categoria==null){
            //System.out.println("llgogogoogo" + id);
            throw new Error(ERROR_NO_ENCONTRADO);
        }
        return categoria;
    }
    
    /**
     * Devuelve la lista de todas las categorías en la bd
     * @return 
     */
    public static ArrayList<Categoria> listarCategorias(){
        DAOCategoria cdao = new DAOCategoria();
        ArrayList<Categoria> lista = cdao.readAll();
        if (lista.isEmpty()){
            //throw new Error(ERROR_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * Busca la categoría con los parametros solicitados, filtro de la columna campo en el orden orden
     * @param campo columna a aplicar filtro 
     * @param filtro filtro a aplicar sobre campo
     * @param orden columna a ordenar
     * @return 
     */
    public static ArrayList<Categoria> buscarCategorias(String campo, String filtro, String orden){
        
        DAOCategoria cdao = new DAOCategoria();
        ArrayList<Categoria> lista = cdao.search(campo, filtro.trim(), orden);
        if (lista.isEmpty()){
            //throw new Error(ERROR_BUSQUEDA_NO_ENCONTRADOS);
        }
        return lista;
    }
    
    /**
     * devuelve los movimientos de la categoría que tiene el identificador id
     * @param id identificado de la categoría
     * @return 
     */
    public static ArrayList<Movimiento> obtenerMovimientosPorCategoria(String id){
        DAOCategoria cdao = new DAOCategoria();
        
        ArrayList<Movimiento> lista = cdao.obtenerMovimientosPorCategoria(id);
        if (lista.isEmpty()){
            //throw new Error(ERROR_NO_ENCONTRADO);
        }
        return lista;
    }

    
}
