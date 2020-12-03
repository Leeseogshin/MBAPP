package com.example.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;





public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listview ;
    ListViewAdapter adapter;
    int CurrentPage = 1;
    String api;

    HashMap<String, Double> la = new HashMap<String, Double>();//위도
    HashMap<String, Double> lo = new HashMap<String ,Double>();//경도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentPage = 1;
        //공항 좌표값 할당
        la.put("AMS",4.7674241);
        lo.put("AMS",52.3076865);
        la.put("KTM",27.6967);
        lo.put("KTM",85.3592);
        la.put("OSL",60.197552);
        lo.put("OSL",11.100415);
        la.put("NOU",-22.2558234);
        lo.put("NOU",166.4505243);
        la.put("AKL",-37.0048 );
        lo.put("AKL",174.7883);
        lo.put("KHH",120.2033);
        la.put("KHH",22.3438);
        la.put("TPE",-25.076068);
        lo.put("TPE",121.231625);
        la.put("CPH",-55.620750);
        lo.put("CPH",12.650462);
        la.put("MUC",48.3536621 );
        lo.put("MUC",11.775027899999941);
        la.put("CGN",50.8595  );
        lo.put("CGN",7.1390);
        la.put("FRA",50.033333  );
        lo.put("FRA",8.570556);
        la.put("VTE",17.9863   );
        lo.put("VTE",102.5580);
        la.put("OVB",55.0045   );
        lo.put("OVB",82.3902);
        la.put("SVO",55.9703    );
        lo.put("SVO",37.4088);
        la.put("VVO",43.2357);
        lo.put("VVO",132.0905);
        la.put("BEY",33.8193 );
        lo.put("BEY",35.4863);
        la.put("LUX",49.628899 );
        lo.put("LUX",6.214745);
        la.put("ULN",47.8392  );
        lo.put("ULN",106.7598);
        la.put("GUM",13.48340034  );
        lo.put("GUM",144.7960052);
        la.put("JFK",40.641766  );
        lo.put("JFK",-73.780968);
        la.put("EWR",40.6895314);
        lo.put("EWR",-74.17446239999998);
        la.put("LAS",36.086010);
        lo.put("LAS",-115.153969);
        la.put("LAX",33.942791);
        lo.put("LAX",-118.410042);
        la.put("MIA",25.7933 );
        lo.put("MIA",-80.2906);
        la.put("IAD",38.9531162  );
        lo.put("IAD",-77.45653879999998);
        la.put("SIN",1.359167  );
        lo.put("SIN",103.989441);
        la.put("DXB",25.252777  );
        lo.put("DXB",55.364445);
        la.put("BOM",19.097403  );
        lo.put("BOM",72.874245);

        la.put("KIX",34.432002);
        lo.put("KIX",135.230394);

        la.put("TAE",37.463333  );
        lo.put("TAE",126.440002);
        la.put("PUS",35.1743);
        lo.put("PUS",128.9363);
        la.put("GMP",37.5575 );
        lo.put("GMP",126.8005);
        la.put("CJU",33.5068  );
        lo.put("CJU",126.4892);

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swip_layout);
        //textView = (TextView)findViewById(R.id.textView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "새로고침 되었습니다.", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //String api = "http://openapi.airport.kr/openapi/service/StatusOfPassengerWeahter/getPassengerArrivalsW?ServiceKey=URTjt6uEUH9SIYq1H3zFtGNg65d2oeLiJGJPk0t1ZcEtUJIw4x4BjRSXIxtM8ZZLI9xiQswQyiD0Us3lNCAaHQ%3D%3D&pageNo=1&numOfRows=10&from_time=0000&to_time=2400&airport=HKG&airline=KE&lang=K";
        api = "http://openapi.airport.kr/openapi/service/StatusOfPassengerWeahter/getPassengerDeparturesW?ServiceKey=URTjt6uEUH9SIYq1H3zFtGNg65d2oeLiJGJPk0t1ZcEtUJIw4x4BjRSXIxtM8ZZLI9xiQswQyiD0Us3lNCAaHQ%3D%3D";

        long mNow = System.currentTimeMillis();
        Date mReDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("HHmm");
        String formatDate = mFormat.format(mReDate);
        api = api + "&from_time="+formatDate+";";


        DownloadWebpageTask task = new DownloadWebpageTask();

        task.execute(api);








/*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                // TODO : use item data.
            }
        }) ;*/
    }

    public void minuspage(View view) {
        if(CurrentPage==1)
            Toast.makeText(this.getApplicationContext(),"현재 1페이지입니다.",Toast.LENGTH_SHORT).show();
        else {
            CurrentPage -= 1;
            String test;
            test = api + "&pageNo=" + CurrentPage;
            textView = (TextView)findViewById(R.id.numtext);
            textView.setText(Integer.toString(CurrentPage));
            DownloadWebpageTask task = new DownloadWebpageTask();
            task.execute(test);

        }
    }

    public void pluspage(View view) {
        String test;
        CurrentPage += 1;
        test = api + "&pageNo=" + CurrentPage;
        textView = (TextView)findViewById(R.id.numtext);
        textView.setText(Integer.toString(CurrentPage));

        DownloadWebpageTask task = new DownloadWebpageTask();
        task.execute(test);
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
// API에 해당하는 문서 다운로드
                String txt = (String) downloadUrl((String)urls[0]);
                return txt; // onPostExecute의 파라미터로 전달됨
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }
        // 문서 다운로드 후 자동 호출
        @Override
        protected void onPostExecute(String result) { // ver1
            super.onPostExecute(result);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            Document doc = null;
            NodeList nodeList = null;

            InputSource is = new InputSource(new StringReader(result));
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                doc = builder.parse(is);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = null;
            try {
                expr = xpath.compile("//items/item");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            adapter = new ListViewAdapter() ;

            // 리스트뷰 참조 및 Adapter달기
            listview = (ListView) findViewById(R.id.listview1);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    // get item
                    ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                    String airportCode = item.getAirportCode();
                    // TODO : use item data.
                }
            });

            // 첫 번째 아이템 추가.
            //adapter.addItem("Box", "Account Box Black 36dp") ;
            // 두 번째 아이템 추가.
            //adapter.addItem("Circle", "Account Circle Black 36dp") ;
            // 세 번째 아이템 추가.
            //adapter.addItem("Ind", "Assignment Ind Black 36dp") ;




            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                String temp = "";
                for (int j = 0; j < child.getLength(); j++) {

                    Node node = child.item(j);
                    temp +=  node.getTextContent() + "@";
                }
                String[] ar = temp.split("@");
                if(ar.length==10)
                    adapter.addItem(ar[0],ar[1],ar[2],ar[3],ar[4],ar[5],ar[6],ar[7],ar[8],ar[9]);
                else
                    adapter.addItem(ar[0],ar[1],ar[2],ar[3],ar[4],ar[5],ar[6],ar[7],ar[8],ar[9],ar[10],
                            ar[11],ar[12],ar[13],ar[14],ar[15]);

            }

            setListViewSize(listview);


        }

        private String downloadUrl(String api) throws IOException {
            HttpURLConnection conn = null;
            try {
// 문서를 읽어 텍스트 단위로 버퍼에 저장
                URL url = new URL(api);
                conn = (HttpURLConnection) url.openConnection();

                /*
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
// 줄 단위로 읽어 문자로 저장
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                    page += "****************************\n";
                }

                 */


                BufferedReader rd;
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                StringBuilder sb = new StringBuilder();
                String line;
                int i=0;
                while ((line = rd.readLine()) != null) {
                    if(i==0)
                    {
                        i+=1;
                        continue;
                    }
                    sb.append(line.trim());
                }
                String page=sb.toString();
// 다운로드 문서 반환
                return page;
            } finally {
                conn.disconnect();
            }
        }
    }// class DownloadWebpageTask

    public void showMap(View view) {
        TextView b = (TextView)view;
        String code = b.getText().toString();
        double longitude = lo.get(code);
        double latitude = la.get(code);
        int zoom = 10;
        String pos = String.format("geo:%f,%f?z=%d", latitude, longitude, zoom);
        Uri geo = Uri.parse(pos);
        Intent intent = new Intent(Intent.ACTION_VIEW, geo);
        startActivity(intent);

    }

    public void setListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

    }



}