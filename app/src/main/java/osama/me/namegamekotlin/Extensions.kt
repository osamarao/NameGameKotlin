@file:Suppress("UNCHECKED_CAST")

package osama.me.namegamekotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.IdRes
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


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
            isLoggingEnabled = false
        }
    }.load(url).transform(CircleBorderTransform()).placeholder(R.mipmap.ic_launcsher).into(this)
}

fun ImageView.setSize(height: Int, width: Int) {
    layoutParams = LinearLayout.LayoutParams(width, height)
}

var ImageView.pair: Pair<PersonViewModel, ImageView>
    get() = tag as Pair<PersonViewModel, ImageView>
    set(value) {
        tag = value
    }

fun Context.dpToPixel(dp: Float): Int = (dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()

inline fun Any.logDebug(message: () -> String?) {
    if (BuildConfig.DEBUG) {
        Log.d(this.javaClass.simpleName.take(20), message())
    }
}

inline fun <reified T : View> Activity.lazyFindView(@IdRes id: Int): Lazy<T> = lazy { this.findViewById<T>(id) }

inline fun ViewGroup.forEachChild(transform: (View) -> Unit) {
    (0 until childCount).map { i -> transform(getChildAt(i)) }
}

fun View.shake() = this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake))


fun <T> pickRandomN(list: List<T>?, n: Int): List<T> {
    if (list == null || list.isEmpty()) {
        return Collections.emptyList()
    }
    val targets = arrayListOf<T>()
    (0 until n).onEach { targets.add(list[Random().nextInt(list.size)]) }

    return targets
}

@SuppressLint("CommitPrefEdits")
fun Activity.prefsEditor(): Lazy<SharedPreferences.Editor> = lazy { this.getSharedPreferences(this.getString(R.string.app_name), 0).edit() }

fun editStats(vararg viaFunction: () -> Unit, editor: SharedPreferences.Editor) {
//    editor.
}