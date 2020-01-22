/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                         Actividad para agregar publicación
:*
:*  Archivo     : AddPostActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que permite que el usuario agregue publicaciones, añadiendo información
:*                para la misma como descripción, lugar y fecha y, además, le da la posibilidad de
:*                adjuntar imágenes.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObject;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObjectDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.Picture;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.PictureDao;

public class AddPostActivity extends AppCompatActivity {

    //Campos para la publicación
    private EditText edDescr;
    private EditText edPlace;
    private EditText edDate;
    //Campos para capturar las imágenes adjuntas
    private LinearLayout llPictures;
    private List<Uri> attachedFiles;
    //Código para selección de archivo
    public static final int SELECTED_FILE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Obtenemos las referencias
        edDescr = findViewById(R.id.edDescr);
        edPlace = findViewById(R.id.edPlace);
        edDate = findViewById(R.id.edDate);
        llPictures = findViewById(R.id.llPictures);

        //Inicializamos el arreglo de archivos adjuntos
        attachedFiles = new ArrayList<>();

    }

    public void btnPublishClick(View view) {

        //Validamos la longitud de la cadena de entrada.
        if(edDate.getText().toString().length() != 10 ){
            edDate.setError("Ingrese una fecha con el formato dd/mm/aaaa");
            return;
        }

        //Tras pasar la valicación, obtenemos los valores y los almacenamos
        //en un objeto LostObject
        LostObject lo = new LostObject();
        lo.setDescr(edDescr.getText().toString());
        lo.setPlace(edPlace.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long ms = 0;
        try {
            ms = sdf.parse(edDate.getText().toString()).getTime();
        } catch (ParseException e) {
            edDate.setError("Ingrese una fecha con el formato dd/mm/aaaa");
            return;
        }
        lo.setFindDate(new Date(ms));
        //Le asignamos como usuario dueño de esta publicación, al usuario que está
        //en sesión.
        lo.setUsr(CommonData.getInstance().user);

        try {
            //Insertamos la publicación.
            LostObjectDao.getInstance().insert(lo);

            ByteArrayOutputStream baos = null;
            InputStream is = null;
            try{
                List<Picture> pics = new ArrayList<>();
                //Iteramos los archivos adjuntos y los convertimos
                //a arreglo de bytes, para poder insertarlos como BLOB en
                //la tabla de imágenes, los agregamos a una lista de Pictures.
                for(Uri u : attachedFiles){
                    is = getContentResolver().openInputStream(u);
                    baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int n;
                    while(-1 != (n = is.read(buffer))){
                        baos.write(buffer, 0, n);
                    }
                    try{
                        is.close();
                    }catch(Exception e){
                        Log.e("GuardarImagenes", e.getMessage());
                    }
                    Picture pic = new Picture();
                    pic.setLostObject(lo);
                    pic.setPicture(baos.toByteArray());
                    pics.add(pic);

                    try{
                        baos.close();
                    }catch(Exception e){

                    }
                }
                //Insertamos masivamente las imágenes
                PictureDao.getInstance().insertBulk(pics);
            }catch(Exception e){
                //Si hay error al guardar las imágenes, borramos la publicación, mandamos error y salimos.
                Toast.makeText(this, "Ocurrió un error al guardar las imágenes.", Toast.LENGTH_LONG).show();
                try{
                    LostObjectDao.getInstance().delete(lo);
                }catch(Exception e1){

                }
                return;
            }

            //Si tod0 salió bien, Mandamos alert dialog.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Publicación exitosa.")
                    .setMessage("La publicación se ha guardado con éxito.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AddPostActivity.this.finish();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
        } catch (Exception e) {
            //Si hay error desde la inserción de la publicación, mandamos el error.
            Toast.makeText(this, "Ocurrió un error al guardar la publicación.", Toast.LENGTH_LONG).show();
        }
    }

    public void btnAddPictureClick(View view){
        //Creamos el intent para abrir el explorador de archivos.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Agregamos el filtro para para que solo permita
        //abrir imágenes.
        intent.setType("image/*");
        //Agregamos la categoría de la aplicación, default.
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        //Tratamos de abrir el explorador de archivos. Si no se puede, lanzamos un toast
        //Con la excepción.
        try {
            startActivityForResult(Intent.createChooser(intent, "Seleccione una opción."), SELECTED_FILE_CODE);
        }catch(ActivityNotFoundException e){
            Toast.makeText(this, "Explorador de archivos no encontrado.\nPor favor instale un explorador de archivos.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Si el código de petición es SELECTED_FILE_CODE
        if(requestCode == SELECTED_FILE_CODE){
            //Si el código de resultado es igual a RESULT_OK
            if(resultCode == RESULT_OK){
                //Verificamos que el archivo no se encuentre adjuntado
                boolean isAttached = false;
                for(Uri u : attachedFiles){
                    if(u.getPath().equals(data.getData().getPath())){
                        isAttached = true;
                        break;
                    }
                }
                if(!isAttached) {
                    //Agregamos un nuevo hijo al layout que sea otro layout linear, horizontal,
                    //Con 2 views, uno que sea un textview para visualizar el nombre del archivo y el
                    //otro un boton para remover el adjunto que estará adentro de otro linear layout.
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setWeightSum(2f);

                    TextView textView = new TextView(this);
                    String[] pathSegments = data.getData().getPath().split("/");
                    String fileName = pathSegments[pathSegments.length - 1];
                    textView.setText(fileName);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f
                    ));


                    LinearLayout llButton = new LinearLayout(this);
                    llButton.setOrientation(LinearLayout.VERTICAL);
                    llButton.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    ));
                    llButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);

                    Button btnDelete = new Button(this);
                    btnDelete.setBackground(getDrawable(R.drawable.tacha));
                    btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                            50,
                            50
                    ));

                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = llPictures.indexOfChild((View) v.getParent().getParent());
                            attachedFiles.remove(index);
                            llPictures.removeView((View) v.getParent().getParent());
                        }
                    });

                    llButton.addView(btnDelete);

                    //Agregamos el botón y el textview al layout creado (horizontal).
                    //Esto simula la adición de un nuevo renglón a una tabla.
                    linearLayout.addView(textView);
                    linearLayout.addView(llButton);
                    //Agregamos el nuevo "renglón" al layout que lista dichos
                    //Elementos adjuntos.
                    llPictures.addView(linearLayout);
                    //Agregamos la ruta del archivo en la lista de archivos adjuntos.
                    attachedFiles.add(data.getData());
                }
            }
        }
    }
}
