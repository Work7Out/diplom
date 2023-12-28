package diplom.gorinych.data.repository

import android.app.Application
import android.content.Context
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.SHARED_DATA
import diplom.gorinych.domain.utils.SHARE_ROLE
import diplom.gorinych.domain.utils.SHARE_USER
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(application: Application): SharedRepository {
    private val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)

    override fun getUser(): Int = sharedPref.getInt(SHARE_USER, -1)

    override fun setUser(date: Int) =
        sharedPref.edit().putInt(SHARE_USER, date).apply()

    override fun getRole(): String? = sharedPref.getString(SHARE_ROLE, "")

    override fun setRole(date: String) =
        sharedPref.edit().putString(SHARE_ROLE, date).apply()
}