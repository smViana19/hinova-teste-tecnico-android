package br.com.smvtech.testetecnico.data.local.sharedpreferences

import android.content.Context
import br.com.smvtech.testetecnico.data.mocks.UserMock
import com.google.gson.Gson

class SharedPrefsServiceImpl(context: Context) : SharedPrefsService {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    override fun saveUserData(userMock: UserMock) {
        val userMockJson = gson.toJson(userMock)
        sharedPreferences.edit().putString("user_session", userMockJson).apply()
    }

    override fun getUserData(): UserMock? {
        val userMockJson = sharedPreferences.getString("user_session", null)
        if(userMockJson == null) return null
        return gson.fromJson(userMockJson, UserMock::class.java)

    }

    override fun isLoggedIn(): Boolean {
        return getUserData() != null
    }
}