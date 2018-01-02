package osama.me.namegamekotlin

import android.content.Context
import android.widget.ImageView


fun profilePairsActionCreator(faces: ArrayList<ImageView>, ctx: Context) {

    ProfileRepository.ProfileRepositoryProvider.provideProfileRepository().profiles().exec(
            { people ->
                val pairs = people.let { pickRandomN(it, 5) }.map { it.viewmodel }.zip(faces)
                mainStore.dispatch(buildAddPairAction(pairs))
            },
            { error ->
                ctx.logDebug { error.message }
            }
    )

}

fun buildAddPairAction(pairs: List<Pair<PersonViewModel, ImageView>>): ReplacePairsAction {
    return ReplacePairsAction(pairs)
}