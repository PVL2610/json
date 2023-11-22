package com.example.phanvanlinh_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import com.example.json_api.R;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvUsers;
    ArrayList<String> arrayUsers;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUsers = (ListView) findViewById(R.id.listviewUsers);
        arrayUsers = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayUsers);
        lvUsers.setAdapter(adapter);

        new ReadJSON().execute("https://raw.githubusercontent.com/PVL2610/demo-1/master/db.json");
    }

    private class  ReadJSON extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL  url= new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray array = new JSONArray(s);

                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    String userId = object.getString("userId");
                    String id = object.getString("id");
                    String title = object.getString("title");
                    String completed = object.getString("completed");
                    arrayUsers.add(userId + "." + id + "\n" + title + "\n" + completed);
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}