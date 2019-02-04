package com.company.zm.myandroidproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static final String BASE_URL = "http://10.0.2.2:8080/ExchangeServlet";
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView whoIsIn;
    private EditText txtAmount;
    private TextView errorMsg;
    private String currencyFrom;
    private String currencyTo;
    private double amount;
    private double result;
    private TextView txtResultAmount;
    private TextView txtResultCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        whoIsIn = findViewById(R.id.whoIsIn);
        txtAmount = findViewById(R.id.txtAmount);
        errorMsg = findViewById(R.id.errorsMsg);
        txtResultAmount = findViewById(R.id.ResultAmount);
        txtResultCurrency = findViewById(R.id.ResultCurrency);
        //get the username of user
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            String input = intent.getStringExtra(Intent.EXTRA_TEXT);
            whoIsIn.setText("  Hello " + input);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(this);
        spinnerFrom.setOnItemSelectedListener(this);
        if(this.getResources().getConfiguration().locale.getCountry() == "IL"){
            spinnerTo.setSelection(1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerFrom){
            String From = parent.getItemAtPosition(position).toString();
            currencyFrom = From;
        }

        if (parent.getId() == R.id.spinnerTo){
            String To = parent.getItemAtPosition(position).toString();
            currencyTo = To;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //HttpRequest
    @SuppressLint("StaticFieldLeak")
    public void btnHttpRequest(View view) {
        //checking if txt amount only numeric
        String amountAsString = txtAmount.getText().toString();
        if (amountAsString.isEmpty() || amountAsString == null){
            errorMsg.setText("please enter number");
        } else  {
            errorMsg.setText("");
            //making the exchanging
            amount = Double.valueOf(amountAsString);
            new AsyncTask<Void, Void, Double>(){

                @Override
                protected Double doInBackground(Void... voids) {
                    URL url = null;
                    InputStream inputStream = null;
                    HttpURLConnection connection = null;
                    try{
                        url = new URL(BASE_URL + "?currencyFrom=" + currencyFrom + "&currencyTo=" + currencyTo + "&amount=" + amount);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(false);
                        connection.setRequestMethod("GET");
                        connection.setUseCaches(false);
                        connection.connect();
                        inputStream = connection.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        int actuallyRead;
                        byte[] buffer = new byte[1024];
                        while ((actuallyRead = inputStream.read(buffer)) != -1){
                            stringBuilder.append(new String(buffer, 0, actuallyRead));
                        }
                        String response = stringBuilder.toString();
                        try{
                            JSONObject jsonExchangeResult = new JSONObject(response);
                            result = jsonExchangeResult.getDouble("exchangeResult");
                            return result;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Double aDouble) {
                    if (aDouble != null){
                        txtResultAmount.setText(String.valueOf(aDouble));
                        txtResultCurrency.setText(currencyTo);
                    }
                }

            }.execute();
        }
    }


    public void btnLogOut(View view) {
        finish();
    }
}
