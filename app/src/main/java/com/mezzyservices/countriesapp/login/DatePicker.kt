package com.mezzyservices.countriesapp.login

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.mezzyservices.countriesapp.R
import java.util.Calendar

class DatePicker: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: LoginViewModel by navGraphViewModels(R.id.login_graph)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = viewModel.birthDate
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month,day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.birthDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month + 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
        viewModel.command.value = LoginViewModel.Companion.Command.SetDate
    }

}