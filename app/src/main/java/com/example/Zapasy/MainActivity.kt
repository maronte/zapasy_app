package com.example.Zapasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.Zapasy.adapters.ViewPagerAdapter
import com.example.Zapasy.dialogs.CreateDialog
import com.example.Zapasy.fragments.Categorias
import com.example.Zapasy.fragments.Inicio
import com.example.Zapasy.fragments.Productos
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs : TabLayout
    private lateinit var buttonAddProduct: FloatingActionButton
    private lateinit var buttonAddMarca: FloatingActionButton
    private lateinit var buttonAddCategorie: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fixElevationTabBar()
        loadTabandViewPager()
        funcionalityFloatingMenu()
    }
    fun fixElevationTabBar(){
        val toolbar = getSupportActionBar()
        toolbar?.setElevation(0f)
    }
    fun loadTabandViewPager(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragments(Inicio())
        adapter.addFragments(Productos())
        adapter.addFragments(Categorias())
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter
        tabs = findViewById(R.id.tabLayout)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
    fun funcionalityFloatingMenu(){
        buttonAddProduct = findViewById(R.id.button_add_product)
        buttonAddMarca = findViewById(R.id.button_add_categorie)
        buttonAddCategorie = findViewById(R.id.button_add_group)
        buttonAddProduct.setOnClickListener{
            val intent = Intent(this,CrearProductoActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"Vas a crear un producto",Toast.LENGTH_SHORT).show()
        }
        buttonAddMarca.setOnClickListener{
            //val intent = Intent(this,CrearEditarMarcaActivity::class.java)
            //intent.putExtra("Accion",1)
            //startActivity(intent)
            //Toast.makeText(this,"Vas a crear una categoría",Toast.LENGTH_SHORT).show()
            val dialog = CreateDialog("Ingresa el nombre de la marca")
            dialog.show(supportFragmentManager,null)
        }
        buttonAddCategorie.setOnClickListener{
            //val intent = Intent(this,CrearEditarCategoriaActivity::class.java)
            //intent.putExtra("Accion",1)
            //startActivity(intent)
            //Toast.makeText(this,"Vas a crear una categoría",Toast.LENGTH_SHORT).show()
            val dialog = CreateDialog("Ingresa el nombre de la categoría")
            dialog.show(supportFragmentManager,null)
        }
    }
}
