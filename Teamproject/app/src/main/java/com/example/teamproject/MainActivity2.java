package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        final String result2, result3, result4;

        Intent getText=getIntent();
        result2=getText.getStringExtra("result2");
        result3=getText.getStringExtra("result3");
        result4=getText.getStringExtra("result4");

        String info=result2+"\n"+result3+"\n"+result4+"\n";
        textSMS.setText(info);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //입력한 값을 가져와 변수에 담는다

                String phoneNo = textPhoneNo.getText().toString();
                String sms=textSMS.getText().toString();

                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SM faild, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }
}