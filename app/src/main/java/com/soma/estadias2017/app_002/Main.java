package com.soma.estadias2017.app_002;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.DocumentFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.itextpdf.text.Font;
import com.lowagie.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.Date;
import java.net.URL;


/**
 * Created by estadias2017 on 2/06/17.
 * Clase para crear PDF del comprobante PrincipalSpei
 */

public class Main  extends AppCompatActivity {

    String SOAP_ACTION = "";
    String METHOD_NAME = "comprobante";
    String NAMESPACE = "http://test/";
    String URL = "http://192.168.1.118:8080/WSAplicacionBanca/WSComprobanteSpei?WSDL";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/WSComprobanteSpei?WSDL";

    private static final String NOMBRE_CARPETA_APP = "com.soma.estadias2017.app_002";
    private static final String GENERADOS = "MisArchivos";

    private static Context mContext;

    public Button bt_generar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bt_generar = (Button) findViewById(R.id.bt_generaPDF);
        bt_generar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
            generarPDFOnClick();
        }
    });

    }

    public void generarPDFOnClick () {
        Document document = new Document(PageSize.LETTER);
        String NOMBRE_ARCHIVO = "Comprobante.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);

        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
        if (!pdfSubDir.exists()) {

            pdfSubDir.mkdir();
        }

        String nombre_completo = Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_APP
                + File.separator + GENERADOS + File.separator + NOMBRE_ARCHIVO;

        File outputfile = new File(nombre_completo);
        if (outputfile.exists()) {

            outputfile.delete();
        }


        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));

            /*Crear documento para escribirlo*/
            document.open();
            document.addAuthor("Operadora de Recursos Reforma");
            document.addCreator("Aplicación Móvil Banca Electrónica");
            document.addSubject("Pdf");
            //document.addCreationDate();
            document.addTitle("Comprobante de Operación-Transferencia SPEI");

            //LOGOTIPO
            InputStream inputStream = Main.this.getAssets().open("reforma.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleAbsolute(200f, 100f);
            imagen.setAbsolutePosition(30, 670);
            document.add(imagen);


            //PARRAFO DE TITULO ALINEADO A LA SUPERIOR DERECHA
            Paragraph parrafotitulo = new Paragraph("Comprobante de Operación-Transferencia SPEI" +
                    " OPERADORA DE RECURSOS REFORMA SA DE CV SFP" +
                    " RFC ORR920530QL5" );
            parrafotitulo.setAlignment(Element.ALIGN_TOP);
            parrafotitulo.setIndentationLeft(280);
            imagen.setAbsolutePosition(150, 660);
            document.add(parrafotitulo);

            Paragraph saltolinea1 = new Paragraph();
            saltolinea1.add("\n\n");
            document.add(saltolinea1);

            Paragraph parrafofecha = new Paragraph(getFecha());
            parrafofecha.setAlignment(Element.ALIGN_LEFT);
            document.add(parrafofecha);

            Paragraph saltolinea2 = new Paragraph();
            saltolinea2.add("\n");
            document.add(saltolinea2);
            document.add(Tabla_Ordenante());

            Paragraph saltolinea3 = new Paragraph();
            saltolinea3.add("\n");
            document.add(saltolinea3);
            document.add(Tabla_Beneficiario());

            Paragraph saltolinea4 = new Paragraph();
            saltolinea4.add("\n");
            document.add(saltolinea4);
            document.add(Tabla_DatosG());

            Paragraph saltolinea5 = new Paragraph();
            saltolinea5.add("\n");
            document.add(saltolinea5);
            document.add(Tabla_Costo());

            Paragraph saltolinea6 = new Paragraph();
            saltolinea6.add("\n\n\n\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n");
            document.add(saltolinea6);
            document.add(Tabla_Terminos());

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmlToPDF = "<html><head></head><body><h4></h4><br/><p></p><p></p><br/>" +
                    "</body></html>";

            try {
                worker.parseXHtml(pdfWriter, document, new StringReader(htmlToPDF));
                document.close();
                Toast.makeText(this, "PDF Generado", Toast.LENGTH_LONG).show();
                muestraPDF(nombre_completo,this);

            }catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PdfPTable Tabla_Ordenante() throws DocumentException {
        //creamos una tabla con 3 columnas
        PdfPTable tabla=new PdfPTable(2);
        PdfPCell celda =new PdfPCell (new Paragraph("Datos del Ordenante"));
        //unimos esta celda con otras 2
        celda.setColspan(2);
        //alineamos el contenido al centro
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        // añadimos un espaciado
        celda.setPadding (3f);
        //se añade a la tabla
        tabla.addCell(celda);
        //celda.getPaddingRight();
        //Porcentaje de la tabla
        tabla.setWidthPercentage(50);
        //Tamaño de columnas (aqui son 2 columnas)
        float[] headerWidths = {80, 40};
        tabla.setWidths(headerWidths);
        tabla.setHorizontalAlignment(Element.ALIGN_LEFT);

        tabla.addCell("Nombre del Ordenante");
        tabla.addCell ("Aprobado");

        tabla.addCell("Cuenta/CLABE Ordenante");
        tabla.addCell("Reprobado");

        tabla.addCell("RFC del Ordenante");
        tabla.addCell("Eximido");

        return tabla;

    }

    public PdfPTable Tabla_Beneficiario() throws DocumentException {
        //creamos una tabla con 3 columnas
        PdfPTable tabla2=new PdfPTable(2);
        PdfPCell celda =new PdfPCell (new Paragraph("Datos del Beneficiario"));
        //unimos esta celda con otras 2
        celda.setColspan(2);
        //alineamos el contenido al centro
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        // añadimos un espaciado
        celda.setPadding (3f);
        //se añade a la tabla
        tabla2.addCell(celda);
        //Porcentaje de la tabla
        tabla2.setWidthPercentage(50);
        //Tamaño de columnas (aqui son 2 columnas)
        float[] headerWidths = {80, 40};
        tabla2.setWidths(headerWidths);
        tabla2.setHorizontalAlignment(Element.ALIGN_LEFT);

        tabla2.addCell("Nombre del Beneficiario");
        tabla2.addCell ("Aprobado");

        tabla2.addCell("Cuenta/CLABE Beneficiario");
        tabla2.addCell("Reprobado");

        tabla2.addCell("RFC del Beneficiario");
        tabla2.addCell("Eximido");

        return tabla2;

    }


    public PdfPTable Tabla_DatosG() throws DocumentException {
        //creamos una tabla con 3 columnas
        PdfPTable tabla3=new PdfPTable(2);
        PdfPCell celda =new PdfPCell (new Paragraph("Datos Generales"));
        //unimos esta celda con otras 2
        celda.setColspan(2);
        //alineamos el contenido al centro
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        // añadimos un espaciado
        celda.setPadding (3f);
        //se añade a la tabla
        tabla3.addCell(celda);
        //Porcentaje de la tabla
        tabla3.setWidthPercentage(50);
        //Tamaño de columnas (aqui son 2 columnas)
        float[] headerWidths = {80, 40};
        tabla3.setWidths(headerWidths);
        tabla3.setHorizontalAlignment(Element.ALIGN_LEFT);

        tabla3.addCell("Concepto del Pago");
        tabla3.addCell ("Aprobado");

        tabla3.addCell("Referencia Numérica");
        tabla3.addCell("Reprobado");

        tabla3.addCell("Clave de Rastreo");
        tabla3.addCell("Eximido");

        return tabla3;

    }


    public PdfPTable Tabla_Costo() throws DocumentException {
        //creamos una tabla con 3 columnas
        PdfPTable tabla4=new PdfPTable(2);
        PdfPCell celda =new PdfPCell (new Paragraph("Costo"));
        //unimos esta celda con otras 2
        celda.setColspan(2);
        //alineamos el contenido al centro
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        // añadimos un espaciado
        celda.setPadding (3f);
        //se añade a la tabla
        tabla4.addCell(celda);
        //Porcentaje de la tabla
        tabla4.setWidthPercentage(50);
        //Tamaño de columnas (aqui son 2 columnas)
        float[] headerWidths = {80, 40};
        tabla4.setWidths(headerWidths);
        tabla4.setHorizontalAlignment(Element.ALIGN_LEFT);

        tabla4.addCell("Importe a Transferir");
        tabla4.addCell ("Aprobado");

        tabla4.addCell("Comisión + IVA");
        tabla4.addCell("Reprobado");

        tabla4.addCell("Fecha");
        tabla4.addCell("Eximido");

        return tabla4;

    }


    public PdfPTable Tabla_Terminos() throws DocumentException {


        Font fuenteTerminos = new Font();
        fuenteTerminos.setSize(10);
        //creamos una tabla con 3 columnas
        PdfPTable tabla5=new PdfPTable(2);
        PdfPCell celda =new PdfPCell (new Paragraph("Operación realizada a través Banca Electrónica " +
                "del sistema de Operadora de Recursos Reforma SA de CV SFP.\n\n" +
                "Para el caso de alguna aclaración se podrá acudir directamente a la sucursal donde " +
                "se realizo la operación o referirse a la Unidad Especializada de Atención a " +
                "Usuarios (UNE) de la institución, con número telefónico 01 800 831 0858 y correo " +
                "electrónico une.reforma@reformasofipo.com; en un plazo no mayor a 45 días " +
                "naturales contados a partir de la ejecución de la presente operación.", fuenteTerminos));
        //unimos esta celda con otras 2
        celda.setColspan(2);
        //alineamos el contenido al centro
        celda.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        // añadimos un espaciado
        celda.setPadding (3f);
        //se añade a la tabla
        tabla5.addCell(celda);
        //Porcentaje de la tabla
        tabla5.setWidthPercentage(100);

        return tabla5;

    }


    private void muestraPDF(String archivo, Context context) {
        Toast.makeText(context, "Leyendo el archivo", Toast.LENGTH_LONG).show();
        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){

            Toast.makeText(context, "No tienes una app para abrir este tipo de archivos",
                    Toast.LENGTH_LONG).show();
        }
    }

    private String getFecha(){

            String[] this_month = new String[12];
            this_month[0] = "Enero";
            this_month[1] = "Febrero";
            this_month[2] = "Marzo";
            this_month[3] = "Abril";
            this_month[4] = "Mayo";
            this_month[5] = "Junio";
            this_month[6] = "Julio";
            this_month[7] = "Agosto";
            this_month[8] = "Septiembre";
            this_month[9] = "Octubre";
            this_month[10] = "Noviembre";
            this_month[11] = "Diciembre";
            String[] this_day_e = new String[7];
            this_day_e[0] = "Domingo";
            this_day_e[1] = "Lunes";
            this_day_e[2] = "Martes";
            this_day_e[3] = "Miércoles";
            this_day_e[4] = "Jueves";
            this_day_e[5] = "Viernes";
            this_day_e[6] = "Sábado";

            Date today = new Date();
            byte day = (byte) today.getDate();
            byte month = (byte) today.getMonth();
            int year = today.getYear();
            byte dia = (byte) today.getDay();
            if (year < 1000) {
                year += 1900;
            }
            int hour = today.getHours() < 10 ? '0' + today.getHours() : today.getHours();
            int minute = today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes();
            int seconds = today.getSeconds() < 10 ? '0' + today.getSeconds() : today.getSeconds();
            return(" " + this_day_e[dia] + " " + day + " de " + this_month[month] + " de " + year +
                    ", " + hour + ":" + minute + ":" + seconds + " Centro de México");

    }


}
