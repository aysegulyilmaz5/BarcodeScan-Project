package com.aysegulyilmaz.barkodproje;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_scan;
    Button btn_about;
    Button btn_about_product;
    TextView textView;
    private TextView barcodeTV,titleTV,categoryTV,manufacturerTV,brandTV,formatTV,descTV;
    private ImageView icon;
    private String barcode_number;
    private RequestQueue myQueue;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        btn_scan = findViewById(R.id.btn_scan);
        btn_about = findViewById(R.id.btn_about);
        barcodeTV = findViewById(R.id.barkod_number);
        titleTV = findViewById(R.id.title);
        categoryTV = findViewById(R.id.category);
        manufacturerTV = findViewById(R.id.manufacturer);
        brandTV = findViewById(R.id.brand);
        formatTV = findViewById(R.id.format);
        descTV = findViewById(R.id.description);
        icon = findViewById(R.id.imageView);


    }

    public void btn_scan(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();


    }

    public void btn_about(View view){
        Intent i_about = new Intent(this,About_Activity.class);
        startActivity(i_about);


    }

    public void btn_product(View view) {
        Intent i_product = new Intent(this, Product_Activity.class);
        startActivity(i_product);
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(getBaseContext(),"Cancelled",Toast.LENGTH_LONG).show();

            }else{
                barcodeTV.setText(result.getContents());
                getBarcodeInfo(result.getContents());


            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void getBarcodeInfo(String barcode_number){
        // String barcode_number = edtBarcode.getText().toString();
        String url = "https://api.barcodelookup.com/v3/products?barcode=3337872411083&formatted=y&key=mq1ilefkzlx19w9p7hzjvrcpu4rhzt";
        //String url = "https://api.barcodelookup.com/v3/products?barcode="+barcode_number+"&formatted=y&key=mq1ilefkzlx19w9p7hzjvrcpu4rhzt";
        RequestQueue requestqueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response) {
                try{
                    JSONArray products = response.getJSONArray("products");
                    JSONObject product =(JSONObject) products.get(0);
                    String barkod = product.getString("barcode_number");
                    barcodeTV.setText("Barcode Number:"+barkod);
                    String title = product.getString("title");
                    titleTV.setText("Title:"+title);
                    String category= product.getString("category");
                    categoryTV.setText("Category:"+category);
                    String manufactur = product.getString("manufacturer");
                    manufacturerTV.setText("Manufacturer:"+manufactur);
                    String brand= product.getString("brand");
                    brandTV.setText("Brand"+brand);
                    String format = product.getString("format");
                    formatTV.setText("Format:"+format);
                    String desc = product.getString("description");
                    descTV.setText("Description"+desc);

                    JSONArray images = response.getJSONArray("images");
                    JSONObject image = (JSONObject) images.get(0);
                    String imageicon = image.getString("images");
                    Picasso.get().load("http:".concat(imageicon)).into(icon);


                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestqueue.add(jsonObjectRequest);

    }


}

