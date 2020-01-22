/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                     Implementación de DAO para las imágenes.
:*
:*  Archivo     : PictureDao.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Hereda de BaseDao e implementa sus métodos para la tabla
:*                fcal_pictures
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture;

import android.app.ActionBar;
import android.media.UnsupportedSchemeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Server;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.TablesCatalog;

public class PictureDao extends BaseDao<Picture> {

    //Implementación del Singleton
    //----------------------------------------------------------------------------------------------
    private static PictureDao instance;

    private PictureDao(){}

    public static PictureDao getInstance(){
        if(instance == null){
            instance = new PictureDao();
        }
        return instance;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int insert(Picture entity) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Método para inserción masiva de imágenes.
    public int[] insertBulk(List<Picture> list) throws Exception{
        int[] c = new int[0];
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(
                    " insert into " + TablesCatalog.PICTURES_TABLE
                            + " ( " + TablesCatalog.PICTURES_TABLE_PICTURE + " , "
                            + TablesCatalog.PICTURES_TABLE_LOST_OBJECT + " ) "
                            + " values (?, ?) "
            );
            for(Picture pic : list){

                int p = 1;
                ps.setBytes(p++, pic.getPicture());
                ps.setInt(p++, pic.getLostObject().getId());
                ps.addBatch();
            }

            c = ps.executeBatch();
            conn.commit();
        }catch(Exception e){
            conn.rollback();
            throw e;
        }finally{
            close(conn, ps, null);
        }
        return c;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public int update(Picture entity) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Borra todas las imágenes de una publicación (la publicación se toma del modelo Picture, es uno
    //de sus atributos)
    @Override
    public int delete(Picture entity) throws Exception {
        int c = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " delete from " + TablesCatalog.PICTURES_TABLE
                    + " where " + TablesCatalog.PICTURES_TABLE_LOST_OBJECT + " = ? "
            );
            int p = 1;
            ps.setInt(p++, entity.getLostObject().getId());
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
    public List<Picture> getAllRecords() throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public List<Picture> getAllRecords(int offset, int rowCount) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //Obtiene todas las imágenes de una publicación especificada (la publicación se toma del
    //modelo Picture, es uno de sus atributos).
    public List<Picture> getRecords(Picture entity) throws Exception{
        List<Picture> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = Server.getInstance().getConnection();
            ps = conn.prepareStatement(
                    " select * from " + TablesCatalog.PICTURES_TABLE
                    + " where " + TablesCatalog.PICTURES_TABLE_LOST_OBJECT + " = ? "
            );
            int p = 1;
            ps.setInt(p++, entity.getLostObject().getId());
            rs = ps.executeQuery();
            while(rs.next()){
                Picture pi = new Picture();
                pi.setId(rs.getInt(TablesCatalog.PICTURES_TABLE_ID));
                pi.setPicture(rs.getBytes(TablesCatalog.PICTURES_TABLE_PICTURE));
                pi.getLostObject().setId(rs.getInt(TablesCatalog.PICTURES_TABLE_LOST_OBJECT));
                list.add(pi);
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
    public Picture getRecordById(Picture entity) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    public Picture getRecord(Picture entity) throws Exception {
        throw new UnsupportedOperationException();
    }
    //----------------------------------------------------------------------------------------------
}
