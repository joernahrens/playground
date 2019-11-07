package com.example.joern.playground

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view_sheet.toolbar
import kotlinx.android.synthetic.main.content_web_view_sheet.webview

class WebViewSheetActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web_view_sheet)
    setSupportActionBar(toolbar)

    webview.apply {
      webViewClient = WebViewClient()
      loadUrl("https://coyoapp.com")

    }
  }

}
