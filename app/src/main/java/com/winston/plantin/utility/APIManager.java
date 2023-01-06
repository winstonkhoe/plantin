package com.winston.plantin.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.PlantShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIManager {
    private Context ctx;
    private RequestQueue queue;
    private final String url = "https://api.jsonbin.io/v3/b/61ca3d0cc912fe67c50a1e27";
    private PlantinDB db;
    private boolean flag;

    public APIManager(Context ctx){
        this.ctx = ctx;
        db = new PlantinDB(ctx);
        queue = Volley.newRequestQueue(ctx);
    }

    public boolean fetchData(){
        flag = true;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET ,url, null, response -> {
            try {
                JSONArray records = response.getJSONArray("record");
                for(int i = 0 ; i < records.length() ; i++){
                    try {
                        JSONObject temp = records.getJSONObject(i);
                        String name = temp.getString("name");
                        String location = temp.getString("location");
                        Double latitude = temp.getDouble("latitude");
                        Double longitude = temp.getDouble("longitude");
                        String image = temp.getString("image");
                        db.insertPlantShop(new PlantShop(null, name, location, image, latitude, longitude));
                    } catch (JSONException e) {
                        flag = false;
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ctx, "Failed To Load Data From API", Toast.LENGTH_SHORT).show());
        queue.add(req);
        return flag;
    }
}
