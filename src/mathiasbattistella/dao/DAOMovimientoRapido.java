/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mathiasbattistella.conexion.Conectar;
import mathiasbattistella.dto.Categoria;
import mathiasbattistella.dto.MovimientoRapido;
import mathiasbattistella.interfaces.IMovimiento;

/**
 *
 * @author user6
 */
public class DAOMovimientoRapido implements IMovimiento<MovimientoRapido>{
    
    /****** SENTENCIAS *****/
    
    private static final String SQL_TABLA = "MovimientoRapido";
    private static final String SQL_INSERT = "INSERT INTO "+SQL_TABLA+" VALUES (0,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM "+SQL_TABLA+" WHERE id=?";
    private static final String SQL_UPDATE = 
            "UPDATE "+SQL_TABLA+" "
            + "SET nombre=?, descripcion=?, monto=?, categoria=?, es_ingreso=?, fecha_pagado=?"
            + "WHERE id=?";
    private static final String SQL_READ   = "SELECT * FROM "+SQL_TABLA+" WHERE id=?";
    private static final String SQL_READALL = "SELECT * FROM "+SQL_TABLA+" ORDER BY nombre";
    private static final String SQL_SEARCH1 = "SELECT * FROM "+SQL_TABLA+" WHERE columna LIKE ? ORDER BY nombre";
    private static final String SQL_SEARCH_BETWEEN = "SELECT * FROM "+SQL_TABLA+" WHERE es_ingreso=? AND columna_entre BETWEEN ? AND ?";
    private static final String SQL_SEARCH_CATEGORIA = "  AND columna_filtro=?";        
   private static boolean retorno = false;

    private static final Conectar CONNECTION = Conectar.estado();
    private PreparedStatement ps;
    private ResultSet resultado;
    private MovimientoRapido c = null;
    private ArrayList<MovimientoRapido> l;


    /****** CRUD *****/
    
    
    @Override
    public boolean crear(MovimientoRapido c) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setDouble(3, c.getMonto());
            ps.setString(4, c.getCategoria().getNombre());            
            ps.setBoolean(5, c.isEs_ingreso());
            ps.setDate(6, c.getFecha_pagado());
            if (ps.executeUpdate() > 0){
                resultado = ps.executeQuery("SELECT @@identity AS id");
                while(resultado.next()){
                    c.setId(resultado.getInt("id"));
                }
                retorno = true;
            }
        } catch (Exception ex) {
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
           CONNECTION.cerrarConexion();
        }
        return retorno;
    }


    @Override
    public boolean delete(Object key) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_DELETE);
            ps.setInt(1, ((int)key));
            if (ps.executeUpdate()>0){
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return retorno;
    }


    @Override
    public boolean update(MovimientoRapido c) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setDouble(3, c.getMonto());
            ps.setString(4, c.getCategoria().getNombre());            
            ps.setBoolean(5, c.isEs_ingreso());
            ps.setDate(6, c.getFecha_pagado());
            ps.setInt(7, c.getId());
            if (ps.executeUpdate()>0){
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return retorno;
    }

    @Override
    public MovimientoRapido read(Object key) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_READ);
            ps.setInt(1, (int)key);
            resultado = ps.executeQuery();
            while(resultado.next()){
                DAOCategoria categoria = new DAOCategoria();
                 c = new MovimientoRapido(
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("monto"),
                        categoria.read(resultado.getString("categoria")),
                        resultado.getBoolean("es_ingreso"),
                        resultado.getDate("fecha_pagado")
                 );
                 c.setId(resultado.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return c;
    }
    

    @Override
    public ArrayList<MovimientoRapido> readAll() {
        l = new ArrayList<>();
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_READALL);
            resultado = ps.executeQuery();
            while(resultado.next()){
                DAOCategoria cdao = new DAOCategoria();
                Categoria categoria = cdao.read(resultado.getString("categoria"));
              
                 c = new MovimientoRapido(
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("monto"),
                        categoria,
                        resultado.getBoolean("es_ingreso"),
                        resultado.getDate("fecha_pagado")
                 );
                 c.setId(resultado.getInt("id"));
                 l.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return l;
    }

    @Override
    public ArrayList<MovimientoRapido> search(String campo, String filter) {
        l = new ArrayList<>();
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_SEARCH1.replace("columna", campo));
            ps.setString(1, "%"+filter+"%");
            resultado = ps.executeQuery();
            while(resultado.next()){
                DAOCategoria categoria = new DAOCategoria();
                 c = new MovimientoRapido(
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("monto"),
                        categoria.read(resultado.getString("categoria")),
                        resultado.getBoolean("es_ingreso"),
                        resultado.getDate("fecha_pagado")
                 );
                 c.setId(resultado.getInt("id"));
                 l.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return l;
    }
    

    @Override
    public ArrayList<MovimientoRapido> betweenByDate(String columna_entre, Date desde, Date hasta,int tipo) {
        l = new ArrayList<>();
        try {
            String enunciado = SQL_SEARCH_BETWEEN.replace("columna_entre", columna_entre);
            switch (tipo){
                case 0 :    ps = CONNECTION.getConnection().prepareStatement(enunciado + " union " + 
                            enunciado + " order by nombre");
                            ps.setBoolean(1, true);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                            ps.setBoolean(4, false);
                            ps.setDate(5, desde);
                            ps.setDate(6, hasta);

                    break;
                case 1 :    ps = CONNECTION.getConnection().prepareStatement(enunciado + " order by nombre");
                            ps.setBoolean(1, true);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                    break;
                case 2 :    ps = CONNECTION.getConnection().prepareStatement(enunciado + " order by nombre");
                            ps.setBoolean(1, false);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                     break;
            }
            resultado = ps.executeQuery();
            while(resultado.next()){
                DAOCategoria categoria = new DAOCategoria();
                 c = new MovimientoRapido(
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("monto"),
                        categoria.read(resultado.getString("categoria")),
                        resultado.getBoolean("es_ingreso"),
                        resultado.getDate("fecha_pagado")
                 );
                 c.setId(resultado.getInt("id"));
                 l.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return l;
    }    



    @Override
    public ArrayList<MovimientoRapido> betweenByDateAndFilter(String columna_entre, Date desde, Date hasta, int tipo, String columna_filtro, String opcion_columna) {
        l = new ArrayList<>();
        try {
            String sentencia = SQL_SEARCH_BETWEEN.replace("columna_entre", columna_entre) + SQL_SEARCH_CATEGORIA.replace("columna_filtro", columna_filtro);
            switch (tipo){
                case 0 :    ps = CONNECTION.getConnection().prepareStatement(sentencia + " union " + 
                            sentencia + " order by nombre");
                            ps.setBoolean(1, true);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                            ps.setString(4, opcion_columna);                            
                            ps.setBoolean(5, false);
                            ps.setDate(6, desde);
                            ps.setDate(7, hasta);
                            ps.setString(8, opcion_columna);

                    break;
                case 1 :    ps = CONNECTION.getConnection().prepareStatement(sentencia+ " order by nombre");
                            ps.setBoolean(1, true);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                            ps.setString(4, opcion_columna);
                    break;
                case 2 :    ps = CONNECTION.getConnection().prepareStatement(sentencia+ " order by nombre");
                            ps.setBoolean(1, false);
                            ps.setDate(2, desde);
                            ps.setDate(3, hasta);
                            ps.setString(4, opcion_columna);
                     break;
            }
            resultado = ps.executeQuery();
            while(resultado.next()){
                DAOCategoria categoria = new DAOCategoria();
                 c = new MovimientoRapido(
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("monto"),
                        categoria.read(resultado.getString("categoria")),
                        resultado.getBoolean("es_ingreso"),
                        resultado.getDate("fecha_pagado")
                 );
                 c.setId(resultado.getInt("id"));
                 l.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
        }finally{
            CONNECTION.cerrarConexion();
        }
        return l;    }
    
    
}
