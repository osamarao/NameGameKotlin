package osama.me.namegamekotlin

import io.reactivex.Flowable

class ProfileRepository{
    fun profiles(): Flowable<List<Person>> = NameGameService.create().profiles()

    object ProfileRepositoryProvider{
        fun provideProfileRepository() = ProfileRepository()
    }
}