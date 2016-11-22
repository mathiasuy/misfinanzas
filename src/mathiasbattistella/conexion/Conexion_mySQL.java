/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.conexion;

import mathiasbattistella.share.Error;
import java.sql.DriverManager;

/**
 *
 * @author user6
 */
public class Conexion_mySQL extends Conexion{

    private String opciones;
    
    public Conexion_mySQL() throws Error {

        opciones = System.clearProperty("options");

        iniciardb();
    }

    @Override
    public void iniciardb() throws Error {
        try{
            
            this.datosConexion = jdbc + host + "/" + database + "?";
            Class.forName(driver).newInstance();
            conn= DriverManager.getConnection(datosConexion, username, password);
//            dbmt = conn.getMetaData();
//            stmt=conn.createStatement();
        }
        catch(Exception e){
           throw new Error("Ha ocurrido un error al conectar a la base de datos");
        }
    }        
}
