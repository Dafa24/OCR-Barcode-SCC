package com.makhalibagas.ocrandqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class QrActivity extends AppCompatActivity {

    private CodeScanner scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        ImageView bg = findViewById(R.id.bg);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        bg.bringToFront();

        scanner = new CodeScanner(this,scannerView);
        scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String pesan = "HASILNYA \n" + result.getText();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getText()));
                        startActivity(intent);
                        showDialogText(pesan);
                    }
                });
            }
        });
        checkKamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkKamera();
    }

    @Override
    protected void onPause() {
        scanner.releaseResources();
        super.onPause();
    }
    private void checkKamera(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
    private void showDialogText(String pesane){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(pesane);
        builder.setCancelable(true);

        builder.setPositiveButton("Scan Lagi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                scanner.startPreview();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
