/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       PROGRAMACION MOVILES EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2018    HORA: 10-11 HRS
:*
:*                       Actividad para inspeccionar publicaciones.
:*
:*  Archivo     : InspectPostActivity.java
:*  Autor       : Fernando Alfonso Caldera Olivas     15130685
:*                Jesús Iván Sifuentes Valle          15131438
:*  Fecha       : 13/Dic/2018
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Actividad que permite inspeccionar una publicación, mostrando los datos de contacto
:*                y su galería.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl2018b.a15130685.u5perdidosencontradosapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.Picture;
import mx.edu.itl2018b.a15130685.u5perdidosencontradosapp.picture.PictureDao;

public class InspectPostActivity extends AppCompatActivity {

    //Campos que visualizan los datos de la publicación
    private TextView tvwDescr;
    private TextView tvwPlace;
    private TextView tvwDate;
    //Campo que visualiza el dato de usuario
    private TextView tvwPhone;
    //Linear layout de las imágenes en el carrucel (Horizontal Scroll View)
    private LinearLayout llPictures;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_post);

        //Obtenemos las referencias
        tvwDescr = findViewById(R.id.tvwDescr);
        tvwPlace = findViewById(R.id.tvwPlace);
        tvwDate = findViewById(R.id.tvwDate);
        llPictures = findViewById(R.id.llPictures);
        tvwPhone = findViewById(R.id.tvwPhone);

        //Establecemos los valores que están en memoria
        tvwDescr.setText(CommonData.getInstance().lostObject.getDescr());
        tvwPlace.setText(CommonData.getInstance().lostObject.getPlace());
        tvwDate.setText(CommonData.getInstance().lostObject.getFindDate().toString());
        tvwPhone.setText(CommonData.getInstance().lostObject.getUsr().getPhoneNumber());

        //Obtenemos las imágenes de la publicación y las insertamos
        //En el scrollablehorizontal.
        try{
            Picture pi = new Picture();
            pi.setLostObject(CommonData.getInstance().lostObject);
            List<Picture> list = PictureDao.getInstance().getRecords(pi);
            llPictures.removeAllViews();
            llPictures.setWeightSum(list.size());
            for(Picture pic : list){
                ImageView imgView = new ImageView(this);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic.getPicture(), 0, pic.getPicture().length);
                imgView.setImageBitmap(bitmap);
                llPictures.addView(imgView);
            }
        }catch(Exception e){
            Toast.makeText(this, "Ocurrió un error al recuperar las imágenes.", Toast.LENGTH_LONG).show();
        }

    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    public void btnCallClick(View view){
        //Creamos un intent y realizamos una llamada.
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CommonData.getInstance().lostObject.getUsr().getPhoneNumber()));
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

}
