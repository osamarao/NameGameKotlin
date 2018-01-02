package osama.me.namegamekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import tw.geothings.rekotlin.Store
import tw.geothings.rekotlin.StoreSubscriber

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    private val container by lazyFindView<ViewGroup>(R.id.face_container)
    private val xtext by lazyFindView<TextView>(R.id.xtext)

    //        private var pairs_: ArrayList<Pair<PersonViewModel, ImageView>> = arrayListOf()
    private var targetPair: Pair<PersonViewModel, ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val faces = ArrayList<ImageView>(5)

        container.forEachChild {
            faces.add(it as ImageView)
            it.setOnClickListener {
                if ((it as ImageView).pair == targetPair) {
                    it.visibility = View.GONE
                    mainStore.dispatch(RemovePairAction(it.pair))
                } else {
                    it.shake()
                }

            }
        }
        mainStore.subscribe(this)
        profilePairsActionCreator(faces, this)
    }

    private fun setTarget(pairs: List<Pair<PersonViewModel, ImageView>>) {
        targetPair = if (!pairs.isEmpty()) {
            pickRandomN(pairs, 1)[0]
        } else {
            null
        }
        xtext.text = targetPair?.first?.name ?: "No Target"
    }


    override fun newState(state: AppState) {
        state.pairs.forEach {
            logDebug { it.first.name }
            it.second.apply { setSize(dpToPixel(100f), dpToPixel(100f)) }.loadUrl(it.first.headshot.trueUrl)
            it.second.pair = it // add to tag of imageview
        }
        setTarget(state.pairs)
    }

    override fun onStop() {
        super.onStop()
        mainStore.unsubscribe(this)
    }

}

val mainStore = Store(
        reducer = ::pairReducer,
        state = null
)