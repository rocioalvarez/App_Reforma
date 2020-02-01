package com.soma.estadias2017.app_002;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.csvreader.CsvReader;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;


/**
 * Created by SOMA-ROCIO on 12/08/2017.
 */

public class CatalogoOrden extends AppCompatActivity {

    public final List<String[]> readCsvC(Context context) {
        List<String[]> listaCSVC = new ArrayList<String[]>();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream csvStream = assetManager.open("/Download");
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            //  csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                listaCSVC.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaCSVC;
    }
}