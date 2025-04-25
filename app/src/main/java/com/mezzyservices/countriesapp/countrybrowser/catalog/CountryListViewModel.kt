package com.mezzyservices.countriesapp.countrybrowser.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezzyservices.countriesapp.api.CountryApiClient
import com.mezzyservices.countriesapp.data.Country
import kotlinx.coroutines.launch

class CountryListViewModel : ViewModel() {

    val command = MutableLiveData<Command>()
    val countryList = MutableLiveData(listOf<Country>())
    val error = MutableLiveData("")

    fun getCountryList() {

        if(countryList.value?.isNotEmpty() == true) {
            command.value = Command.ShowSuccessfulResponse
        } else {
            command.value = Command.ShowLoading
            val service = CountryApiClient.countryService
            viewModelScope.launch {
                try {
                    countryList.postValue(service.getCountryList())
                    command.postValue(Command.ShowSuccessfulResponse)
                } catch (e: Exception) {
                    error.postValue(e.message)
                    command.postValue(Command.ShowError(e.message ?: "Error al consultar servicio"))
                }
            }
        }
    }

    companion object {
        sealed class Command {
            object ShowSuccessfulResponse: Command()
            class ShowError(val message: String): Command()
            object ShowLoading: Command()
        }
    }

}