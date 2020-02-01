package com.soma.estadias2017.app_002;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


import com.opencsv.CSVReader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by estadias2017 on 26/06/17.
 */

public class CargarOrdenSpei extends AppCompatActivity implements View.OnClickListener{

    /*
    private String SOAP_ACTION = "";
    private String METHOD_NAME = "nuevospei";
    private String NAMESPACE = "http://test/";
    private String URL = "http://192.168.1.162:8080/WSAplicacionBanca/Spei?wsdl";
    */

    Button abrir;
    //private Button ultimo;
    EditText csvArchivo;
    private List<String> item = null;
    Button leer;
    String conFile ="";
    String nomFile ="";
    Button siguiente;
    Toolbar toolbar;

    //Variable para seleccion intent
    private final int PICKER = 1;


    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.vista_cargar_orden_spei);

        if(getIntent().getExtras()!=null){
            conFile = getIntent().getExtras().getString("filec");
            nomFile = getIntent().getExtras().getString("name_filec");
        }

        abrir = (Button) findViewById(R.id.btnAbrir);
        abrir.setOnClickListener(this);

        csvArchivo = (EditText) findViewById(R.id.csvArchivo);


        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        siguiente = (Button) findViewById(R.id.siguiente);

//Implementamos el evento “click” del botón
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CargarOrdenSpei.this, CargaSpei.class);
                startActivity(intent);
            }
        });




        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi){
                Intent intent = new Intent(CargarOrdenSpei.this, FileChooser.class);
                startActivity(intent);
                //Abrir();
            }
        });

        leer = (Button) findViewById(R.id.leer);
        //Al presionar Boton
        leer.setOnClickListener(new View.OnClickListener() {
            @Override

            //Se lee el contenido del archivo de texto
            public void onClick(View v) {

                csvArchivo.setText(conFile);
            }
        });

        csvArchivo.setText(nomFile);
    }


/*
    public final List<String[]> readCsvC(Context context) {
        List<String[]> listaCSVC = new ArrayList<String[]>();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream csvStream = assetManager.open("/Download");
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                listaCSVC.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaCSVC;
    }
    */

    @Override
    public void onClick(View view) {
        // Al presionar el boton Seleccionar archivo nos lanzara el activity lista_files
        if (view.getId() == findViewById(R.id.btnAbrir).getId()) {
            Intent i = new Intent();
            i.setClass(this, FileChooser.class);
            startActivity(i);
            finish();

        }
    }
}


/*
    private void PickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        //intent.setType("*/ /*storage");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Instale un administrador de archivos"),
                    PICKER);

        }catch(android.content.ActivityNotFoundException ex){
                //Toast.makeText(this, "Por favor, instale un administrador de archivos", Toast.LENGTH_SHORT).show();
            }
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case PICKER:
                if (requestCode == RESULT_OK){
                    String FilePath = data.getData().getPath();
                    csvArchivo.setText(FilePath);
                }
                break;
        }
    }


    }
*/


// Al presionar el boton Abrir ultimo archivo nos ejecutara el metodo Abrir()
     /*   if (view.getId() == findViewById(R.id.btnAbrirU).getId()) {
            Abrir();

        }

    }

    /*
    private void Abrir(){

        item = new ArrayList<String>();

        File ruta_in = Environment.getExternalStorageDirectory();

        File f = new File(ruta_in.getAbsolutePath() + "/Download");
        File[] files = f.listFiles();

        for (int i = 0; i <files.length; i++)
        {

            File file = files[i];

            if (file.isDirectory()){

            }
            else {
                item.add(file.getName());
            }
        }

        File ff = new File(ruta_in.getAbsolutePath() + "/Download" + item.get(item.size()-1));


        System.out.println("Hasta aquí entro... ");


        try{
            BufferedReader br = new BufferedReader(new FileReader(ff));
            String linea = br.readLine();

            while (linea != null){
                linea = br.readLine();
        }

        }catch (Exception e){
        }
    }


//Funcion que recupera la vista del archivo que se esta seleccionando

    public void recuperar(View v) {
        String nomarchivo = csvArchivo.getText().toString();
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
        try {
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo=new InputStreamReader(fIn);
            BufferedReader br=new BufferedReader(archivo);
            String linea=br.readLine();
            String todo="";
            while (linea!=null)
            {
                todo=todo+linea+"\n";
                linea=br.readLine();
            }
            br.close();
            archivo.close();
            csvArchivo.setText(todo);

        } catch (IOException e)
        {
        }
    }

*/

//}



/*

        //Barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String cv = cvoperacion.getText().toString();

                InvocarEnviarSpei();

            }
        });

    }

}*/
