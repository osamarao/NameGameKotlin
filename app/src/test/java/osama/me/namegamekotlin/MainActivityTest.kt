package osama.me.namegamekotlin

import android.widget.ImageView
import junit.framework.Assert.assertNotNull
import org.junit.Test
import java.util.*


class MainActivityTest {

    @Test
    fun givenLisOfTargetPairsReturnRandomPair() {
        val pairs: ArrayList<Pair<PersonViewModel, ImageView>> = arrayListOf()
        val person = Person("", "", "", "", "", "",
                Headshot("", "", "", null, "", 0, 3),
                null)

        pairs.add(Pair(person.viewmodel, ImageView(null)))
        pairs.add(Pair(person.viewmodel, ImageView(null)))
        pairs.add(Pair(person.viewmodel, ImageView(null)))
        pairs.add(Pair(person.viewmodel, ImageView(null)))
        pairs.add(Pair(person.viewmodel, ImageView(null)))

        val randomPair = pickRandomN(pairs, 1)[0]
        assertNotNull("not null", randomPair )
    }
}