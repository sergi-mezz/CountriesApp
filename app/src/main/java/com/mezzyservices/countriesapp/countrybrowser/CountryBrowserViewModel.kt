package com.mezzyservices.countriesapp.countrybrowser

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.mezzyservices.countriesapp.data.CountriesAppDatabase

class CountryBrowserViewModel: ViewModel() {

    val command = MutableLiveData<Command>(Command.ShowLoading)

    fun logOut(context: Context) {
        command.value = Command.ShowLoading
        val dao = CountriesAppDatabase.getDatabase(context).userDao()
        viewModelScope.launch {
            try {
                val user = dao.getUsers().first()
                dao.deleteUser(user)
                command.postValue(Command.RestartApp)
            } catch (e: Exception) {
                command.postValue(Command.ShowError(e.message ?: "Error al cerrar sesión"))
            }
        }
    }

    companion object {
        sealed class Command {
            object RestartApp: Command()
            class ShowError(val message: String): Command()
            object ShowLoading:Command()
            object ShowDataLoaded: Command()
        }
    }

}