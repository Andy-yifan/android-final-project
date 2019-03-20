package com.example.portfolio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> ProductAdapter;
    private int GETDATA_SUCCESS = 100;
    public RequestQueue requestQueue;
    private boolean option = false;
    ListView like_list;
    ArrayList<Info_holder> get_request_data = new ArrayList<Info_holder>();
    ArrayList<Info_holder> data = new ArrayList<Info_holder>();
    private Context context;
    ArrayAdapter like;
    private LocationManager locationmanager;
//    Handler mhandler = new Handler((Handler.Callback)(msg)->{
//        if (msg.what == GETDATA_SUCCESS){
//            ArrayList<Info_holder> data = msg.getData().getParcelableArrayList("data");
//           System.out.print(data.toString()+"\n");
//
//        }return false;
//    });
    ListView like_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        readLiked();
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    public  Context getContext() {
        return context;
    }
    protected void onResume() {
        super.onResume();
        readLiked();
    }
    public void general_search_listerner(View view) {
        String query = ((EditText) findViewById(R.id.editText)).getText().toString();
        if (option) {

            optionSearch();
        } else {
            simpleSearch();
            // readLiked();
        }
    }



    private void optionSearch() {

    }

    public void local_listerner(View view) {
        System.out.print("hahahahaha");
        localSearch();
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<Info_holder> result);
    }

    private void getdatafromServer(String url,final VolleyCallback callback){
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray establish_contents = response.getJSONArray("establishments");
                            ArrayList<Info_holder> displaylist  = new ArrayList<>();
                                for (int i = 0;i<establish_contents.length();i++){
                                    JSONObject CONTENT = establish_contents.getJSONObject(i);
//                                    String BusinessName = "Business name: "+CONTENT.getString("BusinessName")+"\n";
                                    String BusinessType = "BusinessType: "+CONTENT.getString("BusinessType")+"\n";
                                    String RatingValue = "RatingValue: "+CONTENT.getString("RatingValue")+"\n";
                                    String RatingDate ="RatingDate: "+ CONTENT.getString("RatingDate")+"\n";
                                    String PostCode = "PostCode: "+ CONTENT.getString("PostCode")+"\n";
                                    String BusinessName =CONTENT.getString("BusinessName")+"\n";
//                                    String BusinessType = CONTENT.getString("BusinessType")+"\n";
//                                    String RatingValue = CONTENT.getString("RatingValue")+"\n";
//                                    String RatingDate =CONTENT.getString("RatingDate")+"\n";
//                                    String PostCode = CONTENT.getString("PostCode")+"\n";
                                    Info_holder temp = new Info_holder(BusinessName,BusinessType,RatingValue,RatingDate,PostCode);
                                    displaylist.add(temp);
                                }
                                //passdata
                                callback.onSuccess(displaylist);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("Failed to get data");
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params  = new HashMap<String, String>();
                params.put("x-api-version", "2");
                return params;
            }

        };
        requestQueue.add(getRequest);
    }
    private void simpleSearch() {
        String input = ((TextView) findViewById(R.id.editText)).getText().toString();
        if(input.isEmpty()){
            Toast.makeText(this, "nothing input ", Toast.LENGTH_SHORT).show();
        }else{
            String rating = "Rating";
            String pagesize = "2";
            Holder_bulider holder_builder = new Holder_bulider();
            holder_builder.setSortOptionKey(rating);
            holder_builder.setPageSize(pagesize);
            holder_builder.setName(input);
            final String url = holder_builder.build().simple_url();
            //start decode the json data
            VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Info_holder> result) {
//                CustomAdapter_simple customAdapter = new CustomAdapter_simple(getContext(), R.layout.list_item,result);
//                shop_view.setAdapter(customAdapter);
                    Intent intent = new Intent(getContext(), Show_simple_search.class);
                    intent.putParcelableArrayListExtra("simple", result);
                    startActivity(intent);
                }
            };
            getdatafromServer(url,callback);
        }
    }

    private void localSearch() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
        Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String pagesize = "2";
        String sortoption = "distance";
        String longitued = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        //String longitued = "-1";
        //String latitude = "52.3";
        Holder_bulider holder_builder2 = new Holder_bulider();
        holder_builder2.setPageSize(pagesize);
        holder_builder2.setLongitude(longitued);
        holder_builder2.setLatitude(latitude);
        holder_builder2.setSortOptionKey(sortoption);
        final String url = holder_builder2.build().local_url();
        System.out.print(url+"\n");
        //start decode the json data
        VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Info_holder> result) {
//                CustomAdapter_simple customAdapter = new CustomAdapter_simple(getContext(), R.layout.list_item,result);
//                shop_view.setAdapter(customAdapter);
                System.out.print(result.toString()+"\n");
                Intent intent = new Intent(getContext(), Show_local_search.class);
                intent.putParcelableArrayListExtra("local", result);
                startActivity(intent);
            }
        };
        getdatafromServer(url,callback);
    }

    private void readLiked(){
        SharedPreferences pref = getSharedPreferences("Likedlist",MODE_PRIVATE);
        String name = pref.getString("BusinessName","");
        String info = pref.getString("BusinessInfo","");
        //System.out.print(name+"\n"+info+"\n");
        String[] like_data = {name,info};
        like_view = (ListView)findViewById(R.id.Like_listview);
        like = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, like_data);
        like_view.setAdapter(like);
    }
    public void reset_listerner(View view) {
        SharedPreferences pref = getSharedPreferences("Likedlist",MODE_PRIVATE);
        pref.edit().clear().commit();
//        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
