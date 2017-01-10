package com.example.vaio.maps;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by vaio on 11/27/2016.
 */

public class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<LatLng>> {
    private LatLng start;
    private LatLng end;
    private Handler handler;

    public MyAsyncTask(LatLng start, LatLng end, Handler handler) {
        this.start = start;
        this.end = end;
        this.handler = handler;
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Void... params) {
        Direction direction = new Direction();
        Document document = direction.getDocument(start, end, Direction.MODE_WALKING);
        ArrayList<LatLng> lngArrayList = direction.getDirection(document);
        return lngArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<LatLng> latLngs) {
        super.onPostExecute(latLngs);
        Message message = new Message();
        message.what = MapsActivity.WHAT;
        message.obj = latLngs;
        handler.sendMessage(message);
    }
}
