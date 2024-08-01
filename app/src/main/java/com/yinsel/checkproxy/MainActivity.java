package com.yinsel.checkproxy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        Boolean isProxy = false;
        if (DetectUtils.isProxy(this)) {
            isProxy = true;
        }
        if (isProxy) {
            textView.setText("存在代理！");
        } else {
            textView.setText("未检测到任何代理！");
        }
    }

    public static void showMessage(Context context, String message) {
         new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}