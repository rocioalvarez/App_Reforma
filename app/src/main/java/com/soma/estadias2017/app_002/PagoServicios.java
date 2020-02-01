package com.soma.estadias2017.app_002;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.util.ArrayList;
import static android.R.layout.simple_spinner_item;

/**
 * Created by SOMA-ROCIO on 22/10/2017.
 * Funcion: Realiza Pago de Servicios
 */

public class PagoServicios extends AppCompatActivity{

    public TextView deposito, retiro, serviciop, notelefono, codigo, usuario, folio, autorizacion,
            fecha, codigorespuesta, respuesta, ncajero, referencia2, cliente;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    public EditText referencia, comision, nombre, rfc, pmonto, cmonto, cvoperacion;
    public Spinner cataservicios, cuenta, idcliente;
    public Button btn_finalizar, btn_info;
    public TextView resultado;
    private TextInputLayout tilcvoperacion;
    ProgressDialog progressDialog;


    String SOAP_ACTION = "";
    String NAMESPACE = "http://webservices/";
    String METHOD_NAME = "pagar";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/PagoServicios_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/PagoServicios_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pago_servicios);
        referencia = (EditText) findViewById(R.id.referencia);
        comision = (EditText) findViewById(R.id.comision);
        nombre = (EditText) findViewById(R.id.nombre);
        rfc = (EditText) findViewById(R.id.rfc);
        pmonto = (EditText) findViewById(R.id.pmonto);
        cmonto = (EditText) findViewById(R.id.cmonto);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        deposito = (TextView) findViewById(R.id.deposito);
        retiro = (TextView) findViewById(R.id.retiro);
        serviciop = (TextView) findViewById(R.id.serviciop);
        notelefono = (TextView) findViewById(R.id.notelefono);
        codigo = (TextView) findViewById(R.id.codigo);
        usuario = (TextView) findViewById(R.id.usuario);
        folio = (TextView) findViewById(R.id.folio);
        autorizacion = (TextView) findViewById(R.id.autorizacion);
        fecha = (TextView) findViewById(R.id.fecha);
        codigorespuesta = (TextView) findViewById(R.id.codigorespuesta);
        ncajero = (TextView) findViewById(R.id.ncajero);
        referencia2 = (TextView) findViewById(R.id.referencia2);
        cliente = (TextView) findViewById(R.id.cliente);
        resultado = (TextView) findViewById(R.id.resultado);
        btn_finalizar = (Button) findViewById(R.id.finalizar);
        btn_info = (Button) findViewById(R.id.btn_info);


        respuesta = (TextView) findViewById(R.id.respuesta);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Politicas
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Catalogo de cuentas que llena el Spinner
        CatalogoCuentas cuentas = new CatalogoCuentas();
        final ArrayList<Cuenta> datos = cuentas.consumir2(2); //indicamos llenar con la opcion 2
        final ArrayAdapter<Cuenta> adapter = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        cuenta = (Spinner) findViewById(R.id.cuenta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);

        //Catalogo Servicios
        final CatalogoServicios servicios = new CatalogoServicios();
        final ArrayList<Servicios> data = servicios.consumir1(3);
        final ArrayAdapter<Servicios> adapter1 = new ArrayAdapter<Servicios>(this,
                android.R.layout.simple_spinner_item, data);

        cataservicios = (Spinner) findViewById(R.id.cataservicios);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cataservicios.setAdapter(adapter1);

        cataservicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                colocar(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //LLENA CAMPOS DE NOMBRE Y RFC DEL USUARIO
        CatalogoRfcCliente RfcCliente = new CatalogoRfcCliente();
        final ArrayList<RfcCliente> dato = RfcCliente.consum(1);
        final ArrayAdapter<RfcCliente> adapter7  = new ArrayAdapter<RfcCliente>(this,
                simple_spinner_item, dato);

        idcliente = (Spinner)findViewById(R.id.idcliente);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idcliente.setAdapter(adapter7);
        idcliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int id= (int) idcliente.getSelectedItemId();
                int idcliente=dato.get(id).getIdcliente();

                //Declaro la funcion
                sps_cliente(idcliente);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //Boton para guardar/finalizar la operacion
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usu = usuario.getText().toString();
                String m = pmonto.getText().toString();
                String cm = cmonto.getText().toString();
                Servicios s = adapter1.getItem(cataservicios.getSelectedItemPosition());
                String cv = cvoperacion.getText().toString();
                Cuenta c = adapter.getItem(cuenta.getSelectedItemPosition());
                String ref = referencia.getText().toString();
                String com = comision.getText().toString();
                String nom = nombre.getText().toString();
                String rf = rfc.getText().toString();
                String cli = cliente.getText().toString();
                String ret = cmonto.getText().toString();
                String serp = serviciop.getText().toString();
                String not = notelefono.getText().toString();
                String cod = codigo.getText().toString();
                String fol = folio.getText().toString();
                String aut = autorizacion.getText().toString();
                String fec = fecha.getText().toString();
                String cres = codigorespuesta.getText().toString();
                String res = respuesta.getText().toString();
                String nca = ncajero.getText().toString();
                String ref2 = referencia2.getText().toString();

                Invoke(m, ret, serp, not, ref, cod, m, usu, cli, c.getIdcuenta(), fol,
                        aut, com,  fec, s.getServicio(), cres, res, nca, ref2, cv);




                //if(validacionCampos(ref,m,cm,cv)){

                // Invoke(m, ret, serp, not, ref, cod, m, usu, cli, c.getIdcuenta(), fol,
                //       aut, com,  fec, s.getServicio(), cres, res, nca, ref2, cv);


                    Toast.makeText(PagoServicios.this, "Transaccion Exitosa", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (PagoServicios.this, ComprobantePS.class);
                    intent.putExtra("referencia", ref);
                    intent.putExtra("deposito", m);
                    intent.putExtra("cuenta", c.getIdcuenta());
                    intent.putExtra("cataservicios", s.getServicio());
                    startActivity(intent);




                }


        });



    Button btn_info = (Button) findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = getLayoutInflater();

            View dialoglayout = inflater.inflate(R.layout.vista_info_referencia, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(PagoServicios.this);
            builder.setView(dialoglayout);
            builder.show();
        }
    });
}



    private String[] Invoke(String pmonto, String cmonto, String serviciop, String notelefono, String referencia, String codigo,
                            String monto, String usuario,  String cliente,
                            String cuenta,  String folio, String autorizacion, String comision,
                            String fecha, String producto, String codigorespuesta, String respuesta, String ncajero,
                            String referencia2, String cvoperacion) {


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = false;

            envelope.setOutputSoapObject(Request);
            Request.addProperty("pmonto", pmonto);
            Request.addProperty("retiro", cmonto);
            Request.addProperty("serviciop", serviciop);
            Request.addProperty("notelefono", notelefono);
            Request.addProperty("referencia", referencia);
            Request.addProperty("codigo", codigo);
            Request.addProperty("monto", monto);
            Request.addProperty("usuario", usuario);
            Request.addProperty("cliente", cliente);
            Request.addProperty("cuenta", cuenta);
            Request.addProperty("folio", folio);
            Request.addProperty("autorizacion", autorizacion);
            Request.addProperty("comision", comision);
            Request.addProperty("fecha", fecha);
            Request.addProperty("producto", producto);
            Request.addProperty("codigorespuesta", codigorespuesta);
            Request.addProperty("respuesta", respuesta);
            Request.addProperty("ncajero", ncajero);
            Request.addProperty("referencia2", referencia2);
            Request.addProperty("numcliente", Globals.getCuenta());
            Request.addProperty("cvoperacion", codifica(cvoperacion));
            Request.addProperty("cuenta", cuenta);
            Request.addProperty("pmonto", monto);

            HttpTransportSE tra = new HttpTransportSE(URL);
            System.out.println("URL:     " + URL);
            tra.call(SOAP_ACTION, envelope);
            System.out.print(tra.getServiceConnection()); //imprime en consola la conexion

            SoapObject so = (SoapObject) envelope.bodyIn;


            String r = "";
            //String r1 = "";
            //String r2 = "";

            r = so.getProperty(1).toString();
            resultado.setText(r);
            //r1 = so.getProperty(1).toString();
            // r2 = so.getProperty(2).toString();

            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

            //String[] resultado = {r, r1};

            //return resultado;


        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private Boolean validacionCampos(String ref,String m,String cm, String cv){
        if (ref.isEmpty()) {
            referencia.setError("Campo vacio");
            referencia.requestFocus();
            return false;
        }
        if (m.isEmpty()) {
            pmonto.setError("Campo vacio");
            pmonto.requestFocus();
            return false;
        }
        if(cm.isEmpty()) {
            cmonto.setError("Campo vacio");
            cmonto.requestFocus();
            return false;
        }
        if(cv.isEmpty()) {
            cvoperacion.setError("Campo vacio");
            cvoperacion.requestFocus();
            return false;
        }
        return true;

    }

    String vcodigo ="0";
    private void colocar(ArrayList<Servicios> data) {

        int id= (int) cataservicios.getSelectedItemId();
        vcodigo = data.get(id).getSku();

        codigo.setText(String.valueOf(vcodigo));
        comision.setText(String.valueOf(data.get(id).getMonto()));

    }

    //Funcion para buscar el cliente por id y llenar los datos
    public void sps_cliente(int idcliente){
        CatalogoRfcCliente n=new CatalogoRfcCliente();
        SoapObject ref = n.invokeCatalog(idcliente);

        //Llena datos, nombre y rfc
        for (int i = 0; i < ref.getPropertyCount(); i++) {
            SoapObject result = (SoapObject)
                    ref.getProperty(i);
            nombre.setText(result.getProperty("nombre").toString());
            rfc.setText(result.getProperty("rfc").toString());
        }
    }


    public String codifica(String cadena) {
        //Se crea objeto de messageDigest que permite crear instancia hacia el tipo de encriptacion..
        MessageDigest messageDigest = null;

        //Se crea objeto de string buffer para almacenar la concatenacion de los datos del arreglo.
        StringBuffer sb = null;
        try {
            //Se inicializa el objeto y se le pasa el tipo de encriptacion deseada
            messageDigest = MessageDigest.getInstance("SHA-512");

            //Se extraen los bytes que contiene la cadena
            byte[] encripta = messageDigest.digest(cadena.getBytes());

            //Se inicializa un objeto tipo String buffer
            sb = new StringBuffer();

            //Se recorre el arreglo de bytes que genero messageDigest
            for (byte hashB : encripta) {
                // se concatena los datos obtenidos del arreglo
                sb.append(String.format("%02x", hashB));
            }

        } catch (Exception e) {
//            log.info(e.getMessage());
//            errorService.createError(e.getMessage(), "Error encriptando la cadena: "+ cadena);
        }

        return sb.toString();
    }

}




