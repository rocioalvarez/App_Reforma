package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SOMA-ROCIO on 06/03/2018.
 */

public class ComprobantePS extends AppCompatActivity{

    String referencia;
    String monto;
    String cuenta;
    String cataservicios;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pago_realizado);
        referencia = getIntent().getExtras().getString("referencia");
        monto = getIntent().getExtras().getString("deposito");
        cuenta = getIntent().getExtras().getString("cuenta");
        cataservicios = getIntent().getExtras().getString("cataservicios");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Referencia:                " + referencia + '\n'
                + "Servicio:                      " + cataservicios + '\n'
                + "Cuenta de retiro:       " + cuenta + '\n'
                + "Monto:                         $ " + monto + '\n' + '\n');


        Button btn_menu = (Button) findViewById(R.id.btn_regresar);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View menu) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void saveFrameLayout(FrameLayout frameLayout, String path) {
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.buildDrawingCache();
        Bitmap cache = frameLayout.getDrawingCache();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            cache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            frameLayout.destroyDrawingCache();
        }
    }
}

