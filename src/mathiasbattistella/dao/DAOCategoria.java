/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mathiasbattistella.dto.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mathiasbattistella.conexion.Conectar;
import mathiasbattistella.controlador.CCategoria;
import mathiasbattistella.dto.Movimiento;
import mathiasbattistella.interfaces.ICategoria;


/**
 *
 * @author user6
 */
public class DAOCategoria implements ICategoria<Categoria>{

    /****** SENTENCIAS *****/
    
    private static final String SQL_INSERT = "INSERT INTO Categoria VALUES (?,?)";
    private static final String SQL_DELETE = "DELETE FROM Categoria WHERE nombre=?";
    private static final String SQL_UPDATE = "UPDATE Categoria SET descripcion=? WHERE nombre=?";
    private static final String SQL_READ   = "SELECT * FROM Categoria WHERE nombre=?";
    private static final String SQL_READALL = "SELECT * FROM Categoria ORDER BY nombre";
    private static final String SQL_SEARCH1 = "SELECT * FROM Categoria WHERE columna LIKE ? ORDER BY orden";
    private static final String SQL_SEARCH_BY_CATEGORIAS = "select id, nombre, descripcion, monto,  categoria, es_ingreso, fecha_pagado from MovimientoMensual "
                                                            + "where categoria = ?"
                                                            + "union "
                                                            + "select id, nombre, descripcion, monto,  categoria, es_ingreso, fecha_pagado from MovimientoRapido "
                                                            + "where categoria = ?;";    
    
    /****** CRUD *****/
    
    private static boolean retorno = false;
   
    private static final Conectar CONNECTION = Conectar.estado();
    private PreparedStatement ps;
    private Categoria c = null;
    private ResultSet resultado;            
    private ArrayList<Categoria> l;
    
    @Override
    public boolean crear(Categoria c) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());

            if (ps.executeUpdate() > 0){
                retorno = true;
            }
        } catch (Exception e) {
            throw new Error("ERROR " + e.getMessage() + " en " + this.getClass().getName());
        }finally{
           CONNECTION.cerrarConexion();
        }
        return retorno;
    }

    @Override
    public boolean delete(Object key) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_DELETE);
            ps.setString(1, ((String)key));
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
    public boolean update(Categoria c) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getDescripcion());
            ps.setString(2, c.getNombre());
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
    public Categoria read(Object key) {
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_READ);
            ps.setString(1, ((String)key));
            resultado = ps.executeQuery();
            while(resultado.next()){
                 c = new Categoria(
                    resultado.getString("nombre"),
                    resultado.getString("descripcion")
                 );
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
    public ArrayList<Categoria> readAll() {
        l = new ArrayList<>();
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_READALL);
            resultado = ps.executeQuery();
            while(resultado.next()){
                 c = new Categoria(
                    resultado.getString("nombre"),
                    resultado.getString("descripcion")      
                 );
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
    public ArrayList<Categoria> search(String campo, String filter, String orden) {
        l = new ArrayList<>();
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_SEARCH1.replace("columna", campo).replace("orden", orden));
            ps.setString(1, "%"+filter+"%");
            resultado = ps.executeQuery();
            while(resultado.next()){
                 c = new Categoria(
                    resultado.getString("nombre"),
                    resultado.getString("descripcion")        
                 );
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
    public ArrayList<Movimiento> obtenerMovimientosPorCategoria(String id) {
        Movimiento m;
        ArrayList<Movimiento> lista_movimientos;
        lista_movimientos = new ArrayList<>();
        try {
            ps = CONNECTION.getConnection().prepareStatement(SQL_SEARCH_BY_CATEGORIAS);
            ps.setString(1, id);
            ps.setString(2, id);
            resultado = ps.executeQuery();
            while(resultado.next()){
         
                 m = new Movimiento(
                    resultado.getInt("id"),
                    resultado.getString("nombre"),
                    resultado.getString("descripcion"),
                    resultado.getDouble("monto"),
                    CCategoria.obtenerCategoria(resultado.getString("categoria")),
                    resultado.getBoolean("es_ingreso"),
                    resultado.getDate("fecha_pagado")
                 );
                 lista_movimientos.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR " + ex.getMessage() + " en " + this.getClass().getName());
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            CONNECTION.cerrarConexion();
        }
        return lista_movimientos;
    }



}
