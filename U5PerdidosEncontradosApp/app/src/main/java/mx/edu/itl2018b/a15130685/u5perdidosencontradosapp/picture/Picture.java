/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Modelo para imágenes de objectos en publicaciones
:*
:*  Archivo     : Picture.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Implementa el modelo para mapear la tabla fcal_pictures.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseEntity;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObject;

public class Picture extends BaseEntity {

    //Atributos para mapear columnas de tabla
    private Integer id;
    private byte[] picture;
    private LostObject lostObject;

    //Constructores
    //----------------------------------------------------------------------------------------------
    public Picture() {
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public Picture(Integer id, byte[] picture, LostObject lostObject) {
        this.id = id;
        this.picture = picture;
        this.lostObject = lostObject;
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
    public byte[] getPicture() {
        return picture;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public LostObject getLostObject() {
        if(lostObject == null)
            lostObject = new LostObject();
        return lostObject;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setLostObject(LostObject lostObject) {
        this.lostObject = lostObject;
    }
    //----------------------------------------------------------------------------------------------

    //Implementación para limpiar el modelo
    @Override
    public void clean() {
        this.id = null;
        this.picture = null;
        this.lostObject = null;
    }
}
