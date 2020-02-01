package com.soma.estadias2017.app_002;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SOMA-ROCIO on 03/10/2017.
 */

public class FileChooser extends ListActivity {
    private File direccion;
    private FileArrayAdapter adapter;

    //Lanza la vista al momento de dar clik en la imagenBoton para seleccionar el archivo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        direccion = new File("/Documents"); //Direccionamiento
        fill(direccion);
    }

    //Lee el nombre del archivo
    public String leer(String nombre){

        try{
            File f;
            FileReader lectorArchivo;
            //nombre del archivo /sdcard/Download/file.txt
            f = new File(nombre);
            lectorArchivo = new FileReader(f);

            //BufferedReader sirve para leer el contenido el archivo que se acaebe de selecionar
            BufferedReader br = new BufferedReader(lectorArchivo);

            String l="";
            String aux="";

            while(true)
            {
                aux=br.readLine();
                if(aux!=null)
                    l=l+aux+" ";
                else
                    break;
            }

            br.close();
            lectorArchivo.close();
            return l;

        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
        return null;
    }

    //Se comienza a buscar en la sdcard
    private void fill(File f){

        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: "+f.getName());
        List<Opcion> dir = new ArrayList<Opcion>();
        List<Opcion>fls = new ArrayList<Opcion>();

        // busca en "/sdcard/.android_secure"
        // Recorre hasta el if comenzando a buscar carpeta por carpeta
        try{
            for(File ff: dirs) // ff: "/sdcard/.android_secure" dirs:File[12]@3820
            {
                if(ff.isDirectory())   // "/sdcard/LOST.DIR"
                    dir.add(new Opcion(ff.getName(),"Folder",ff.getAbsolutePath()));
                else
                {
                    fls.add(new Opcion(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
                }
            }
        }catch(Exception e)
        {
//Conecta directorios
        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Opcion("..","Parent Directory",f.getParent()));

        adapter = new FileArrayAdapter(FileChooser.this,R.layout.activity_file_chooser,dir);
        this.setListAdapter(adapter);}


    //Fncion al presionar el archivo que se va a elegir para que lo carge
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        //Muestra la posicion del archivo
        Opcion o = adapter.getItem(position);
        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
            direccion = new File(o.getPath());
            fill(direccion);

        }
        //  else if(){        }
        else
        {
            System.out.println(leer(o.getPath()));

            Intent intent2 = new Intent(FileChooser.this, CargarOrdenSpei.class);
            intent2.putExtra("filec", leer(o.getPath()));
            intent2.putExtra("name_filec", o.getName());
            //Se inicia la actividad abriendo de nuevo el achivo pricipal
            startActivity(intent2);

        }
    }
}