/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Actividad para iniciar sesión
:*
:*  Archivo     : LoginActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que permite iniciar sesión en una cuenta registrada.
:*                Lo anterior lo realiza buscando si existe el usuario y comparando las
:*                contraseñas. Si el usuario y contraseña son correctos, se guarda en sesión
:*                el usuario.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.User;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.UserDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Encrypter;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.utils.Server;

public class LoginActivity extends AppCompatActivity {

    //Campos de logeo.
    private EditText edUser;
    private EditText edPass;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Obtenemos la referencia de los edits
        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnLoginClick(View view){

        //Verificamos que los campos no estén vacios.
        boolean fieldsOk = true;
        if(edUser.getText() == null || edUser.getText().toString().trim().equals("")){
            edUser.setError("Especifique el usuario.");
            fieldsOk = false;
        }
        if(edPass.getText() == null || edPass.getText().toString().trim().equals("")){
            edPass.setError("Especifique la contraseña.");
            fieldsOk = false;
        }

        //Si hay un campo que está vacío, detenemos la ejecución.
        if(!fieldsOk){
            return;
        }

        //Obtenemos usuario de la base de datos
        User u = new User();
        u.setUserName(edUser.getText().toString());
        u.setPass(Encrypter.encrypt(edPass.getText().toString()));

        User udb = new User();
        udb.setUserName(u.getUserName());
        try{
            udb = UserDao.getInstance().getRecord(udb);
        }catch(Exception e){
            Toast.makeText(this, "Ocurrió un error al recuperar los datos del usuario.", Toast.LENGTH_LONG).show();
        }

        //Verificamos que el usuario exista, si no, mandamos error.
        if(udb.getId() == null){
            Toast.makeText(this, "El usuario o la contraseña son incorrectos.", Toast.LENGTH_LONG).show();
            return;
        }

        //Los password no coinciden, mandamos error.
        if(!u.getPass().equals(udb.getPass())){
            Toast.makeText(this, "El usuario o la contraseña son incorrectos.", Toast.LENGTH_LONG).show();
            return;
        }

        //Tod0 está bien, continuamos con la siguiente actividad y guardamos
        //al usuario en sesión en memoria.
        CommonData.getInstance().user = udb;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnSignupClick(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnAboutClick(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

}
