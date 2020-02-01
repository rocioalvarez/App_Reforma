package com.soma.estadias2017.app_002;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOMA-ROCIO on 29/08/2017.
 */

public class ListaFiles extends Activity {

    private List<String> item = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // para quitar titulo
        setContentView(R.layout.lista_files);

        item = new ArrayList<String>();

        File f = new File(Environment.getExternalStorageDirectory() + "/Download");
        File[] files = f.listFiles();

        for (int i = 0; i < files.length; i++)

        {
            File file = files[i];

            if (file.isDirectory())

                item.add(file.getName() + "/");

            else
                item.add(file.getName());
        }


        //Mostramos la ruta en el layout
        TextView ruta = (TextView) findViewById(R.id.ruta);
        ruta.setText(Environment.getExternalStorageDirectory() + "/Download");

        //Localizamos y llenamos la lista
        ListView lstOpciones = (ListView) findViewById(R.id.listaFiles);
        ArrayAdapter<String> fileList = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, item);
        lstOpciones.setAdapter(fileList);

        // Accion para realizar al pulsar sobre la lista
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                // Devuelvo los datos a la activity principal
                Intent data = new Intent();
                data.putExtra("filename", item.get(position));
                setResult(RESULT_OK, data);
                finish();

            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
