package com.example.sinki.bai60_soap_phan1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sinki.config.Configuration;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText txtFromUnit,txtToUnit,txtTemp;
    Button btnChange;
    TextView txtResult;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvents();
    }

    private void addEvents() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChange();
            }
        });
    }

    private void xuLyChange() {
        String temp = txtTemp.getText().toString();
        String from = txtFromUnit.getText().toString();
        String to = txtToUnit.getText().toString();

        ConvertTemperatureTask task = new ConvertTemperatureTask();
        task.execute(temp,from,to);
    }

    private void addControl() {
        txtFromUnit = (EditText) findViewById(R.id.txtFromUnit);
        txtToUnit = (EditText) findViewById(R.id.txtToUnit);
        txtTemp = (EditText) findViewById(R.id.txtTemp);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnChange = (Button) findViewById(R.id.btnChange);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private class ConvertTemperatureTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtResult.setText(s);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                String temp = strings[0];
                String fromUnit = strings[1];
                String toUnit = strings[2];

                SoapObject requets = new SoapObject(Configuration.NAME_SPACE,Configuration.METHOD_CONVERT);
                requets.addProperty(Configuration.PARAM_TEMP,temp);
                requets.addProperty(Configuration.PARAM_FROM,fromUnit);
                requets.addProperty(Configuration.PARAM_TO,toUnit);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(requets);

                HttpTransportSE httpTransportSE = new HttpTransportSE(Configuration.SERVER_URL);
                httpTransportSE.call(Configuration.SOAP_ACTION,envelope);
                SoapPrimitive data = (SoapPrimitive) envelope.getResponse();
                return data.toString();
            }
            catch (Exception ex)
            {
                Log.e("Loi", ex.toString());
            }
            return null;
        }
    }
}
