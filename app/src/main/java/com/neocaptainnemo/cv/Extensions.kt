package com.neocaptainnemo.cv

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LiveData

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

inline fun View.visibleIf(condition: (() -> Boolean)) {
    visibility = if (condition.invoke()) View.VISIBLE else View.GONE
}

val String?.spanned: Spanned
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }

data class Scrollevent(
        val scrollX: Int,
        val scrollY: Int,
        val oldScrollX: Int,
        val oldScrollY: Int
)
fun NestedScrollView.scrollChangeLiveData() = object : LiveData<Scrollevent>() {

    override fun onActive() {
        super.onActive()
        setOnScrollChangeListener { _: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            value = Scrollevent(scrollX, scrollY, oldScrollX, oldScrollY)
        }
    }

    override fun onInactive() {
        super.onInactive()

        val listener: NestedScrollView.OnScrollChangeListener? = null
        setOnScrollChangeListener(listener)
    }
}