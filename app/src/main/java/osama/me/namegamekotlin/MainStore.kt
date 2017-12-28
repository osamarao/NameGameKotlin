package osama.me.namegamekotlin

import android.widget.ImageView
import me.tatarka.redux.Dispatcher
import me.tatarka.redux.Reducer
import me.tatarka.redux.Reducers
import me.tatarka.redux.SimpleStore

class MainStore(val store: SimpleStore<MainState>, val dispatcher: Dispatcher<Action, Action>) {

    companion object {
        val instance = MainStore(mainStore, dispatcher)
    }


    fun dispatch(action: Action): Action {
        return dispatcher.dispatch(action)
    }

    fun getState(): MainState {
        return store.state
    }

    fun addListener(listener: SimpleStore.Listener<MainState>) {
        store.addListener(listener)
    }

    fun removeListener(listener: SimpleStore.Listener<MainState>) {
        store.removeListener(listener)
    }
}

interface Action

class SomeAction (var pair: Pair<PersonViewModel, ImageView> ): Action

val mainStore: SimpleStore<MainState> = SimpleStore(MainState(arrayListOf()))

val dispatcher: Dispatcher<Action, Action> = Dispatcher.forStore(MainStore.instance.store, mainReducer())


fun mainReducer(): Reducer<Action, MainState> {
    return Reducers.matchClass<Action, MainState>().`when`(SomeAction::class.java, specificReducer())
}


fun specificReducer(): Reducer<SomeAction, MainState> {
    return Reducer { action, state -> removePairFromState(action.pair, state) }
}

fun removePairFromState(  pairToBeRemoved: Pair<PersonViewModel, ImageView>,  state : MainState) : MainState{

    state.pairs.remove(pairToBeRemoved)

    return state.copy(pairs = state.pairs)
}