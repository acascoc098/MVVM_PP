package com.example.proyectopersonalizado.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectopersonalizado.R
import com.example.proyectopersonalizado.objets_models.Login
import com.example.proyectopersonalizado.objets_models.Preferencias
import com.example.proyectopersonalizado.ui.fragments.FragmentConfiguracionDirections
import com.example.proyectopersonalizado.ui.fragments.FragmentInformacionDirections
import com.example.proyectopersonalizado.ui.fragments.FragmentListDirections
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var textomenu1: TextView
    private lateinit var textomenu2: TextView
    private lateinit var preferencias: Preferencias


    @Inject
    lateinit var pruebaHilt: QuePasa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val chiste = pruebaHilt.cuenta()
        Toast.makeText( this, chiste, Toast. LENGTH_LONG).show()


        val intent = intent
        val usuario = intent.getStringExtra("usuario") // Obtener el nombre de usuario

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        preferencias = Preferencias(this)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //drawerLayout = findViewById(R.id.fragment_container)
        drawerLayout = findViewById(R.id.fragment_container)
        navigationView = findViewById(R.id.navigationView)

        // Inflar el encabezado del menú
        val headerView = navigationView.getHeaderView(0)

        textomenu1 = headerView.findViewById(R.id.textomenu1)
        textomenu2 = headerView.findViewById(R.id.textomenu2)

        textomenu1.text = usuario
        textomenu2.text = usuario + "@ejemplo.com"

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val currentFragmentId = navController.currentDestination?.id

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.fragmentList)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_gallery -> {
                    if (currentFragmentId == R.id.fragmentList) {
                        val action = FragmentListDirections.actionFragmentListToFragmentHotels(
                            nombre = "Andrea Castilla Cocera",
                            curso = "Programación Multimedia"
                        )
                        navController.navigate(action)
                    } else if (currentFragmentId == R.id.fragmentConfiguracion) {
                        val action = FragmentConfiguracionDirections.actionFragmentConfiguracionToFragmentHotels(
                            nombre = "Andrea Castilla Cocera",
                            curso = "Programación Multimedia"
                        )
                        navController.navigate(action)
                    }else if  (currentFragmentId == R.id.fragmentInformacion) {

                        val action = FragmentInformacionDirections.actionFragmentInformacionToFragmentHotels(
                            nombre = "Andrea Castilla Cocera",
                            curso = "Programación Multimedia"
                        )
                        navController.navigate(action)
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout -> {
                    preferencias.borrarPreferencias()
                    finishAffinity()
                    true
                }
                else -> false
            }
        }



        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    // Método que es llamado después de crear la vista del activity.
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // Para controlar los eventos de los items del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_list -> {
                navController.navigate(R.id.fragmentList)
                true
            }
            R.id.toolbar_configuration -> {
                navController.navigate(R.id.fragmentConfiguracion)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}



