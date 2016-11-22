/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.conexion;

import mathiasbattistella.share.Error;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author user6
 */
public class Conexion {
    public Connection conn=null;
    public DatabaseMetaData dbmt;
    public String datosConexion;
    
    protected String jdbc;
    protected String driver;
    protected String host;
    protected String database;
    protected String username;
    protected String password;
    
    
    public Conexion() throws Error{
        //obtenemos los parametros de el archivo de configuracion señalado en Inicio.java
        this.jdbc = System.getProperty("jdbc");
        this.driver = System.getProperty("driver");
        this.host = System.getProperty("host");
        this.database = System.getProperty("database");
        this.username = System.getProperty("username");
        this.password = System.getProperty("password");
        
        try {
            iniciardb();
        } catch (Exception e) {
            throw new Error("Ha ocurrido un error al conectar a la base de datos");
        }
    }

    public void iniciardb() throws Error {
        try{
            this.datosConexion = jdbc + host + "/" + database;
            Class.forName(driver).newInstance();
            conn= DriverManager.getConnection(datosConexion, username, password);
        }
        catch(Exception e){
           throw new Error("Ha ocurrido un error al conectar a la base de datos");
        }
    }
}
