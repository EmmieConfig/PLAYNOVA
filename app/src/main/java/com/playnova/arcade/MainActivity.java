package com.playnova.arcade;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/** Offline Android wrapper for PLAYNOVA Premium v2 and TRAP IQ. */
public final class MainActivity extends Activity {
    private WebView gameView;
    private long lastBackAt = 0L;

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
        // Keep large LUDO ROYALE artwork in its own offline HTML asset.
        // Served under PLAYNOVA's existing trusted local origin; no internet or file access required.
        gameView.setWebViewClient(new WebViewClient() {
            @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equals(uri.getScheme())
                        && "playnova.local".equals(uri.getHost())
                        && "/ludo_royale.html".equals(uri.getPath())) {
                    try {
                        return new WebResourceResponse("text/html", "UTF-8",
                                getAssets().open("ludo_royale.html"));
                    } catch (IOException exception) {
                        return new WebResourceResponse("text/plain", "UTF-8",
                                new java.io.ByteArrayInputStream(
                                    "LUDO ROYALE asset not found".getBytes(
                                        java.nio.charset.StandardCharsets.UTF_8)));
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
        setContentView(gameView);

        // Android 13+ system back swipe, including Android 14 predictive back.
        if (Build.VERSION.SDK_INT >= 33) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                new OnBackInvokedCallback() {
                    @Override public void onBackInvoked() { handleBack(); }
                });
        }

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

    /** Request in-app back navigation first; only exit after a second back at Home. */
    private void handleBack() {
        if (gameView == null) { finish(); return; }
        gameView.evaluateJavascript(
            "(function(){return !!(window.playNovaBack && window.playNovaBack());})()",
            result -> {
                if ("true".equals(result)) {
                    lastBackAt = 0L;
                    return;
                }
                long now = android.os.SystemClock.elapsedRealtime();
                if (now - lastBackAt < 2000) {
                    finish();
                } else {
                    lastBackAt = now;
                    Toast.makeText(this, "Swipe back again to exit PLAYNOVA", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override public void onBackPressed() {
        handleBack();
    }

    @Override protected void onDestroy() {
        if (gameView != null) {
            gameView.stopLoading();
            gameView.destroy();
            gameView = null;
        }
        super.onDestroy();
    }
}
