/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Modelo para los usuarios de la aplicación
:*
:*  Archivo     : User.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Implementa el modelo para mapear la tabla fcal_users.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base.BaseEntity;

public class User extends BaseEntity {
    //Atributos para mapear las columnas de la tabla
    private Integer id;
    private String userName;
    private String pass;
    private String phoneNumber;

    //Constructores
    //----------------------------------------------------------------------------------------------
    public User() {
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public User(Integer id, String userName, String pass, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
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
    public String getUserName() {
        return userName;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setUserName(String userName) {
        this.userName = userName;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public String getPass() {
        return pass;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setPass(String pass) {
        this.pass = pass;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public String getPhoneNumber() {
        return phoneNumber;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    //----------------------------------------------------------------------------------------------

    //Implementación para limpiar el modelo
    //----------------------------------------------------------------------------------------------
    @Override
    public void clean() {
        this.id = null;
        this.userName = null;
        this.pass = null;
        this.phoneNumber = null;
    }
    //----------------------------------------------------------------------------------------------
}
