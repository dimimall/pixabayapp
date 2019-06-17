package com.example.pixabayapp.Activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pixabayapp.Models.Images;
import com.example.pixabayapp.R;
import com.example.pixabayapp.adapters.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    private Spinner spinner;

    private List<Images> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("fashion");
        spinnerData.add("nature");
        spinnerData.add("backgrounds");
        spinnerData.add("science");
        spinnerData.add("education");
        spinnerData.add("people");
        spinnerData.add("feelings");
        spinnerData.add("religion");
        spinnerData.add("health");
        spinnerData.add("places");
        spinnerData.add("animals");
        spinnerData.add("industry");
        spinnerData.add("food");
        spinnerData.add("computer");
        spinnerData.add("sports");
        spinnerData.add("transportation");
        spinnerData.add("travel");
        spinnerData.add("buildings");
        spinnerData.add("business");
        spinnerData.add("music");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerData)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);

                // Return the view
                return view;
            }
        };

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StringRequest request = new StringRequest("https://pixabay.com/api/?key=12521885-c3db1e076f341010c636256f2&category="+spinner.getSelectedItem()+"&per_page=100", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        list = parseJsonData(string);
                        gridView.setAdapter(new ImageAdapter(list,MainActivity.this));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
                rQueue.add(request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,HomeScreenLockActivity.class);
                intent.putExtra("images", (Serializable) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    List<Images> parseJsonData(String jsonString) {
        List<Images> al = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray imageArray = object.getJSONArray("hits");
            for(int i = 0; i < imageArray.length(); ++i) {
                JSONObject imageObj = imageArray.getJSONObject(i);
                Images images = new Images(imageObj.getString("previewURL"),imageObj.getString("id"),imageObj.getString("webformatURL"));
                al.add(images);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return al;
    }

}
