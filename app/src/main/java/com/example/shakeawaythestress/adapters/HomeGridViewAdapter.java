package com.example.shakeawaythestress.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shakeawaythestress.R;
import com.example.shakeawaythestress.models.GridOption;

import java.util.ArrayList;
/*
Custom GridView Adapter for quote categories
 */

public class HomeGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<GridOption> gridOptions;

    private class ViewHolder{
        private TextView text;
        private ImageView image;
        public ViewHolder (TextView text, ImageView image){
            this.text = text;
            this.image = image;
        }
    }

    public HomeGridViewAdapter(Context context, ArrayList<GridOption> gridOptions){
        this.mContext = context;
        this.gridOptions = gridOptions;
    }



    @Override
    public int getCount(){
        return gridOptions.size();
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final GridOption option = gridOptions.get(position);
        if(convertView == null){

        }
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.grid_option_item,null);
        final ImageView choiceImage = (ImageView) convertView.findViewById(R.id.optionImage);
        final TextView textView = (TextView) convertView.findViewById(R.id.optionText);
        final ViewHolder viewHolder = new ViewHolder(textView,choiceImage);

        //final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        String mDrawableName = option.getImageName();
        int resID = convertView.getResources().getIdentifier(mDrawableName , "drawable", mContext.getPackageName());
        viewHolder.image.setBackgroundResource(resID);
        viewHolder.text.setText(option.getText());

        //choiceImage.setBackgroundResource(resID);
        //textView.setText(option.getText());
        return convertView;
    }
}
