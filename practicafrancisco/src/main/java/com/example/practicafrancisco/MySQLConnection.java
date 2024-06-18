package com.example.practicafrancisco;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//esta clase es la de conexion a la base de datos es copia y pega todo menos lo que va despues de localhost que es
//el nombre de la respectiva base de datos


public class MySQLConnection {

    static private final Connection con;

    static{
        try {
            con =  DriverManager.getConnection("jdbc:mysql://localhost/coche","root","");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        return con;
    }
}