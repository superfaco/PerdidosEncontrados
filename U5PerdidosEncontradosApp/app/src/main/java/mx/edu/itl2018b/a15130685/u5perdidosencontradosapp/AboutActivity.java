/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                                Actividad Acerca de...
:*
:*  Archivo     : AboutActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que muestra información acerca de los autores de la aplicación, donde
:*                estudiaron, bajo que materia hiciero la app y el nombre del catedrático.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    //----------------------------------------------------------------------------------------------
}
