/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Actividad para ver tus publicaciones.
:*
:*  Archivo     : MyPostsActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que sirve para ver las cosas que has publicado.
:*                Desde esta actividad, puedes borrar tus publicaciones, lo cual
:*                logra borrando primero las imágenes asociadas, si es que las hay,
:*                y luego borra la publicación.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObject;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObjectDao;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.Picture;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.PictureDao;

public class MyPostsActivity extends AppCompatActivity {

    //Tabla para agregar publicaciones.
    private LinearLayout llPosts;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        //Obtenemos las referencias
        llPosts = findViewById(R.id.llPosts);

        //Buscamos todas las publicaciones del usuario (Cambiará en el futuro por una implementación con paginación).
        try{
            List<LostObject> list = LostObjectDao.getInstance().getRecordsByUser(CommonData.getInstance().user);
            loadPosts(list);
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    private void loadPosts(List<LostObject> list){
        //Limpiamos la tabla.
        llPosts.removeAllViews();

        //Iteramos las publicaciones en la lista.
        for(final LostObject lo : list){
            //Creamos un linear layout que va a actuar como "row" de la tabla.
            //Contendrá 4 elementos.
            final LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.setWeightSum(4.0f);

            //Agregamos la fecha con que se registró la publicación.
            TextView tvwDate = new TextView(this);
            tvwDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwDate.setText(lo.getFindDate().toString());

            //Agregamos la descripción.
            TextView tvwDescr = new TextView(this);
            tvwDescr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwDescr.setText(lo.getDescr());

            //Agregamos el lugar.
            TextView tvwPlace = new TextView(this);
            tvwPlace.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwPlace.setText(lo.getPlace());

            //Agregamos el botón con la acción de eliminar publicación.
            //Lo encerramos dentro de un linear layout vertical, para
            //poder centrar el botón verticalmente y mandarlo al final
            //(orilla derecha).
            LinearLayout llButton = new LinearLayout(this);
            llButton.setOrientation(LinearLayout.VERTICAL);
            llButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            llButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            Button btnDelete = new Button(this);
            btnDelete.setLayoutParams(new LinearLayout.LayoutParams(50,
                    50));
            btnDelete.setBackground(getDrawable(R.drawable.tacha));
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        //Eliminamos todas las imágenes asociadas a la publicación.
                        Picture pic = new Picture();
                        pic.setLostObject(lo);
                        PictureDao.getInstance().delete(pic);
                        //Eliminamos la publicación en cuestión, la cual es la
                        //publicación de la iteración actual.
                        LostObjectDao.getInstance().delete(lo);
                        //Removemos el elemento actual (row actual) del layout,
                        //eliminandolo del padre.
                        llPosts.removeView(ll);
                        Toast.makeText(MyPostsActivity.this, "Publicación eliminada.", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        Toast.makeText(MyPostsActivity.this, "Ocurrió un error al eliminar la publicación.", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //Agregamos el botón a su layout contenedor.
            llButton.addView(btnDelete);

            //Agregamos tod0 lo demás al nuevo "row"
            ll.addView(tvwDate);
            ll.addView(tvwDescr);
            ll.addView(tvwPlace);
            ll.addView(llButton);

            //Agregamos el row a la "tabla".
            llPosts.addView(ll);

        }
    }
    //----------------------------------------------------------------------------------------------

}
