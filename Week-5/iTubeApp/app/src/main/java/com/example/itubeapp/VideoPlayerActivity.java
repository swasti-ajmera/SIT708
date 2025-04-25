package com.example.itubeapp;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoPlayerActivity extends AppCompatActivity {
    private WebView webView;
    private static final String TAG = "VideoPlayer";
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        webView = findViewById(R.id.webview_video_player);

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);

        // *** SOLUTION 1: ADDED SETTINGS ***
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Force hardware rendering
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setWebChromeClient(new WebChromeClient());

        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Set WebView clients with improved logging
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "Page finished loading: " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e(TAG, "WebView error: " + description + " (code: " + errorCode + ") for URL: " + failingUrl);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        // Get original URL passed from HomeActivity
        String originalUrl = getIntent().getStringExtra("videoUrl");

        if (originalUrl != null && !originalUrl.isEmpty()) {
            videoId = extractYouTubeId(originalUrl);
            if (videoId != null && !videoId.isEmpty()) {
                loadYoutubeVideo(videoId);
            } else {
                Log.e(TAG, "Could not extract YouTube video ID.");
            }
        } else {
            Log.e(TAG, "Invalid or missing video URL.");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save video ID for rotation
        outState.putString("videoId", videoId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore video ID after rotation
        if (savedInstanceState != null) {
            videoId = savedInstanceState.getString("videoId");
            if (videoId != null && !videoId.isEmpty()) {
                loadYoutubeVideo(videoId);
            }
        }
    }

    private void loadYoutubeVideo(String videoId) {
        // Modified HTML to ensure proper video loading
        String embedHtml = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">" +
                "<style>" +
                "body { margin: 0; padding: 0; background-color: #000; }" +
                "iframe { width: 100%; height: 100vh; border: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<iframe src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1&rel=0&playsinline=1\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>" +
                "</body>" +
                "</html>";

        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&playsinline=1&rel=0&enablejsapi=1";
//        webView.loadUrl(embedUrl);
//        webView.loadDataWithBaseURL("https://www.youtube.com", embedHtml, "text/html", "UTF-8", null);
        webView.loadDataWithBaseURL(embedUrl, embedHtml, "text/html", "UTF-8", null);
        Log.d(TAG, "Loading video ID: " + videoId);
    }

    private String extractYouTubeId(String youtubeUrl) {
        String videoId = null;

        // Comprehensive pattern to match most YouTube URL formats
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);

        if (matcher.find()) {
            videoId = matcher.group();
        }

        Log.d(TAG, "Extracted video ID: " + videoId + " from URL: " + youtubeUrl);
        return videoId;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause video when activity is paused
        webView.onPause();
        webView.loadUrl("javascript:document.querySelector('iframe').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"pauseVideo\",\"args\":\"\"}', '*');");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}