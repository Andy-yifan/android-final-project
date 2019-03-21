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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
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
    public RequestQueue requestQueue;
    private boolean adv = false;
    private boolean locationused = false;
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
    Holder_bulider holder_builder_adv = new Holder_bulider();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        readLiked();
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getBusinessTypes();
        getRegions();
        checkbox_listerner();
        setmile_spinner();
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
        if (adv) {
            advSearch();
        } else {
            simpleSearch();
            // readLiked();
        }
    }

    public void checkbox_listerner(){
    CheckBox business_cb = (CheckBox) findViewById(R.id.businesstype_cb);
        business_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Businesstype_holder option = (Businesstype_holder)((Spinner) findViewById(R.id.BusinessType_spinner)).getSelectedItem();
                    Toast.makeText(MainActivity.this, "You just checked ", Toast.LENGTH_SHORT).show();
                    adv = true;
                    holder_builder_adv.setBusinessTypeId(option.getBusinessId());
                }else{
                    Toast.makeText(MainActivity.this, "You just unchecked", Toast.LENGTH_SHORT).show();
                    adv=false;
                    holder_builder_adv.setBusinessTypeId(null);
                }
            }
        });
        //update cb when spinner is changed
        ((Spinner)findViewById(R.id.BusinessType_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((CheckBox) findViewById(R.id.businesstype_cb)).setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    CheckBox region_cb = (CheckBox) findViewById(R.id.Region_cb);
    region_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){

                LocalAuthority_holder option = (LocalAuthority_holder)((Spinner) findViewById(R.id.Authority_spinner)).getSelectedItem();
                Toast.makeText(MainActivity.this, "You just checked ", Toast.LENGTH_SHORT).show();
                adv = true;
                holder_builder_adv.setLocalAuthorityId(option.getId());
            }else{
                Toast.makeText(MainActivity.this, "You just unchecked", Toast.LENGTH_SHORT).show();
                adv = false;
                holder_builder_adv.setLocalAuthorityId(null);
            }
        }
    });
    CheckBox mile_cb = (CheckBox) findViewById(R.id.mile_cb);
    mile_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Toast.makeText(MainActivity.this, "You just checked", Toast.LENGTH_SHORT).show();
                String option = (String) ((Spinner) findViewById(R.id.mile_spinner)).getSelectedItem();
                holder_builder_adv.setMaxDistanceLimit(option.substring(0,2));
                adv = true;
                locationused = true;
            }else{
                Toast.makeText(MainActivity.this, "You just unchecked", Toast.LENGTH_SHORT).show();
                holder_builder_adv.setMaxDistanceLimit(null);
                adv = false;
                locationused = false;
            }
        }
    });
    CheckBox rate_cb = (CheckBox) findViewById(R.id.rate_cb);
    rate_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                Toast.makeText(MainActivity.this, "You just checked", Toast.LENGTH_SHORT).show();
                RatingBar option = findViewById(R.id.Rating_br);
                holder_builder_adv.setRatingKey(String.valueOf((int) option.getRating()));
                adv =true;
            }else{
                Toast.makeText(MainActivity.this, "You just unchecked", Toast.LENGTH_SHORT).show();
                adv = false;
                holder_builder_adv.setRatingKey(null);
            }
        }
    });
    }
    private void advSearch() {
        if(((CheckBox) findViewById(R.id.businesstype_cb)).isChecked()){
            Businesstype_holder option = (Businesstype_holder) ((Spinner) findViewById(R.id.BusinessType_spinner)).getSelectedItem();
            holder_builder_adv.setBusinessTypeId(option.getBusinessId());
        }
        if(((CheckBox) findViewById(R.id.Region_cb)).isChecked()){
            LocalAuthority_holder option = (LocalAuthority_holder)((Spinner) findViewById(R.id.Authority_spinner)).getSelectedItem();
            holder_builder_adv.setLocalAuthorityId(option.getId());
        }
        if(((CheckBox) findViewById(R.id.mile_cb)).isChecked()){
            String option = (String) ((Spinner) findViewById(R.id.mile_spinner)).getSelectedItem();
            holder_builder_adv.setMaxDistanceLimit(option.substring(0,2));
        }
        if(((CheckBox) findViewById(R.id.rate_cb)).isChecked()){
            RatingBar option = findViewById(R.id.Rating_br);
            holder_builder_adv.setRatingKey(String.valueOf((int) option.getRating()));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
        Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            String longitued = String.valueOf(location.getLongitude());
            String latitude = String.valueOf(location.getLatitude());
            String input = ((TextView) findViewById(R.id.editText)).getText().toString();
            if(input.isEmpty()){
                Toast.makeText(this, "nothing input ", Toast.LENGTH_SHORT).show();
            }else{
                String pagesize = "2";
                holder_builder_adv.setPageSize(pagesize);

                if(locationused){
                    holder_builder_adv.setLatitude(latitude);
                    holder_builder_adv.setLongitude(longitued);
                    holder_builder_adv.setName(input);
                }else{
                    holder_builder_adv.setName(input);
                    holder_builder_adv.setLatitude(null);
                    holder_builder_adv.setLongitude(null);
                }
                final String url = holder_builder_adv.build().simple_url();
                System.out.print(url+" adv\n");
                //start decode the json data
                VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Info_holder> result) {
        //                CustomAdapter_simple customAdapter = new CustomAdapter_simple(getContext(), R.layout.list_item,result);
        //                shop_view.setAdapter(customAdapter);
                          if (!result.isEmpty()){
                              Intent intent = new Intent(getContext(), Show_simple_search.class);
                              intent.putParcelableArrayListExtra("simple", result);
                              startActivity(intent);
                          }else{
                              Toast.makeText(MainActivity.this, "Result not found", Toast.LENGTH_SHORT).show();
                          }
                    }
                };
                getdatafromServer(url,callback);
            }
        }else{
            Toast.makeText(MainActivity.this, "no location data", Toast.LENGTH_SHORT).show();
        }
    }

    public void getBusinessTypes() {
        final String url = "http://api.ratings.food.gov.uk/BusinessTypes";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray Business_type_contents = response.getJSONArray("businessTypes");
                            ArrayList<Businesstype_holder> businesstype_holder = new ArrayList<>();
                            for (int i = 0; i < Business_type_contents.length(); i++) {
                                JSONObject CONTENT = Business_type_contents.getJSONObject(i);
                                String BusinessType = CONTENT.getString("BusinessTypeName");
                                String BusinessId = CONTENT.getString("BusinessTypeId");
                                Businesstype_holder temp = new Businesstype_holder(BusinessType,BusinessId);

                                businesstype_holder.add(temp);
                                ArrayAdapter<Businesstype_holder> adapter = new ArrayAdapter<>(
                                        getApplicationContext(), android.R.layout.simple_spinner_item, businesstype_holder);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                ((Spinner) findViewById(R.id.BusinessType_spinner)).setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("Failed to get Business Types");
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

    public void getRegions() {
        final String url = "http://api.ratings.food.gov.uk/Regions";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contents = response.getJSONArray("regions");
                            ArrayList<Region_holder> region_holder = new ArrayList<>();
                            for (int i = 0; i < contents.length(); i++) {
                                JSONObject CONTENT = contents.getJSONObject(i);
                                String name = CONTENT.getString("name");
                                String nameKey = CONTENT.getString("nameKey");
                                String id = CONTENT.getString("id");
                                Region_holder temp = new Region_holder(name,nameKey,id);

                                region_holder.add(temp);
                                ArrayAdapter<Region_holder> adapter = new ArrayAdapter<>(
                                        getApplicationContext(), android.R.layout.simple_spinner_item, region_holder);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                ((Spinner) findViewById(R.id.RegionType_spinner)).setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("Failed to get Region Types");
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
        ((Spinner) findViewById(R.id.RegionType_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getLocalAuthority(((Region_holder) parent.getSelectedItem()).getKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getLocalAuthority(String nameKey) {
        final String url = "http://api.ratings.food.gov.uk/Authorities";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contents = response.getJSONArray("authorities");
                            ArrayList<LocalAuthority_holder> localAuthority_holder = new ArrayList<>();
                            for (int i = 0; i < contents.length(); i++) {
                                JSONObject CONTENT = contents.getJSONObject(i);
                                if(CONTENT.getString("RegionName").equals(nameKey)){
                                    String name = CONTENT.getString("Name");
                                    String id = CONTENT.getString("LocalAuthorityId");
                                    LocalAuthority_holder temp = new LocalAuthority_holder(name,id);
                                    localAuthority_holder.add(temp);
                                    ArrayAdapter<LocalAuthority_holder> adapter = new ArrayAdapter<>(
                                            getApplicationContext(), android.R.layout.simple_spinner_item, localAuthority_holder);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    ((Spinner) findViewById(R.id.Authority_spinner)).setAdapter(adapter);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("Failed to getLocalAuthority");
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

    public void setmile_spinner(){

        String[] range = {"10 Miles","20 Miles","30 Miles","40 Miles","50 Miles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_item, range);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) findViewById(R.id.mile_spinner)).setAdapter(adapter);
    }

    public void local_listerner(View view) {
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
            System.out.print(url+" simple\n");

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
        if (location != null) {
            String longitued = String.valueOf(location.getLongitude());
            String latitude = String.valueOf(location.getLatitude());
            String pagesize = "2";
            String sortoption = "distance";

            Holder_bulider holder_builder2 = new Holder_bulider();
            holder_builder2.setPageSize(pagesize);
            holder_builder2.setLongitude(longitued);
            holder_builder2.setLatitude(latitude);
            holder_builder2.setSortOptionKey(sortoption);
            final String url = holder_builder2.build().simple_url();
            System.out.print(url+" local\n");
            //start decode the json data
            VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Info_holder> result) {
                    //System.out.print(result.toString()+"\n");
                    Intent intent = new Intent(getContext(), Show_local_search.class);
                    intent.putParcelableArrayListExtra("local", result);
                    startActivity(intent);
                }
            };
            getdatafromServer(url,callback);
        }else{
            Toast.makeText(MainActivity.this, "no location data", Toast.LENGTH_SHORT).show();

        }

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
        if(pref.getString("BusinessName","").isEmpty()){
            Toast.makeText(MainActivity.this, "You like nothing", Toast.LENGTH_SHORT).show();
        }else{
            pref.edit().clear().commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }

}
