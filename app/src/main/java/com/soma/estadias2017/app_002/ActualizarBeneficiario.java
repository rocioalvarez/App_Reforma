package com.soma.estadias2017.app_002;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by estadias2017 on 4/03/17.
 */

public class ActualizarBeneficiario extends AppCompatActivity {

    public EditText beActivo,cuentaspei, estatusC, paccion, idcuenta, nombreBeneficiario, satRC;
    public TextView resultado;
    private Button actualizarb;
    Spinner id_beneficiario, tipoCuenta, catalogos, estatus;
    Toolbar toolbar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "ActualizarBene";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/ActualizarBeneficiario_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/ActualizarBeneficiario_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_act_beneficiario);
        resultado = (TextView) findViewById(R.id.resultado);
        nombreBeneficiario = (EditText) findViewById(R.id.nombreBeneficiario);
        satRC = (EditText) findViewById(R.id.satRC);
        cuentaspei = (EditText) findViewById(R.id.cuentaspei);
        estatusC = (EditText) findViewById(R.id.estatusC);
        paccion = (EditText) findViewById(R.id.paccion);
        idcuenta = (EditText) findViewById(R.id.idcuenta);
        actualizarb=(Button)findViewById(R.id.btn_act_bene);


        //Arreglo para llenar Spinner tipoCuenta
        TipoCuenta cuentas=new TipoCuenta();

        ArrayList<TCuenta> datos1 = cuentas.consumir2(1);
        final ArrayAdapter<TCuenta> adapter1  = new ArrayAdapter<TCuenta>(this,
                android.R.layout.simple_spinner_item, datos1);

        tipoCuenta = (Spinner)findViewById(R.id.tipoCuenta);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoCuenta.setAdapter(adapter1);

        //Arreglo2 para llenar Spinner catalogos
        TipoParticipante cuent=new TipoParticipante();

        ArrayList<TCuenta> data = cuent.consumir3(1);
        final ArrayAdapter<TCuenta> adapter2  = new ArrayAdapter<TCuenta>(this,
                android.R.layout.simple_spinner_item, data);

        catalogos = (Spinner)findViewById(R.id.catalogos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catalogos.setAdapter(adapter2);

        //Arreglo para llenar el combo de estatus
        CatalogoEstatus status = new CatalogoEstatus();

        ArrayList<CEstatus> datost = status.consumir2(1);
        final ArrayAdapter<CEstatus> adapter0 = new ArrayAdapter<CEstatus>(this,
                android.R.layout.simple_spinner_item, datost);

        estatus = (Spinner) findViewById(R.id.estatus);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estatus.setAdapter(adapter0);


        //Arreglo3 para llenar Spinner beneficiario
        BuscaBeneficiario Beneficiario = new BuscaBeneficiario();

        final ArrayList<Beneficiario> datos = Beneficiario.consumirBen(1);
        final ArrayAdapter<Beneficiario> adapter  = new ArrayAdapter<Beneficiario>(this,
                android.R.layout.simple_spinner_item, datos);

        id_beneficiario = (Spinner)findViewById(R.id.id_beneficiario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_beneficiario.setFocusable(true);
        id_beneficiario.setAdapter(adapter);

        id_beneficiario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
        int arg2, long arg3) {

             /*   String items=creditoid.getSelectedItem().toString();
                Log.i("Selected item : ",items);*/
                /*ArrayList<CreditoD> ldc = credito.consume (1);
                for(int i=0;i<ldc.size();i++){
                    total.setText(ldc.get(i).getTotal());
                }*/

            int id= (int) id_beneficiario.getSelectedItemId();

            int id_beneficiario=datos.get(id).getIdcliente();
            System.out.println("id beneficiario es "+ nombreBeneficiario);

            sps_beneficiario(id_beneficiario);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    });

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Mensaje que muestra al abrir el formulario
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Sigue los pasos para dar de Actualizar un Beneficiario",
                        Toast.LENGTH_SHORT);
        toast1.show();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Funcion del boton para actualizar
        actualizarb.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Beneficiario id_b = adapter.getItem(id_beneficiario.getSelectedItemPosition());
                        String nom_b = nombreBeneficiario.getText().toString();
                        String s_rf = satRC.getText().toString();
                        int beActivo =1;
                        TCuenta ti_cu = adapter1.getItem(tipoCuenta.getSelectedItemPosition());
                        TCuenta cata = adapter2.getItem(catalogos.getSelectedItemPosition());
                        String cu_sp = cuentaspei.getText().toString();
                        String id_c = idcuenta.getText().toString();
                        int estatusC = 1;
                        int paccion = 2;
                        CEstatus esta = adapter0.getItem(estatus.getSelectedItemPosition());

                        if(cu_sp.isEmpty()){
                            cuentaspei.setError("Campo vacio");
                            cuentaspei.requestFocus();

                        } else if(id_c.isEmpty()){
                            idcuenta.setError("Campo vacio");
                            idcuenta.requestFocus();

                        }else {

                            InvocarAc(id_b.getIdcliente(), nom_b, s_rf, beActivo, ti_cu.getCatalogos(),
                                    cata.getCatalogos(), cu_sp, estatusC, paccion, esta.getCatalogos());

                            nombreBeneficiario.setText("");
                            satRC.setText("");
                            tipoCuenta.setAdapter(adapter1);
                            catalogos.setAdapter(adapter2);
                            cuentaspei.setText("");
                            idcuenta.setText("");
                            estatus.setAdapter(adapter0);
                        }
                    }
                });
    }


    private void InvocarAc(int id_beneficiario, String nombreBeneficiario, String rfc, int beActivo,
                           String tipoCuenta, String catalogos, String cuentaspei, int estatusC,
                           int paccion, String estatus) {

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("numcliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());

            Request.addProperty("id_beneficiario", id_beneficiario);
            Request.addProperty("nombreBeneficiario", nombreBeneficiario);
            Request.addProperty("satRC", rfc);
            Request.addProperty("beActivo", beActivo);
            Request.addProperty("tipoCuenta", tipoCuenta);
            Request.addProperty("catalogos", catalogos);
            Request.addProperty("cuenta", cuentaspei);
            Request.addProperty("estatusC", estatusC);
            Request.addProperty("paccion", paccion);
            Request.addProperty("estatus", estatus);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(Request);

            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, envelope);

            SoapObject respuesta = (SoapObject) envelope.bodyIn;

            String r = respuesta.getProperty(1).toString();
            resultado.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void sps_beneficiario(int id_beneficiario){
        BuscaDBeneficiario n=new BuscaDBeneficiario();
        SoapObject bene = n.spsbeneficiario(id_beneficiario);

        for (int i = 0; i < bene.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    bene.getProperty(i);
            //nombreBeneficiario.setText("Rocio "+id_beneficiario);
            nombreBeneficiario.setText(result2.getProperty("nombre").toString());
            satRC.setText(result2.getProperty("rfc").toString());
            cuentaspei.setText(result2.getProperty("cuentaspei").toString());

        }
    }


}