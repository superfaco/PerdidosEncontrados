/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Encriptador de cadenas
:*
:*  Archivo     : Encrypter.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Encriptador propio de cadenas. Se usa para encriptar las contraseñas
:*                de los usuarios antes de insertarlos en la tabla.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils;

public final class Encrypter {
    //Implementación de clase estática.
    private Encrypter(){
    }

    //Método estático para encriptar.
    public static final String encrypt(String s){
        String encryptedString = "";
        for(int i = 0, j = s.length() - 1; i < s.length() / 2; i++, j--){
            encryptedString += (char)((s.charAt(i) ^ Integer.MAX_VALUE) % 256);
            encryptedString += (char)((s.charAt(j) ^ Integer.MAX_VALUE) % 256);
        }
        return encryptedString;
    }

}
