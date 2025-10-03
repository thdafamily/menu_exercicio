package com.thiago.navigationdrawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.thiago.navigationdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_inicio -> openFragment(InicioFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.bottom_run -> openFragment(RunClubFragment())
                R.id.bottom_config -> openFragment(ConfigFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager
        openFragment(NikeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Categorias", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this){
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else{
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_nike -> openFragment(NikeFragment())
            R.id.nav_esporte -> openFragment(EsporteFragment())
            R.id.nav_roupa -> openFragment(RoupaFragment())
            R.id.nav_acess -> Toast.makeText(this, "Acessórios", Toast.LENGTH_SHORT).show()
            R.id.nav_calcado -> Toast.makeText(this, "Calçados", Toast.LENGTH_SHORT).show()
            R.id.nav_lancamento -> Toast.makeText(this, "Lançamentos", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}