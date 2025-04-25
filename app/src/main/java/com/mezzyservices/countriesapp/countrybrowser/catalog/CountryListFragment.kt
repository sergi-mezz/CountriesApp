package com.mezzyservices.countriesapp.countrybrowser.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mezzyservices.countriesapp.R
import com.mezzyservices.countriesapp.countrybrowser.CountryBrowserViewModel
import com.mezzyservices.countriesapp.databinding.FragmentCountryListBinding

class CountryListFragment : Fragment() {

    private val viewModel: CountryListViewModel by viewModels()
    private val activityViewModel: CountryBrowserViewModel by activityViewModels()
    private lateinit var view: FragmentCountryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentCountryListBinding.inflate(layoutInflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
        configureObservers()
        viewModel.getCountryList()
    }

    private fun configureView() {
        with(view) {
            countryList.layoutManager = GridLayoutManager(context, 2)
            countryList.adapter = CountryListAdapter(viewModel.countryList.value!!, ::onCardClicked)
        }
    }

    private fun onCardClicked(name: String) {
        findNavController().navigate(R.id.Catalog_to_Detail, Bundle().apply {
            putString("country", name)
        })
    }

    private fun configureObservers() {
        viewModel.command.observe(viewLifecycleOwner, ::processCommand)
    }

    private fun processCommand(command: CountryListViewModel.Companion.Command) {
        when(command) {
            is CountryListViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is CountryListViewModel.Companion.Command.ShowSuccessfulResponse -> { showCountries() }
            is CountryListViewModel.Companion.Command.ShowError -> { showError(command.message) }
        }
    }

    private fun showLoading() {
        activityViewModel.command.value = CountryBrowserViewModel.Companion.Command.ShowLoading
        view.progressBar.visibility = View.VISIBLE
        view.countryList.visibility = View.GONE
    }

    private fun showCountries() {
        activityViewModel.command.value = CountryBrowserViewModel.Companion.Command.ShowDataLoaded
        view.progressBar.visibility = View.GONE
        view.countryList.visibility = View.VISIBLE
        view.countryList.layoutManager = GridLayoutManager(context, 2)
        view.countryList.adapter = CountryListAdapter(
            viewModel.countryList.value!!,
            ::onCardClicked
        )
    }

    private fun showError(error: String) {
        activityViewModel.command.value = CountryBrowserViewModel.Companion.Command.ShowError(error)
        view.progressBar.visibility = View.GONE
        Snackbar.make(view.root, error, Snackbar.LENGTH_SHORT).show()
    }

}