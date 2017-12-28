package osama.me.namegamekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.tatarka.redux.SimpleStore


class MainActivity : AppCompatActivity(), SimpleStore.Listener<MainState> {


    private val container by lazyFindView<ViewGroup>(R.id.face_container)
    private val xtext by lazyFindView<TextView>(R.id.xtext)

    private var pairs: ArrayList<Pair<PersonViewModel, ImageView>> = arrayListOf()
    private var targetPair: Pair<PersonViewModel, ImageView>? = null

    val mainStore = MainStore.instance.store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val faces = ArrayList<ImageView>(5)

        container.forEachChild {
            faces.add(it as ImageView)
            it.setOnClickListener {
                if ((it as ImageView).pair == targetPair) {
                    it.visibility = View.GONE
                    pairs.remove(targetPair!!)
                    setTarget()
                } else {
                    it.shake()
                }

            }
        }

        ProfileRepository.ProfileRepositoryProvider.provideProfileRepository().profiles().exec({ people ->
            people.let { pickRandomN(it, 5) }.map { it.viewmodel }.zip(faces).onEach { pairs.add(it) }.forEach {
                it.second.apply { setSize(dpToPixel(100f), dpToPixel(100f)) }.loadUrl(it.first.headshot.trueUrl)
                it.second.pair = it // ???
            }
            setTarget()
        }, { error ->
            logDebug { error.message }
        })
    }


    private fun setTarget() {
        if (!pairs.isEmpty()) {
            targetPair = pickRandomN(pairs, 1)[0]
        } else {
            targetPair = null
        }
        xtext.text = targetPair?.first?.name ?: "No Target"
    }


    override fun onStart() {
        super.onStart()
        mainStore.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        mainStore.removeListener(this)
    }

    override fun onNewState(state: MainState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}