package com.ivan.android.manhattanenglish.app.core.purchase;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.remote.course.Appointment;

/**
 * Created by ivan on 9/23/14.
 */
public class AlipayActivity extends BaseActivity {

    public static final String KEY_SUBJECT = "SUBJECT";
    public static final String KEY_APPOINTMENT = "APPOINTMENT";

    private String appointmentJson;
    private String subject;
    private Appointment appointment;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_alipay);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        appointmentJson = intent.getStringExtra(KEY_APPOINTMENT);
        appointment = JSON.parseObject(appointmentJson, Appointment.class);

        subject = intent.getStringExtra(KEY_SUBJECT);

        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(AlipayActivity.this, "error happens." + errorCode + ".description:" + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                error.getCertificate();
            }
        };

        webView.setWebViewClient(webViewClient);

//        String url = appointment.toUrl(subject);
//        webView.loadUrl(url);
        String url = AbstractService.HOST + "/payment/payment";
        webView.postUrl(url, appointment.getPostData(subject));

    }


}
