package com.tonikamitv.loginregister.Library;

/**
 * Created by ASUS on 27/11/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonikamitv.loginregister.R;

import java.util.ArrayList;
import java.util.List;

public class LaporanAdapter extends ArrayAdapter<String> {
    private final Activity context;
    List<String> listid_laporan = new ArrayList<String>();
    List<String> listkolom_laporan = new ArrayList<String>();
    List<String> listnama_kategori = new ArrayList<String>();
    List<String> liststatus = new ArrayList<String>();
    List<String> listuser_id = new ArrayList<String>();
    List<String> listfoto_laporan = new ArrayList<String>();
    List<String> listnama = new ArrayList<String>();
    public LaporanAdapter(Activity context,
                          List<String> listid_laporan, List<String> listkolom_laporan, List<String> listnama_kategori, List<String> liststatus, List<String> listuser_id, List<String> listfoto_laporan, List<String> listnama) {
        super(context, R.layout.laporan_list, listid_laporan);
        this.context = context;
        this.listid_laporan = listid_laporan;
        this.listkolom_laporan = listkolom_laporan;
        this.listnama_kategori = listnama_kategori;
        this.liststatus = liststatus;
        this.listuser_id = listuser_id;
        this.listfoto_laporan = listfoto_laporan;
        this.listnama = listnama;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.laporan_list, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView short_content = (TextView) rowView.findViewById(R.id.short_content);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        title.setText(listnama.get(position)+" ("+listid_laporan.get(position)+")");
        if(listkolom_laporan.get(position).length()>=80){
            short_content.setText(listkolom_laporan.get(position).substring(0, 80)+"...");
        }else{
            short_content.setText(listkolom_laporan.get(position)+"...");
        }
        return rowView;
    }


}