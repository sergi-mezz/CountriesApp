package com.mezzyservices.countriesapp.login

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.mezzyservices.countriesapp.R
import com.mezzyservices.countriesapp.countrybrowser.CountryBrowserActivity
import com.mezzyservices.countriesapp.data.User
import com.mezzyservices.countriesapp.databinding.FragmentLoginBinding
import java.util.Calendar

class LoginFragment: Fragment() {

    lateinit var view: FragmentLoginBinding
    private val viewModel: LoginViewModel by navGraphViewModels(R.id.login_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
        configureObservers()
    }

    private fun configureView() {
        view.birthdateTextView.onFocusChangeListener = null
        view.birthdateTextView.setOnClickListener {
            val datePicker = DatePicker()
            datePicker.show(parentFragmentManager, "datePicker")
        }
        configureSpinner()
        view.signUpButton.setOnClickListener(::signUpUser)
    }

    private fun signUpUser(view: View) {
        this.view.progressBar.visibility = View.VISIBLE
        this.view.loginFormLayout.visibility = View.GONE
        if(isDataValid()) {
            viewModel.saveUser(createUser(), requireActivity().applicationContext)
        } else {
            this.view.progressBar.visibility = View.GONE
            this.view.loginFormLayout.visibility = View.VISIBLE
            Snackbar.make(this.view.parentLayout, "Revisar errores en formulario", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun createUser(): User {
        with(view) {
            return User(
                name = nameEditText.text.toString(),
                paternalSurname = lastNameEditText.text.toString(),
                maternalSurname = motherSurnameEditText.text.toString(),
                birthDate = birthdateTextView.text.toString(),
                country = countrySpinner.selectedItem.toString()
            )
        }
    }

    private fun isDataValid(): Boolean {

        var valid = true
        if(view.nameEditText.text!!.isEmpty()) {
            view.nameEditText.error = "Campo vacío"
            valid = false
        }
        if(view.lastNameEditText.text!!.isEmpty()) {
            view.lastNameEditText.error = "Campo vacío"
            valid = false
        }
        if(view.motherSurnameEditText.text!!.isEmpty()) {
            view.motherSurnameEditText.error = "Campo vacío"
            valid = false
        }
        valid = valid and isDateValid()
        return valid
    }

    private fun isDateValid(): Boolean {
        if(view.birthdateTextView.text?.isEmpty() == true) {
            view.birthdateTextView.error = "Campo vacío"
            return false
        }
        val referenceDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2019)
        }
        if(viewModel.birthDate > referenceDate) {
            view.birthdateTextView.error = "Fecha inválida"
            return false
        }
        return true
    }

    private fun configureSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.countrySpinner.adapter = adapter
        }
    }

    private fun configureObservers() {
        viewModel.command.observe(viewLifecycleOwner, ::processCommand)
    }

    private fun processCommand(command: LoginViewModel.Companion.Command) {
        when(command) {
            is LoginViewModel.Companion.Command.NavigateToCatalog -> { navigateToCatalog() }
            is LoginViewModel.Companion.Command.ShowError -> { showError(command.message) }
            is LoginViewModel.Companion.Command.SetDate -> { showDate() }
        }
    }

    private fun navigateToCatalog() {
        val activity = requireActivity()
        activity.startActivity(Intent(requireContext(), CountryBrowserActivity::class.java))
        activity.finish()
    }

    private fun showError(message: String) {
        Snackbar.make(this.view.parentLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showDate() {
        with(viewModel.birthDate) {
            val dateString = "${get(Calendar.DAY_OF_MONTH)}/${get(Calendar.MONTH)}/${get(Calendar.YEAR)}"
            view.birthdateTextView.setText("Fecha de nacimiento\n${dateString}", TextView.BufferType.EDITABLE)
        }
    }

}