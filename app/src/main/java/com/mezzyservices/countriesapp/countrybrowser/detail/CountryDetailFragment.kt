package com.mezzyservices.countriesapp.countrybrowser.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mezzyservices.countriesapp.R
import com.mezzyservices.countriesapp.data.Country
import com.mezzyservices.countriesapp.databinding.FragmentCountryDetailBinding

class CountryDetailFragment : Fragment() {

    lateinit var view: FragmentCountryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentCountryDetailBinding.inflate(layoutInflater, container, false)
        return view.root
    }

    private val args: CountryDetailFragmentArgs by navArgs()
    private val viewModel: CountryDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.data)
        configureObservers()
        viewModel.fetchCountryData(args.country)
    }

    private fun configureObservers() {
        viewModel.command.observe(viewLifecycleOwner, ::processCommand)
    }

    private fun processCommand(command: CountryDetailViewModel.Companion.Command) {
        when(command) {
            is CountryDetailViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is CountryDetailViewModel.Companion.Command.ShowSuccessfulResponse -> { viewModel.countryData?.let { showData(it) } }
            is CountryDetailViewModel.Companion.Command.ShowError -> { showError(command.message) }
        }
    }

    private fun showLoading() {
        view.contentLayout.visibility = View.GONE
        view.progressBar.visibility = View.VISIBLE
    }


    private fun showData(country: Country) {
        with(view) {
            Glide.with(root.context).load(country.flags.png).into(flagImageView)
            nameTextView.text = country.name.common
            populationTextView.text = "Población ${country.population}"
            progressBar.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        Snackbar.make(view.root, message, Snackbar.LENGTH_SHORT).show()
        view.progressBar.visibility = View.GONE
    }
}