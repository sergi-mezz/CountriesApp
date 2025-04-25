package com.mezzyservices.countriesapp.countrybrowser

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mezzyservices.countriesapp.R
import com.mezzyservices.countriesapp.databinding.ActivityCountryBrowserBinding
import com.mezzyservices.countriesapp.splash.SplashActivity

class CountryBrowserActivity : AppCompatActivity() {

    lateinit var view: ActivityCountryBrowserBinding
    private val viewModel: CountryBrowserViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityCountryBrowserBinding.inflate(layoutInflater)
        setContentView(view.root)
        configureObservers()
        configureToolBar()
        view.logOutFab.setOnClickListener {
            viewModel.logOut(applicationContext)
        }
    }

    private fun configureObservers() {
        viewModel.command.observe(this, ::processCommand)
    }

    private fun configureToolBar() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(view.myToolbar, navController)
        setSupportActionBar(view.myToolbar)
        view.myToolbar.setupWithNavController(navController)
    }

    private fun showSnackbar(message: String, length: Int) {
        Snackbar.make(view.coordinator, message, length).show()
    }

    private fun processCommand(command: CountryBrowserViewModel.Companion.Command) {
        when(command) {
            is CountryBrowserViewModel.Companion.Command.ShowLoading -> { showLoading() }
            is CountryBrowserViewModel.Companion.Command.RestartApp -> { restartApp() }
            is CountryBrowserViewModel.Companion.Command.ShowError -> { showError(command.message) }
            is CountryBrowserViewModel.Companion.Command.ShowDataLoaded -> { }
        }
    }

    private fun showError(message: String) {
        showSnackbar(message, Snackbar.LENGTH_LONG)
    }

    private fun restartApp() {
        showSnackbar("Cerrando sesión...", Snackbar.LENGTH_LONG)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    private fun showLoading() {
        showSnackbar(getString(R.string.catalog_welocme_message), Snackbar.LENGTH_SHORT)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

}