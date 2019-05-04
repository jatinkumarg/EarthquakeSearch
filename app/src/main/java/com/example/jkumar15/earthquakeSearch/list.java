package com.example.jkumar15.earthquakeSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class list extends AppCompatActivity {

    ListView linearLayoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        linearLayoutListView = findViewById(R.id.list);

        Intent intent = getIntent();
        String stringData1 = intent.getStringExtra("eqNo");
        String stringData2 = intent.getStringExtra("orderBy");
        String stringData3 = intent.getStringExtra("date");

        if(stringData2.equals("date")){
            QuakeAsyncTask task = new QuakeAsyncTask();
            task.execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+ stringData3+"&limit="+stringData1+"&orderby=time");

        }else{
            QuakeAsyncTask task = new QuakeAsyncTask();
            task.execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+ stringData3+"&limit="+stringData1+"&minmagnitude=7.0&orderby=" + stringData2);
        }


    }


    class QuakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> result = Utils.fetchEarthquakeData(urls[0]);
            return result;
        }

        public void onPostExecute(final List<Earthquake> list) {

            if (list != null) {
                linearLayoutListView.setAdapter(new CustomListAdapter(getApplicationContext(), R.layout.custom, list));
                linearLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String lat = list.get(position).getLat();
                        String lng = list.get(position).getLng();

                        String URL = "https://www.openstreetmap.org/?mlat=" + lng + "&mlon=" + lat + "#map=5/" + lng + "/" + lat;
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(URL));
                        startActivity(viewIntent);
                    }
                });
            }
        }
    }

}

class CustomListAdapter extends ArrayAdapter<Earthquake> {

    private int resourceLayout;
    private Context mContext;


    public CustomListAdapter(Context context, int resource, List<Earthquake> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout,null);
        }

        Earthquake p = getItem(position);

        if(p!=null){
            TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
            TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
            LinearLayout Layout = (LinearLayout) view.findViewById(R.id.layoutg);

            tv1.setText(p.getTitle());
            tv2.setText(""+p.getTime());

            if (p.getMag() >= 7.5){
                tv1.setBackgroundColor(Color.parseColor("#ff4444"));
                tv2.setBackgroundColor(Color.parseColor("#ff4444"));

            }else{
                tv1.setBackgroundColor(Color.parseColor("#ffbb33"));
                tv2.setBackgroundColor(Color.parseColor("#ffbb33"));

            }
        }

        return view;

    };
}