package com.example.teamproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
    TextView textView, textViewDetail, textViewDetail2, textViewDetail3;
    HashMap<String, Double> la = new HashMap<String, Double>();//위도
    HashMap<String, Double> lo = new HashMap<String ,Double>();//경도
    String result, result2, result3, result4;

    private static final int MULTIPLE_PERMISSION = 10235;
    private String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, MULTIPLE_PERMISSION);
        } else {
            /*..권한이 있는경우 실행할 코드....*/
            System.out.println("권한이 있음.");
        }

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

        la.put("TAE",37.463333  );
        lo.put("TAE",126.440002);
        la.put("PUS",35.1743);
        lo.put("PUS",128.9363);
        la.put("GMP",37.5575 );
        lo.put("GMP",126.8005);
        la.put("CJU",33.5068  );
        lo.put("CJU",126.4892);

        //final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swip_layout);
        textView = (TextView)findViewById(R.id.textView);
        textViewDetail = (TextView)findViewById(R.id.textView2);
        textViewDetail2 = (TextView)findViewById(R.id.textView3);
        textViewDetail3 = (TextView)findViewById(R.id.textView4);

        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "새로고침 되었습니다.", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/

        //String api = "http://openapi.airport.kr/openapi/service/StatusOfPassengerWeahter/getPassengerArrivalsW?ServiceKey=URTjt6uEUH9SIYq1H3zFtGNg65d2oeLiJGJPk0t1ZcEtUJIw4x4BjRSXIxtM8ZZLI9xiQswQyiD0Us3lNCAaHQ%3D%3D&pageNo=1&numOfRows=10&from_time=0000&to_time=2400&airport=HKG&airline=KE&lang=K";
        String api = "http://openapi.airport.kr/openapi/service/StatusOfPassengerWeahter/getPassengerDeparturesW?ServiceKey=URTjt6uEUH9SIYq1H3zFtGNg65d2oeLiJGJPk0t1ZcEtUJIw4x4BjRSXIxtM8ZZLI9xiQswQyiD0Us3lNCAaHQ%3D%3D";
        long mNow = System.currentTimeMillis();
        Date mReDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("HHmm");
        String formatDate = mFormat.format(mReDate);
        api = api + "&from_time="+formatDate+";";

        DownloadWebpageTask task = new DownloadWebpageTask();

        task.execute(api);

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

            Button btn_prev, btn_next;

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
            /*String result2 = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    result2 += node.getNodeName();
                    result2 += " : ";
                    result2 += node.getTextContent();
                    result2 += "\n";
                }
            }

            textView.setText(result2);
            */
            result2 = "";

            try {
                expr = xpath.compile("flightId");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            Node node = null;
            try {
                node = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result2 += node.getTextContent();
            result2 += "\n";
            try {
                expr = xpath.compile("airline");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result2 += node.getTextContent();
            result2 += "\n";

            textView.setText(result2);

            result3 = "";

            try {
                expr = xpath.compile("airport");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            Node node2 = null;
            try {
                node2 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result3 += "공항 : ";
            result3 += node2.getTextContent();
            result3 += "\n";
            try {
                expr = xpath.compile("scheduleDateTime");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node2 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result3 += "도착 예정 시간 : ";
            result3 += node2.getTextContent().charAt(0);
            result3 += node2.getTextContent().charAt(1);
            result3 += "시 ";
            result3 += node2.getTextContent().charAt(2);
            result3 += node2.getTextContent().charAt(3);
            result3 += "분";
            result3 += "\n";
            try {
                expr = xpath.compile("estimatedDateTime");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node2 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result3 += "도착 변경 시간 : ";
            result3 += node2.getTextContent().charAt(0);
            result3 += node2.getTextContent().charAt(1);
            result3 += "시 ";
            result3 += node2.getTextContent().charAt(2);
            result3 += node2.getTextContent().charAt(3);
            result3 += "분";
            result3 += "\n";

            textViewDetail.setText(result3);

            result4 = "";

            try {
                expr = xpath.compile("gatenumber");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            Node node3 = null;
            try {
                node3 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result4 += "탑승구 : ";
            result4 += node3.getTextContent();
            result4 += "\n";
            /*try {
                expr = xpath.compile("exitnumber");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node3 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result4 += "출구 : ";
            result4 += node3.getTextContent();
            result4 += "\n";*/
            try {
                expr = xpath.compile("remark");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node3 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result4 += "상태 : ";
            result4 = node3.getTextContent();
            result4 += "\n";

            textViewDetail2.setText(result4);

            String result5 = "";

            try {
                expr = xpath.compile("mintem");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            Node node4 = null;
            try {
                node4 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result5 += "최저 기온 : ";
            result5 += node4.getTextContent();
            result5 += "도\n";
            try {
                expr = xpath.compile("maxtem");
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            try {
                node4 = (Node) expr.evaluate(nodeList.item(0), XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            result5 += "최고 기온 : ";
            result5 += node4.getTextContent();
            result5 += "도\n";

            textViewDetail3.setText(result5);
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
//        Button b = (Button)findViewById(R.id.next);
//        String code = b.getText().toString();
        String code="AMS";
        double longitude = lo.get(code);
        double latitude = la.get(code);
        int zoom = 10;
        String pos = String.format("geo:%f,%f?z=%d", latitude, longitude, zoom);
        Uri geo = Uri.parse(pos);
        Intent intent = new Intent(Intent.ACTION_VIEW, geo);
        startActivity(intent);

    }

    public void Messaging(View view){
        Intent intent=new Intent(this, MainActivity2.class);
        intent.putExtra("result2", result2);
        intent.putExtra("result3", result3);
        intent.putExtra("result4", result4);
        startActivity(intent);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    //권한 요청에 대한 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*..권한이 있는경우 실행할 코드....*/
                    System.out.println("권한이 있음.");
                } else {
                    // 하나라도 거부한다면.
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("앱 권한");
                    alertDialog.setMessage("해당 앱의 원할한 기능을 이용하시려면 애플리케이션 정보>권한> 에서 모든 권한을 허용해 주십시오");
                    // 권한설정 클릭시 이벤트 발생
                    alertDialog.setPositiveButton("권한설정",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    //취소
                    alertDialog.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
                return;
        }
    }
}