/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Implementación del DAO para los usuarios
:*
:*  Archivo     : UserDao.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Implementa el DAO, herendando de BaseDao e implementando sus métodos.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Encrypter;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Server;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.TablesCatalog;

public final class UserDao extends BaseDao<User> {

    //Implementación del Singleton
    //----------------------------------------------------------------------------------------------
    private static UserDao instance;

    private UserDao(){}

    public static UserDao getInstance(){
        if(instance == null){
            instance = new UserDao();
        }
        return instance;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int insert(User entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " insert into " + TablesCatalog.USERS_TABLE + "" +
                    " ( " +
                    TablesCatalog.USERS_TABLE_USERNAME + ", " +
                    TablesCatalog.USERS_TABLE_PASS + ", " +
                    TablesCatalog.USERS_TABLE_PHONE +
                    " ) values (?, ?, ?)");
            int p = 1;
            ps.setString(p++, entity.getUserName());
            //Encriptamos el password
            entity.setPass(Encrypter.encrypt(entity.getPass()));
            ps.setString(p++, entity.getPass());
            ps.setString(p++, entity.getPhoneNumber());
            c = ps.executeUpdate();
            User u = getRecord(entity);
            entity.setId(u.getId());
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, null);
        }
        return c;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int update(User entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " update " + TablesCatalog.USERS_TABLE
                    + " set "
                    + TablesCatalog.USERS_TABLE_USERNAME  + " = ?, "
                    + TablesCatalog.USERS_TABLE_PASS + " = ?, "
                    + TablesCatalog.USERS_TABLE_PHONE + " = ? "
                    + " where " + TablesCatalog.USERS_TABLE_ID + " = ?"
            );
            int p = 1;
            ps.setString(p++, entity.getUserName());
            //Encriptamos el password
            entity.setPass(Encrypter.encrypt(entity.getPass()));
            ps.setString(p++, entity.getPass());
            ps.setString(p++, entity.getPhoneNumber());
            ps.setInt(p++, entity.getId());
            c = ps.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, null);
        }
        return c;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int delete(User entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " delete from " + TablesCatalog.USERS_TABLE
                    + " where " + TablesCatalog.USERS_TABLE_ID + " = ?"
            );
            int p = 1;
            ps.setInt(p++, entity.getId());
            c = ps.executeUpdate();
            entity.clean();
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, null);
        }
        return c;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public List<User> getAllRecords() throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public List<User> getAllRecords(int offset, int rowCount) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public User getRecordById(User entity) throws Exception {

        User u = new User();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select * from " + TablesCatalog.USERS_TABLE
                    + " where " + TablesCatalog.USERS_TABLE_ID + " = ?"
            );
            int p = 1;
            ps.setInt(p++, entity.getId());
            rs = ps.executeQuery();

            if(rs.next()){
                u.setId(rs.getInt(TablesCatalog.USERS_TABLE_ID));
                u.setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                u.setPass(rs.getString(TablesCatalog.USERS_TABLE_PASS));
                u.setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
            }
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return u;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public User getRecord(User entity) throws Exception {

        User u = new User();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select * from " + TablesCatalog.USERS_TABLE
                    + " where " + TablesCatalog.USERS_TABLE_USERNAME + " = ?"
            );

            int p = 1;
            ps.setString(p++, entity.getUserName());
            rs = ps.executeQuery();

            if(rs.next()){
                u.setId(rs.getInt(TablesCatalog.USERS_TABLE_ID));
                u.setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                u.setPass(rs.getString(TablesCatalog.USERS_TABLE_PASS));
                u.setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
            }
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return u;
    }
    //----------------------------------------------------------------------------------------------
}
