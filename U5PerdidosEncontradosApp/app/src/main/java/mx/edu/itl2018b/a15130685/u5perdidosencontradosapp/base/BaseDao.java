/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                                DAO básico
:*
:*  Archivo     : BaseDao.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Esta clase sirve como base para los DAOs, como extensión del modelo (MVC),
:*                que sirve para acceder a los datos.
:*                Requiere un tipado que extienda de BaseEntity para funcionar y tiene varias
:*                firmas de métodos de acceso a los datos que son comunes, como insertar, actualizar,
:*                etc.
:*                También contiene un método para cerrar la conexión, sentencias preparadas y conjuntos
:*                resultado.
:*                Las clases que hereden de esta, deben implementar el patrón Singleton, por lo que queda
:*                deshabilitada la acción de clonar.
:*                Tod0 lo anterior, para lograr una Arquitectura Limpia (Independiente del Framework,
:*                Manejador de Base de Datos, etc).
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao<T extends BaseEntity> {

    //----------------------------------------------------------------------------------------------
    //Guarda una entidad.
    public abstract int insert(T entity) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Actualiza una entidad.
    public abstract int update(T entity) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Borra una entidad
    public abstract int delete(T entity) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene todas las entidades
    public abstract List<T> getAllRecords() throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene todas las entidades con paginación
    public abstract List<T> getAllRecords(int offset, int rowCount) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene entidad por id
    public abstract T getRecordById(T entity) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene entidad por parámetros de búsqueda simples
    public abstract T getRecord(T entity) throws Exception;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Método para cerrar conexión, Sentencias preparadas y Conjuntos resultado.
    protected final void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            }catch(SQLException e){

            }
        }
        if(ps != null){
            try{
                ps.close();
            }catch(SQLException e){

            }
        }
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){

            }
        }
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Clonación deshabilitada.
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    //----------------------------------------------------------------------------------------------
}
