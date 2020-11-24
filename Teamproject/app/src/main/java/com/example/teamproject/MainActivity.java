package com.example.teamproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.net.URLEncoder.encode;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        String api = "http://openapi.airport.kr/openapi/service/StatusOfPassengerWeahter/getPassengerArrivalsW?ServiceKey=URTjt6uEUH9SIYq1H3zFtGNg65d2oeLiJGJPk0t1ZcEtUJIw4x4BjRSXIxtM8ZZLI9xiQswQyiD0Us3lNCAaHQ%3D%3D&pageNo=1&numOfRows=10&from_time=0000&to_time=2400&airport=HKG&airline=KE&lang=K";

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
            textView.setText(result);
        }

        private String downloadUrl(String api) throws IOException {
            HttpURLConnection conn = null;
            try {
// 문서를 읽어 텍스트 단위로 버퍼에 저장
                URL url = new URL(api);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
// 줄 단위로 읽어 문자로 저장
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }
// 다운로드 문서 반환
                return page;
            } finally {
                conn.disconnect();
            }
        }
    }// class DownloadWebpageTask
}