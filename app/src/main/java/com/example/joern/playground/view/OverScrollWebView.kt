package com.example.joern.playground.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView


class OverScrollWebView : WebView {

  constructor(context: Context?) : super(context)

  constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)

  var overScrollStartedListener = {}
  var furtherOverScrollingListener = {}
  var scrollUpAgainListener = {}
  var scrollDownAgainListener = {}

  private var startedOverScrollingY = false
  private var wasAtBottomAtAll = false

  override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
    if (scrollY > 0 && clampedY && !startedOverScrollingY) {
      Log.d("OverScrollWebView", "Overscroll detected")
      startedOverScrollingY = true
      wasAtBottomAtAll = true
      overScrollStartedListener()
    } else if (scrollY > 0 && startedOverScrollingY) {
      Log.d("OverScrollWebView", "Further Overscrolling detected")
      furtherOverScrollingListener()
    }
    super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
  }

  override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
    super.onScrollChanged(x, y, oldX, oldY)
    if (y < oldY) {
      Log.d("OverScrollWebView", "Scrolling UP detected")
      startedOverScrollingY = false
      scrollUpAgainListener()
    } else if (y > oldY && wasAtBottomAtAll) {
      Log.d("OverScrollWebView", "Scrolling down again detected")
      scrollDownAgainListener()
    }
  }
}
