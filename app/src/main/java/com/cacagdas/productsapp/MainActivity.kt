package com.cacagdas.productsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.cacagdas.productsApp.R
import com.cacagdas.productsApp.databinding.ActivityMainBinding
import com.cacagdas.productsapp.core.widget.WidgetToolbar
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        toolbar = binding.toolbar
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setSupportActionBar(toolbar)
        navController = navHostFragment.navController
        navController?.let { setupActionBarWithNavController(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() == true
    }

    fun updateToolbar(widgetToolbar: WidgetToolbar) {
        toolbar.apply {
            title = widgetToolbar.title
            menu.clear()
            widgetToolbar.updateToolbarMenu()
        }
    }

    private fun WidgetToolbar.updateToolbarMenu() {
        menu?.let { menuList ->
            menuList.forEachIndexed { index, toolbarMenu ->
                val menuTitle = toolbarMenu.title
                val menuItem = toolbar.menu.add(Menu.NONE, index, Menu.NONE, menuTitle)
                    .setShowAsActionFlags(SHOW_AS_ACTION_IF_ROOM)
                toolbarMenu.icon?.let {
                    menuItem.icon = AppCompatResources.getDrawable(this@MainActivity, it)
                }
            }
            toolbar.setOnMenuItemClickListener {
                menuList.getOrNull(it.itemId)?.action?.invoke()
                true
            }
        }
    }
}
