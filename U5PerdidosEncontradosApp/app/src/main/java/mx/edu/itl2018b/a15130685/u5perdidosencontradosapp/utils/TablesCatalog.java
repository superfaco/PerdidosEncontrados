/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                                Catalogo de tablas
:*
:*  Archivo     : TablesCatalog.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase estática que solo da acceso a constantes para nombrar tablas y sus
:*                columnas. Se utiliza para aumentar considerablemente la escalabilidad y
:*                facilidad de mantenimiento.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils;

public final class TablesCatalog {
    //Implementación de clase estática.
    private TablesCatalog(){
    }

    //Mapeo de metadatos de tabla "users"
    public static final String USERS_TABLE = "fcal_users";
    public static final String USERS_TABLE_ID = "id";
    public static final String USERS_TABLE_USERNAME = "userName";
    public static final String USERS_TABLE_PASS = "pass";
    public static final String USERS_TABLE_PHONE = "phoneNumber";

    //Mapeo de metadatos de tabla "lost_objects"
    public static final String LOST_OBJECTS_TABLE = "fcal_lost_objects";
    public static final String LOST_OBJECTS_TABLE_ID = "id";
    public static final String LOST_OBJECTS_TABLE_DESCR = "descr";
    public static final String LOST_OBJECTS_TABLE_PLACE = "place";
    public static final String LOST_OBJECTS_TABLE_FINDDATE = "findDate";
    public static final String LOST_OBJECTS_TABLE_USER = "usr";

    //Mapeo de metadatos de tabla "pictures"
    public static final String PICTURES_TABLE = "fcal_pictures";
    public static final String PICTURES_TABLE_ID = "id";
    public static final String PICTURES_TABLE_PICTURE = "picture";
    public static final String PICTURES_TABLE_LOST_OBJECT = "lostObject";
}
