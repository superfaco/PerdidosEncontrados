/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Actividad principal. Menú principal de la aplicación.
:*
:*  Archivo     : MainActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que sirve como punto de partida para las demás actividades.
:*                Este menú principal permite, desde aquí, agregar nuevas publicaciones, ver
:*                lo que has publicado y borrar tus publicaciones, buscar publicaciones usando
:*                diferentes filtros e inspeccionar las publicaciones.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObject;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.lost_object.LostObjectDao;

public class MainActivity extends AppCompatActivity {

    //Campos del layout.
    private TextView tvwUser;
    private LinearLayout llPosts;
    private EditText edDescr;
    private EditText edPlace;
    private EditText edStartDate;
    private EditText edEndDate;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos las referencias
        tvwUser = findViewById(R.id.tvwUser);
        llPosts = findViewById(R.id.llPosts);
        edDescr = findViewById(R.id.edDescr);
        edPlace = findViewById(R.id.edPlace);
        edStartDate = findViewById(R.id.edStartDate);
        edEndDate = findViewById(R.id.edEndDate);

        //Establecemos el nombre de usuario en sesión, para que se muestre.
        tvwUser.setText(CommonData.getInstance().user.getUserName());

        //Cargamos todos los registros (Se va a cambiar en el futuro para usar paginación.)
        try{
            List<LostObject> list = LostObjectDao.getInstance().getAllRecords();
            loadPosts(list);
        }catch(Exception e){
            Toast.makeText(this, "Ocurrió un error al obtener las publicaciones.", Toast.LENGTH_LONG).show();
        }

    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    private void loadPosts(List<LostObject> list){
        //Limpiamos la tabla
        llPosts.removeAllViews();

        //Ingresamos los renglones
        for(final LostObject lo : list){
            //Agregamos un linea layour horizontal, el cual va a funcionar como
            //un renglón, como si fueramos a llenar una tabla.
            //El renglón tendra 4 elementos.
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(4.0f);

            //Agregamos la fecha cuando se encontró
            TextView tvwDate = new TextView(this);
            tvwDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwDate.setText(lo.getFindDate().toString());

            //Agregamos la descripción del objecto
            TextView tvwDescr = new TextView(this);
            tvwDescr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwDescr.setText(lo.getDescr());

            //Agregamos el lugar donde se encontró.
            TextView tvwPlace = new TextView(this);
            tvwPlace.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            tvwPlace.setText(lo.getPlace());

            //Agregamos botón de inspeccionar
            //Agregamos este layout que va a contener el botón, esto para poder centrar
            //el botón y mandarlo al final (que esté hasta la orilla derecha).
            LinearLayout llButton = new LinearLayout(this);
            llButton.setOrientation(LinearLayout.VERTICAL);
            llButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            llButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            Button btnInspect = new Button(this);
            btnInspect.setLayoutParams(new LinearLayout.LayoutParams(50,
                    50));
            btnInspect.setBackground(getDrawable(R.drawable.lupa));
            btnInspect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Al hacer click, establece el lostObject en memoria como el lostObject
                    //de esta iteración.
                    CommonData.getInstance().lostObject = lo;
                    //Inicia la actividad de inspección.
                    Intent intent = new Intent(MainActivity.this, InspectPostActivity.class);
                    startActivity(intent);
                }
            });
            //Agregamos el botón en el layout que lo va a contener.
            llButton.addView(btnInspect);

            //Agregamos todos los elementos anteriores al "row"
            ll.addView(tvwDate);
            ll.addView(tvwDescr);
            ll.addView(tvwPlace);
            ll.addView(llButton);

            //Agregamos el nuevo row al LayoutPadre.
            llPosts.addView(ll);

        }
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnSearchClick(View view){
        //Validamos que si se busca por fecha, ambos campos de fecha estén llenos
        if(!edStartDate.getText().toString().equals("")){
            if(edEndDate.getText().toString().equals("")){
                edEndDate.setError("Para buscar por fecha, debe llenar ambos campos.");
                return;
            }
        }
        if(!edEndDate.getText().toString().equals("")){
            if(edStartDate.getText().toString().equals("")){
                edStartDate.setError("Para buscar por fecha, debe llenar ambos campos.");
            }
        }


        //Obtenemos los valores si pasan la validación
        String descr = edDescr.getText().toString();
        String place = edPlace.getText().toString();
        String startDate = edStartDate.getText().toString();
        String endDate = edEndDate.getText().toString();

        //Buscamos según los parámetros.
        try{
            List<LostObject> list = LostObjectDao.getInstance().getRecordsInRange(startDate, endDate, descr, place);
            loadPosts(list);
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Cargamos todos los registros (Se va a cambiar en el futuro por una implementación con paginación.)
        try{
            List<LostObject> list = LostObjectDao.getInstance().getAllRecords();
            loadPosts(list);
        }catch(Exception e){
            Toast.makeText(this, "Ocurrió un error al obtener las publicaciones.", Toast.LENGTH_LONG).show();
        }
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnExitClick(View view){
        CommonData.getInstance().user = null;
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnAddPostClick(View view){
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnMyPostsClick(View view){
        Intent intent = new Intent(this, MyPostsActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

}
