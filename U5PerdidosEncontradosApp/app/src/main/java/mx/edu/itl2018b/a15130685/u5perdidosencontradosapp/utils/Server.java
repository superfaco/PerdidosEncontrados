/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                           Despachador de Conexiones
:*
:*  Archivo     : Server.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase estática que implementa singleton, no es heredable.
:*                Tiene método para devolver conexión.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils;

import android.os.StrictMode;

import net.sourceforge.jtds.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Server {

    //Datos de conexión
    private final String HOST;
    private final String PORT;
    private final String DB;
    private final String USER;
    private final String PASS;

    //Implementación del singleton
    //----------------------------------------------------------------------------------------------
    private static Server instance;

    //Constructor
    private Server() throws SQLException {
        //Inicializamos las Constantes.
        HOST = "187.189.201.198";
        PORT = "1433";
        DB = "pmadb";
        USER = "pma";
        PASS = "android";

        //Registramos solo una vez el driver
        try{
            DriverManager.registerDriver(new Driver());
        }catch(SQLException e){
            throw e;
        }
    }

    public static final Server getInstance() throws SQLException {
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtenemos conexión con el DriverManager, usando los valores de las constantes y la cadena
    //de conexión.
    public final Connection getConnection() throws SQLException{
        Connection conn = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + HOST + ":" + PORT + ";"
                    + "databaseName=" + DB + ";user=" + USER
                    + ";password=" + PASS + ";");
        }catch(SQLException e){
            throw e;
        }
        return conn;
    }
    //----------------------------------------------------------------------------------------------

}
