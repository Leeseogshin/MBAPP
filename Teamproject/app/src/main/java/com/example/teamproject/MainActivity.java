package com.example.teamproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

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
            String result2 = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    result2 += node.getNodeName();
                    result2 += " : ";
                    result2 += node.getTextContent();
                    result2 += "\n";
                }
                result2 += "\n";
            }




            textView.setText(result2);
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
}