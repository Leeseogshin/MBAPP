package com.example.final_task;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_task.R;

public class input2 extends AppCompatActivity {

    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input2);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        final String result2, result3, result4, result5, result6;

        Intent getText=getIntent();
        result2=getText.getStringExtra("result2");
        result3=getText.getStringExtra("result3");
        result4=getText.getStringExtra("result4");
        result5 = getText.getStringExtra("result5");
        result6=getText.getStringExtra("result6");

        String info="공항 : "+result2+"\n"+"체크인 카운터 : "+result3+"\n"+"탑승구 : "+result4+"\n"
                +"예정시간 : "+result5+"\n"+"변경시간 : "+result6+"\n";
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