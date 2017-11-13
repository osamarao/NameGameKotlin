package osama.me.namegamekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ProfileRepository.ProfileRepositoryProvider.provideProfileRepository().profiles()
                .observeOn(AndroidSchedulers.mainThread()) // TODO ASK EXPLANATION
                .subscribeOn(Schedulers.io())
                .flatMap { Flowable.fromIterable(it) }
                .subscribe { Log.i("MainActivity", "${it.firstName} ${it.lastName}") }
    }
}