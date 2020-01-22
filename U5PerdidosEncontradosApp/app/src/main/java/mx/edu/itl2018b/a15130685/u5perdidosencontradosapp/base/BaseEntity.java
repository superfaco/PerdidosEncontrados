/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Entidad básica para tipado.
:*
:*  Archivo     : BaseEntity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Esta clase sirve como padre de todas las clases que sean modelos (MVC).
:*                Es necesario heredar de esta clase para poder implementar el DAO,
:*                ya que se requiere que el tipado del DAO herede de esta clase.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.base;

public abstract class BaseEntity{

    //----------------------------------------------------------------------------------------------
    //Método para limpiar el modelo (sus datos).
    public abstract void clean();
    //----------------------------------------------------------------------------------------------
}
