package com.mezzyservices.countriesapp.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezzyservices.countriesapp.data.CountriesAppDatabase
import com.mezzyservices.countriesapp.data.User
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class LoginViewModel: ViewModel() {

    val command = MutableLiveData<Command>()
    var birthDate: Calendar = Calendar.getInstance().apply { time = Date() }

    fun saveUser(user: User, applicationContext: Context) {
        val dao = CountriesAppDatabase.getDatabase(applicationContext).userDao()
        viewModelScope.launch {
            try {
                dao.saveUser(user)
                command.postValue(Command.NavigateToCatalog)
            } catch (e: Exception) {
                command.postValue(Command.ShowError(e.message ?: "Error al guardar usuario"))
            }
        }
    }

    companion object {
        sealed class Command(){
            object NavigateToCatalog: Command()
            class ShowError(val message: String): Command()
            object SetDate: Command()
        }
    }


}