package com.playnova.arcade;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/** Offline Android wrapper for the approved PLAYNOVA Premium v2 HTML arcade. */
public final class MainActivity extends Activity {
    private WebView gameView;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(8, 14, 32));
        getWindow().setNavigationBarColor(Color.rgb(8, 14, 32));

        gameView = new WebView(this);
        gameView.setBackgroundColor(Color.rgb(8, 14, 32));
        gameView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        WebSettings settings = gameView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setDefaultTextEncodingName("UTF-8");
        gameView.setWebChromeClient(new WebChromeClient());
        gameView.setWebViewClient(new WebViewClient());
        setContentView(gameView);

        try {
            gameView.loadDataWithBaseURL("https://playnova.local/", readBundledHtml(),
                    "text/html", "UTF-8", null);
        } catch (IOException exception) {
            TextView message = new TextView(this);
            message.setText("PLAYNOVA could not load the offline arcade. Please reinstall the APK.");
            message.setTextColor(Color.WHITE);
            message.setBackgroundColor(Color.rgb(8, 14, 32));
            message.setPadding(28, 60, 28, 28);
            setContentView(message);
        }
    }

    private String readBundledHtml() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (InputStream input = getAssets().open("index.html");
             InputStreamReader reader = new InputStreamReader(input, "UTF-8")) {
            char[] chunk = new char[4096];
            int count;
            while ((count = reader.read(chunk)) != -1) builder.append(chunk, 0, count);
        }
        return builder.toString();
    }

    @Override public void onBackPressed() {
        if (gameView != null && gameView.canGoBack()) gameView.goBack();
        else super.onBackPressed();
    }

    @Override protected void onDestroy() {
        if (gameView != null) {
            gameView.stopLoading();
            gameView.destroy();
        }
        super.onDestroy();
    }
}
