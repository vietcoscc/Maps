package com.example.vaio.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by vaio on 11/27/2016.
 */

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater inflater;
    private Context context;

    public MyInfoWindowAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getInfoWindow(Marker marker) {
//        return getView(marker);
        return null;
    }

    private View getView(Marker marker) {
        View v = inflater.inflate(R.layout.info_window, null);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        TextView tvSnippet = (TextView) v.findViewById(R.id.tvSnippet);
        tvTitle.setText(marker.getTitle());
        tvSnippet.setText(marker.getSnippet());
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return getView(marker);
    }
}
