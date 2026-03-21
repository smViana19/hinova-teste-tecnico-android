package br.com.smvtech.testetecnico.data.local.sharedpreferences

import br.com.smvtech.testetecnico.data.mocks.UserMock

interface SharedPrefsService {
    fun saveUserData(userMock: UserMock)
    fun getUserData(): UserMock?
    fun isLoggedIn(): Boolean

}