@file:Suppress("UNCHECKED_CAST")

package osama.me.namegamekotlin

import android.widget.ImageView
import tw.geothings.rekotlin.Action
import tw.geothings.rekotlin.StateType

data class AppState(val pairs: ArrayList<Pair<PersonViewModel, ImageView>> = arrayListOf()) : StateType


data class ReplacePairsAction(val pairs: List<Pair<PersonViewModel, ImageView>>) : Action
data class RemovePairAction(val pair: Pair<PersonViewModel, ImageView>) : Action

val reducers: List<(Action, AppState?) -> AppState> = listOf(::someReducer)

fun reducer(action: Action, state: AppState?): AppState {

    val result: AppState? = reducers.fold(state, { acc: AppState?, reduceFunction: (Action, AppState?) -> AppState -> reduceFunction(action, acc) })
    return result!!
}

fun someReducer(action: Action, state: AppState?): AppState {
    var stateInternal = state ?: AppState()
    when (action) {
        is ReplacePairsAction -> {
            if (state != null) {
                stateInternal = state.copy(pairs = action.pairs as ArrayList<Pair<PersonViewModel, ImageView>>)
            }
        }
        is RemovePairAction -> {
            if (state != null) {
                val clonePairs = state.pairs.clone() as ArrayList<Pair<PersonViewModel, ImageView>>
                clonePairs.remove(action.pair)
                stateInternal = state.copy(pairs = clonePairs)
            }
        }
    }

    return stateInternal
}