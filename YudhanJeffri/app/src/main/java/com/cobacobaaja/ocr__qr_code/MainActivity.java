package com.cobacobaaja.ocr__qr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cobacobaaja.ocr__qr_code.OCR.OCRMainActivity;
import com.cobacobaaja.ocr__qr_code.QRCode.QRMainActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btn_ocr = findViewById(R.id.fab_action_ocr);
        btn_ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OCRMainActivity.class));
            }
        });

        FloatingActionButton btn_qr = findViewById(R.id.fab_action_menu);


        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,QRMainActivity.class));
            }
        });

    }
}
