package osama.me.namegamekotlin

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


inline fun <T> Call<T>.exec(crossinline response: (T) -> Unit, crossinline error: (Throwable) -> Unit) {
    this.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful && response.body() != null) {
                        response(response.body()!!)
                        return
                    }
                    error(kotlin.Exception())
                }

                override fun onFailure(call: Call<T>?, t: Throwable) {
                    error(t)
                }
            }
    )
}

fun ImageView.loadUrl(url: String) {
    Picasso.with(context).apply {
        if (BuildConfig.DEBUG) {
            isLoggingEnabled = true
        }
    }.load(url).into(this)
}

fun Context.dpToPixel(dp: Float): Int = (dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

inline fun Any.logDebug(message: () -> String?) {
    if (BuildConfig.DEBUG) {
        Log.d(this.javaClass.simpleName.take(20), message())
    }
}

inline fun <reified T : View> Activity.lazyFindView(@IdRes id: Int): Lazy<T> = lazy { this.findViewById<T>(id) }

inline fun ViewGroup.forEachChild(transform: (View) -> Unit) {
    (0..childCount).map { i -> transform(getChildAt(i)) }
}