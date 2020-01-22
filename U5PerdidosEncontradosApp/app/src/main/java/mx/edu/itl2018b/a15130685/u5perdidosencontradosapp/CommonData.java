/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                                Singleton de sesión
:*
:*  Archivo     : CommonData.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase que implementa singleton para guardar datos de sesión, o que se van a
:*                pasar entre actividades.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObject;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.User;

public final class CommonData {
    //Implementación del Singleton
    //----------------------------------------------------------------------------------------------
    private static CommonData instance;
    private CommonData(){}

    public static CommonData getInstance(){
        if(instance == null){
            instance = new CommonData();
        }
        return instance;
    }
    //----------------------------------------------------------------------------------------------

    //Datos de la sesión
    public User user;

    //Datos de inspeccionar
    public LostObject lostObject;

}
