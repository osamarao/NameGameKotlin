package osama.me.namegamekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    val container by lazyFindView<ViewGroup>(R.id.face_container)
    val xtext by lazyFindView<TextView>(R.id.xtext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val faces = ArrayList<ImageView>(5)

        container.forEachChild {
            faces.add(it as ImageView)
        }

        ProfileRepository.ProfileRepositoryProvider.provideProfileRepository().profiles().exec(response = { people ->
            people.let { pickRandomFive(it) }.map { it.viewmodel }.zip(faces).forEach {
                it.second.loadUrl(it.first.headshot.url!!)
            }
        }, error = { error ->
            logDebug { error.message }
        })
        ProfileRepository.ProfileRepositoryProvider.provideProfileRepository().profiles().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>?, response: Response<List<Person>>?) {
                if (response?.body() != null && response.isSuccessful) {
                    response.body().let { pickRandomFive(it) }.map { it.viewmodel }.zip(faces).forEach {
                        it.first.socialLinks?.map { it.SLtype }
                                ?.map {
                                    when (it) {
                                        FacebookSocialLink -> "FACEBOOK"
                                        TwitterSocialLink -> "TWITTER"
                                        LinkedInSocialLink -> "Linked In"
                                        is NotKnownSocialLink -> it.actual
                                        null -> "null"
                                    }
                                }
                                ?.forEach {
                                    logDebug { it }
                                }
                    }
                }
            }

            override fun onFailure(call: Call<List<Person>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }


    fun pickRandomFive(list: List<Person>?): List<Person> {
        if (list == null) {
            return Collections.emptyList()
        }
        val persons = arrayListOf<Person>()
        var count = 0
        while (count <= 5) {
            persons.add(list[Random().nextInt(list.size)])
            count++
        }
        return persons
    }


}

