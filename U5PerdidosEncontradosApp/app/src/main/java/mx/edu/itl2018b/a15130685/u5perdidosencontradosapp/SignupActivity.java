/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Actividad para registrar una cuenta.
:*
:*  Archivo     : SignupActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que sirve para registrar una cuenta nueva.
:*                Para registrar una cuenta nueva, tienes que agregar un nombre
:*                de usuario que no exista, una contaseña y un número de telefono.
:*
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.User;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.user.UserDao;

public class SignupActivity extends AppCompatActivity {

    //Campos para registrarse
    private EditText edUser;
    private EditText edPass;
    private EditText edPhone;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Obtenemos las referencias
        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
        edPhone = findViewById(R.id.edPhone);

    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnSignupClick(View view){
        //Verificamos que los campos no estén vacios
        boolean fieldsOk = true;
        if(edUser.getText() == null || edUser.getText().toString().trim().equals("")){
            edUser.setError("Especifique el usuario.");
            fieldsOk = false;
        }
        if(edPass.getText() == null || edPass.getText().toString().trim().equals("")){
            edPass.setError("Especifique la contraseña.");
            fieldsOk = false;
        }

        //Si hay un error en los campos, termina
        if(!fieldsOk){
            return;
        }

        //Guardamos los datos del layout en un objeto usuario.
        User u = new User();
        u.setUserName(edUser.getText().toString());
        u.setPass(edPass.getText().toString());
        u.setPhoneNumber(edPhone.getText().toString());

        //Verificamos que el nombre de usuario este disponible
        User udb = new User();
        udb.setUserName(u.getUserName());

        try{
            udb = UserDao.getInstance().getRecord(udb);
        }catch(Exception e){
            Toast.makeText(this, "Ocurrió un error de conexión.", Toast.LENGTH_LONG).show();
        }

        //Si el usuario existe, mandamos error
        if(udb.getId() != null){
            edUser.setError("El nombre de usuario ya existe.");
        }
        //si no existe, lo insertamos.
        else{
            try{
                UserDao.getInstance().insert(u);
                //Tod0 bien, notificamos al usuario y concluimos el activity
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Usuario registrado.")
                        .setMessage("El usuario se ha registrado con éxito!")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SignupActivity.this.finish();
                            }
                        }).create().show();
            }catch(Exception e){
                Toast.makeText(this, "Ocurrió un error al registrar al usuario.", Toast.LENGTH_LONG).show();
            }
        }
    }
    //----------------------------------------------------------------------------------------------

}
