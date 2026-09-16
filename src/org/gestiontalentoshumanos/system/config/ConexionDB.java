/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gestiontalentoshumanos.system.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static ConexionDB instanciaConexionDB;
    private Connection connection;
    
      private ConexionDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + 
                                                        Enviroment.LOCATION_SERVICE + "/"
                                                      + Enviroment.DATA_BASE,
                                                        Enviroment.USER, 
                                                        Enviroment.PASSWORD);
        } catch (ClassNotFoundException classNotFound) {
            System.out.println("Error clase no se encontro");
            
        } catch (SQLException sqlException){
            System.out.println("Error de conexion a db");
        } catch (Exception e ){
            System.out.println("Error padre" + e.getMessage());
            
        }
    }

    public static ConexionDB getInstanciaConexionDB() {
        if(instanciaConexionDB == null){
            instanciaConexionDB = new ConexionDB();
        }
        return instanciaConexionDB;
    }

    public static void setInstanciaConexionDB(ConexionDB instanciaConexionDB) {
        ConexionDB.instanciaConexionDB = instanciaConexionDB;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
    
    
    
}
