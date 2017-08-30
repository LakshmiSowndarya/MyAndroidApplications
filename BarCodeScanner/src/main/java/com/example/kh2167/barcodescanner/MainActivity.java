package com.example.kh2167.barcodescanner;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","PRODUCT_MODE");
            startActivityForResult(intent,0);
        }
        catch(ActivityNotFoundException e) {
            showDialog(MainActivity.this, "No Scanner Found", "Downdload a scanner code activity?", "YES", "NO").show();
        }
    }

    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","QR_CODE_MODE");
            startActivityForResult(intent,0);
        }
        catch(ActivityNotFoundException e) {
            showDialog(MainActivity.this, "No Scanner Found", "Downdload a scanner code activity?", "YES", "NO").show();
        }
    }

    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title,
          CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title).setMessage(message).setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                }
                catch(Exception e) {

                }
            }
        }).setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return downloadDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, "Content: " + contents + " Format: " + format, Toast.LENGTH_LONG).show();
            }
        }
    }
}
