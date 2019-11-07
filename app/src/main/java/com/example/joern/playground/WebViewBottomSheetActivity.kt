package com.example.joern.playground

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_web_view_bottom_sheet.toolbar
import kotlinx.android.synthetic.main.content_web_view_bottom_sheet.news_web_view
import kotlinx.android.synthetic.main.social_bottom_sheet.bottom_sheet
import org.jetbrains.anko.dip

class WebViewBottomSheetActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web_view_bottom_sheet)
    setSupportActionBar(toolbar)

    news_web_view.apply {
      overScrollStartedListener = {
        BottomSheetBehavior.from(bottom_sheet)?.let {
          changePeek(it, dip(80))
//          it.peekHeight = dip(80)
        }
      }
      furtherOverScrollingListener = {
        BottomSheetBehavior.from(bottom_sheet)?.let {

          it.state = BottomSheetBehavior.STATE_EXPANDED
        }
      }
      scrollUpAgainListener = {
        BottomSheetBehavior.from(bottom_sheet)?.let {
//          it.state = BottomSheetBehavior.STATE_COLLAPSED
          changePeek(it, dip(0))
        }
      }
      scrollDownAgainListener = {
        BottomSheetBehavior.from(bottom_sheet)?.let {
//          it.state = BottomSheetBehavior.STATE_COLLAPSED
          changePeek(it, dip(80))
        }
      }
      webViewClient = WebViewClient()
      loadUrl("https://coyoapp.com")
    }
  }

  private fun changePeek(behavior: BottomSheetBehavior<*>?, height: Int) {
    behavior?.apply {
      state = BottomSheetBehavior.STATE_HIDDEN
      peekHeight = height
      state = BottomSheetBehavior.STATE_COLLAPSED
    }
  }
}
