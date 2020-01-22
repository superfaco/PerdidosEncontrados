/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       DAO para publicaciones de objectos perdidos
:*
:*  Archivo     : LostObjectDao.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Implementa el DAO para las publicaciones de objectos perdidos, heredando
:*                e implementando los métodos de BaseDao.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.User;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Server;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.TablesCatalog;

public class LostObjectDao extends BaseDao<LostObject> {

    //----------------------------------------------------------------------------------------------
    //Implementación del Singleton
    private static LostObjectDao instance;
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    private LostObjectDao(){}
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public static LostObjectDao getInstance(){
        if(instance == null){
            instance = new LostObjectDao();
        }
        return instance;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int insert(LostObject entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " insert into " + TablesCatalog.LOST_OBJECTS_TABLE
                    + " ( "
                            + TablesCatalog.LOST_OBJECTS_TABLE_DESCR + " , "
                            + TablesCatalog.LOST_OBJECTS_TABLE_PLACE + " , "
                            + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " , "
                            + TablesCatalog.LOST_OBJECTS_TABLE_USER
                    + " ) "
                    + " values(?, ?, ?, ?) "
            );
            int p = 1;
            ps.setString(p++, entity.getDescr());
            ps.setString(p++, entity.getPlace());
            ps.setDate(p++, entity.getFindDate());
            ps.setInt(p++, entity.getUsr().getId());
            c = ps.executeUpdate();
            LostObject lostObjectAux = getRecord(entity);
            entity.setId(lostObjectAux.getId());
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
    public int update(LostObject entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " update " + TablesCatalog.LOST_OBJECTS_TABLE
                    + " set "
                    + TablesCatalog.LOST_OBJECTS_TABLE_DESCR + " = ?, "
                    + TablesCatalog.LOST_OBJECTS_TABLE_PLACE + " = ?, "
                    + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " = ?, "
                    + TablesCatalog.LOST_OBJECTS_TABLE_USER + " = ? "
                    + " where " + TablesCatalog.LOST_OBJECTS_TABLE_ID + " = ?"
            );
            int p = 1;
            ps.setString(p++, entity.getDescr());
            ps.setString(p++, entity.getPlace());
            ps.setDate(p++, entity.getFindDate());
            ps.setInt(p++, entity.getUsr().getId());
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
    public int delete(LostObject entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " delete from " + TablesCatalog.LOST_OBJECTS_TABLE
                    + " where " + TablesCatalog.LOST_OBJECTS_TABLE_ID + " = ?"
            );
            int p = 1;
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
    public List<LostObject> getAllRecords() throws Exception {
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                            + " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc "
            );

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public List<LostObject> getAllRecords(int offset, int rowCount) throws Exception {
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                            + " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc "
                            + " offset " + offset + " rows fetch next " + rowCount + " rows"
            );

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene las publicaciones en un rango de fechas, que cumplan con un patrón de descripción y un patrón
    //de lugar.
    public List<LostObject> getRecordsInRange(String startDate, String endDate, String descr, String place) throws Exception{
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            String s = " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                    + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                    + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                    + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                    + " where 1 = 1 ";

            if(!startDate.trim().equals("") && !endDate.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " between ? and ?";
            }
            if(!place.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_PLACE + " like ? ";
            }
            if(!descr.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_DESCR + " like ? ";
            }

            s += " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc ";

            ps = conn.prepareStatement(s);

            int p = 1;
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

            long ms = 0;
            if(!startDate.trim().equals("")) {
                ms = date.parse(startDate).getTime();
                ps.setDate(p++, new Date(ms));
            }
            if(!endDate.trim().equals("")) {
                ms = date.parse(endDate).getTime();
                ps.setDate(p++, new Date(ms));
            }
            if(!place.trim().equals("")) {
                ps.setString(p++, "%" + place + "%");
            }
            if(!descr.trim().equals("")) {
                ps.setString(p++, "%" + descr + "%");
            }

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene las publicaciones en un rango de fechas, que cumplan con un patrón de descripción y un patrón
    //de lugar, usando paginación.
    public List<LostObject> getRecordsInRange(String startDate, String endDate, String descr, String place, int offset, int rowCount) throws Exception{
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            String s = " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                    + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                    + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                    + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                    + " where 1 = 1 ";

            if(!startDate.trim().equals("") && !endDate.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " between ? and ?";
            }
            if(!place.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_PLACE + " like ? ";
            }
            if(!descr.trim().equals("")){
                s += " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_DESCR + " like ? ";
            }

            s += " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc ";
            s += " offset " + offset + " rows fetch next " + rowCount + " rows";

            ps = conn.prepareStatement(s);

            int p = 1;
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

            long ms = 0;
            if(!startDate.trim().equals("")) {
                ms = date.parse(startDate).getTime();
                ps.setDate(p++, new Date(ms));
            }
            if(!endDate.trim().equals("")) {
                ms = date.parse(endDate).getTime();
                ps.setDate(p++, new Date(ms));
            }
            if(!place.trim().equals("")) {
                ps.setString(p++, "%" + place + "%");
            }
            if(!descr.trim().equals("")) {
                ps.setString(p++, "%" + descr + "%");
            }

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene todas las publicaciónes de un usuario especificado, usando paginación.
    public List<LostObject> getRecordsByUser(User u, int offset, int rowCount) throws Exception {
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE_ID
                            + " where " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER + " = ? "
                            + " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc "
                            + " offset " + offset + " rows fetch next " + rowCount + " rows"
            );

            int p = 1;
            ps.setInt(p++, u.getId());

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene todas las publicaciones de un usuario especificado
    public List<LostObject> getRecordsByUser(User u) throws Exception {
        List<LostObject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                            + " where " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER + " = ? "
                            + " order by " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " desc "
            );

            int p = 1;
            ps.setInt(p++, u.getId());

            rs = ps.executeQuery();

            while(rs.next()){
                LostObject lo = new LostObject();
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
                list.add(lo);
            }

        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return list;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public LostObject getRecordById(LostObject entity) throws Exception {
        LostObject lo = new LostObject();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                            + " where " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_ID + " = ?"

            );
            int p = 1;
            ps.setInt(p++, entity.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
            }
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return lo;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public LostObject getRecord(LostObject entity) throws Exception {
        LostObject lo = new LostObject();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select " + TablesCatalog.LOST_OBJECTS_TABLE + ".*, "
                            + TablesCatalog.USERS_TABLE + ".*" + " from " + TablesCatalog.LOST_OBJECTS_TABLE
                            + " join " + TablesCatalog.USERS_TABLE + " on " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_USER
                            + " = " + TablesCatalog.USERS_TABLE + "." + TablesCatalog.USERS_TABLE_ID
                            + " where " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_DESCR + " = ? "
                            + " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_PLACE + " = ? "
                            + " and " + TablesCatalog.LOST_OBJECTS_TABLE + "." + TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE + " between ? and ? "

            );
            int p = 1;
            ps.setString(p++, entity.getDescr());
            ps.setString(p++, entity.getPlace());
            ps.setDate(p++, entity.getFindDate());
            ps.setDate(p++, entity.getFindDate());
            rs = ps.executeQuery();
            if(rs.next()){
                lo.setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_ID));
                lo.setDescr(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_DESCR));
                lo.setPlace(rs.getString(TablesCatalog.LOST_OBJECTS_TABLE_PLACE));
                lo.setFindDate(rs.getDate(TablesCatalog.LOST_OBJECTS_TABLE_FINDDATE));
                lo.getUsr().setId(rs.getInt(TablesCatalog.LOST_OBJECTS_TABLE_USER));
                lo.getUsr().setUserName(rs.getString(TablesCatalog.USERS_TABLE_USERNAME));
                lo.getUsr().setPhoneNumber(rs.getString(TablesCatalog.USERS_TABLE_PHONE));
            }
        }catch(Exception e){
            throw e;
        }finally{
            close(conn, ps, rs);
        }
        return lo;
    }
    //----------------------------------------------------------------------------------------------
}
