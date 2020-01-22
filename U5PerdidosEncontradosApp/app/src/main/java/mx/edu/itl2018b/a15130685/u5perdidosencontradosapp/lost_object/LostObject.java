/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Modelo para publicaciones de objetos perdidos
:*
:*  Archivo     : LostObject.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Implementa el modelo para mapear la tabla fcal_lost_objects.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object;

import java.sql.Date;
import java.sql.Time;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.User;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseEntity;

public class LostObject extends BaseEntity {
    //Atributos para mapear las columnas de la tabla
    private Integer id;
    private String descr;
    private String place;
    private Date findDate;
    private User usr;

    //Constructores
    //----------------------------------------------------------------------------------------------
    public LostObject() {
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public LostObject(Integer id, String descr, String place, Date findDate, User usr) {
        this.id = id;
        this.descr = descr;
        this.place = place;
        this.findDate = findDate;
        this.usr = usr;
    }
    //----------------------------------------------------------------------------------------------

    //Getters y Setters
    //----------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setId(Integer id) {
        this.id = id;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public String getDescr() {
        return descr;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setDescr(String descr) {
        this.descr = descr;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public String getPlace() {
        return place;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setPlace(String place) {
        this.place = place;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public Date getFindDate() {
        return findDate;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public User getUsr() {
        if(usr == null){
            usr = new User();
        }
        return usr;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setUsr(User usr) {
        this.usr = usr;
    }
    //----------------------------------------------------------------------------------------------

    //Implementación para limpiar modelo
    @Override
    public void clean() {
        this.id = null;
        this.descr = null;
        this.place = null;
        this.findDate = null;
        this.usr = null;
    }
}
