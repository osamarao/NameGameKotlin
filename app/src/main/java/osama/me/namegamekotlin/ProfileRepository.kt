package osama.me.namegamekotlin

import retrofit2.Call

class ProfileRepository {
    fun profiles(): Call<List<Person>> = NameGameService.create().profiles()

    object ProfileRepositoryProvider {
        fun provideProfileRepository() = ProfileRepository()
    }
}