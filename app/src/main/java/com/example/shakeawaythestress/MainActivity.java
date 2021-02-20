package com.example.shakeawaythestress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.shakeawaythestress.adapters.HomeGridViewAdapter;
import com.example.shakeawaythestress.models.GridOption;

import java.util.ArrayList;
/*
Displays a grid view of different types of quotes the user can select from
 */

public class MainActivity extends AppCompatActivity {
    private GridView optionsGrid;
    private BaseAdapter gridAdapter;
    private ArrayList<GridOption> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.options = new ArrayList<>();
        this.options.add(new GridOption("Advice","adviceoption"));
        this.options.add(new GridOption("Affirmations","affirmationsoption"));
        this.options.add(new GridOption("Chuck Norris Jokes","chucknorrisoption"));
        this.options.add(new GridOption("General Jokes","jokeoption"));
        this.options.add(new GridOption("Kanye Quotes","kanyeoption"));
        this.options.add(new GridOption("Geek Jokes","geekoption"));

        initUI();
    }
    //Bind View IDs and initialize onclick listeners for each gridView item
    private  void initUI(){
        this.optionsGrid = findViewById(R.id.homeGridView);
        this.gridAdapter = new HomeGridViewAdapter(this,this.options);
        this.optionsGrid.setAdapter(this.gridAdapter);

        this.optionsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridOption option = options.get(position);
                Intent i = new Intent(MainActivity.this,ShakeActivity.class);
                i.putExtra("Type",option.getText());
                startActivity(i);
            }
        });
    }
}