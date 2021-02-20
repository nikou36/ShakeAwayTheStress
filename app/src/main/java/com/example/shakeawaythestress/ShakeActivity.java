package com.example.shakeawaythestress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shakeawaythestress.controllers.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
/*
Displays a random quote from the chosen category. Calls the appropriate API to retrieve quotes.
 */

public class ShakeActivity extends AppCompatActivity {
    private ConstraintLayout parent;
    private SensorManager mSensorManager;
    private ProgressBar progressBar;
    private TextView shakeText;
    private TextView reminderText;
    private String type;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.i("Type",extras.getString("Type"));

        this.type = extras.getString("Type");
        initUI();
    }
    //Detect fingers sliding
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    disableUI();
                    switch (this.type){
                        case "Affirmations":
                            affirmationGetRequest();
                            break;
                        case "Chuck Norris Jokes":
                            chuckGetRequest();
                            break;
                        case "Advice":
                            adviceGetRequest();
                            break;
                        case "General Jokes":
                            jokeGetRequest();
                            break;
                        case "Kanye Quotes":
                            kanyeGetRequest();
                            break;
                        default:
                            geekGetRequest();

                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /*
    Binds View IDs and randomly chooses a background. Each quote category has a specific set of
    background images to chose from.
     */
    private void initUI(){
        this.parent = (ConstraintLayout)findViewById(R.id.parentLayout);
        this.shakeText = findViewById(R.id.shakeText);
        this.progressBar = findViewById(R.id.progressBar);
        this.reminderText = findViewById(R.id.reminderText);
        TypedArray imgs = null;
        switch (this.type){
            case "Affirmations":
                imgs = getResources().obtainTypedArray(R.array.affirmArray);
                this.shakeText.setText("Swipe for an Affirmation");
                this.reminderText.setText("Swipe again for another Affirmation!");

                break;
            case "Chuck Norris Jokes":
                imgs = getResources().obtainTypedArray(R.array.chuckArray);
                this.shakeText.setText("Swipe for Chuck Norris Joke!");
                this.reminderText.setText("Swipe again for another Chuck Norris Joke");
                break;
            case "Advice":
                imgs = getResources().obtainTypedArray(R.array.adviceArray);
                this.shakeText.setText("Swipe for a piece of Advice");
                this.reminderText.setText("Swipe again for more Advice");

                break;
            case "General Jokes":
                imgs = getResources().obtainTypedArray(R.array.jokeArray);
                this.shakeText.setText("Swipe for a Joke!");
                this.reminderText.setText("Swipe again for another Joke");
                break;
            case "Kanye Quotes":
                imgs = getResources().obtainTypedArray(R.array.kanyeArray);
                this.shakeText.setText("Swipe for a Kanye Quote");
                this.reminderText.setText("Swipe again for another Kanye Quote");
                break;
            default:
                //Defaults to geek
                imgs = getResources().obtainTypedArray(R.array.geekArray);
                this.shakeText.setText("Swipe for a Geek Joke!");
                this.reminderText.setText("Swipe again for another Geek Joke");
        }

        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        final int resID = imgs.getResourceId(rndInt, 0);

        parent.setBackgroundResource(resID);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {

        super.onPause();
    }
    //Temporary make views invisible during API calls
    private void disableUI(){
        this.shakeText.setVisibility(View.INVISIBLE);
        this.progressBar.setVisibility(View.VISIBLE);

    }
    //Make views visible after API call is complete
    private void enableUI(){
        this.shakeText.setVisibility(View.VISIBLE);
        this.reminderText.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    //GET request to joke API
    private void jokeGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.randomJokeGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String joke = response.getString("setup");
                    String punchline = response.getString("punchline");
                    String combined = joke + " " + punchline;
                    shakeText.setText(combined);
                    enableUI();
                    mQueue.stop();
                }catch (JSONException e){
                    Log.i("Joke Call","JSON parse Error ");
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Joke Call","Error Occured");
                System.out.println(error.toString());
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    //GET request to geek joke API
    private void geekGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.geekGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    shakeText.setText(response.getString("joke"));
                    enableUI();
                    mQueue.stop();
                }catch (JSONException e){
                    Log.i("Norris Call","JSON parse Error ");
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Norris Call","Error Occured");
                System.out.println(error.toString());
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    //GET request to kanye API
    private void kanyeGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.kanyeGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    shakeText.setText(response.getString("quote"));
                    enableUI();
                    mQueue.stop();
                }catch (JSONException e){
                    Log.i("Kanye Call","JSON parse Error ");
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Kanye Call","Error Occured");
                System.out.println(error.toString());
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    //GET request to Chuck Norris API
    private void chuckGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.chuckNorrisGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    shakeText.setText(response.getString("value"));
                    enableUI();
                    mQueue.stop();
                }catch (JSONException e){
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                    Log.i("Norris Call","JSON parse Error ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Norris Call","Error Occured");
                System.out.println(error.toString());
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    //GET request to Affirmations API
    private void affirmationGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.affirmationsGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    shakeText.setText(response.getString("affirmation"));
                    Log.i("Volley Advice","Successful Call");
                    enableUI();
                    mQueue.stop();
                } catch (JSONException e) {
                    Log.i("Volley Advice","JSON parse error");
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Advice","Error Occured");
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();

            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    //GET request to Advice API
    private void adviceGetRequest(){
        RequestQueue mQueue = VolleyController.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = getString(R.string.adviceGET);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Log.i("Advice Call","Successful Call ");
                    JSONObject obj = response.getJSONObject("slip");
                    shakeText.setText(obj.getString("advice"));
                    enableUI();
                    mQueue.stop();
                }catch (JSONException e){
                    Log.i("Advice Call","JSON parse Error ");
                    Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Advice Call","Error Occured");
                System.out.println(error.toString());
                Toast.makeText(getBaseContext(), "Error Occured! Try again later!", Toast.LENGTH_SHORT).show ();
            }
        });
        request.setShouldCache(false);
        mQueue.add(request);
        mQueue.start();
    }
}