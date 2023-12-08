package com.example.cognitive_diagnosis_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ModifyAdapter extends BaseAdapter {
//这个class是为了老师查看自己班级的下拉表的变化

    private Context context;
    private String[] options;
    public ModifyAdapter(Context context, String[] options) {
        this.context = context;
        this.options = options;
    }
    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_spinner_item, null);
        TextView textView=view.findViewById(android.R.id.text1);
        textView.setText(options[position]);
        return view;
    }
}
