package com.mezzyservices.countriesapp.countrybrowser.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezzyservices.countriesapp.api.CountryApiClient
import com.mezzyservices.countriesapp.data.Country
import kotlinx.coroutines.launch

class CountryDetailViewModel: ViewModel() {

    val command = MutableLiveData<Command>()
    var countryData: Country? = null

    fun fetchCountryData(name: String) {
        if(countryData != null) {
            command.postValue(Command.ShowSuccessfulResponse)
        } else {
            command.value = Command.ShowLoading
            val service = CountryApiClient.countryService
            viewModelScope.launch {
                try {
                    countryData = service.getCountry(name).first()
                    command.postValue(Command.ShowSuccessfulResponse)
                } catch (e: Exception) {
                    command.postValue(Command.ShowError(e.message ?: "Error al consumir servicio"))
                }
            }
        }
    }

    companion object {
        sealed class Command() {
            object ShowSuccessfulResponse: Command()
            object ShowLoading: Command()
            class ShowError(val message: String): Command()
        }
    }
}