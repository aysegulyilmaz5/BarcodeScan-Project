package com.aysegulyilmaz.barkodproje;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product_Activity extends AppCompatActivity {


    private RecyclerView RVProduct;
    private ArrayList<RVModal> RVModalArrayList;
    private RVAdapter RVAdapter;
    private String barcode_number;
    Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        btnBack =findViewById(R.id.btn_back);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String barcode = bundle.getString("Barcode");
        }


        RecyclerView recyclerView = findViewById(R.id.idRVProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RVModalArrayList = new ArrayList<>();
        RVAdapter = new RVAdapter(this, RVModalArrayList);
        recyclerView.setAdapter(RVAdapter);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(Product_Activity.this, LinearLayoutManager.HORIZONTAL, false);


        getProductInfo(barcode_number);
    }

    public void btn_back(View view){
        onBackPressed();

    }




    private void getProductInfo(String barcode_number){
        String url = "https://api.barcodelookup.com/v3/products?barcode=3337872411083&formatted=y&key=mq1ilefkzlx19w9p7hzjvrcpu4rhzt";

        RequestQueue requestqueue = Volley.newRequestQueue(Product_Activity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response) {
                try{
                    JSONArray products = response.getJSONArray("products");
                    JSONObject product =(JSONObject) products.get(0);

                    JSONArray storeArray = product.getJSONArray("stores");

                    for (int i = 0; i < storeArray.length(); i++) {
                        JSONObject storeObj = storeArray.getJSONObject(i);
                        String name = storeObj.getString("name");
                        String country = storeObj.getString("country");
                        String currency = storeObj.getString("currency");
                        String price = storeObj.getString("price");



                        RVModalArrayList.add(new RVModal(name, country, currency, price));
                    }
                    RVAdapter.notifyDataSetChanged();




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