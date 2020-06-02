package com.example.Zapasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.adapters.ViewPagerAdapter
import com.example.Zapasy.dialogs.CreateDialog
import com.example.Zapasy.fragments.Categorias
import com.example.Zapasy.fragments.Inicio
import com.example.Zapasy.fragments.Productos
import com.example.Zapasy.interfaces.CreateListener
import com.example.Zapasy.room.CategoriasRepository
import com.example.Zapasy.room.MarcaRepository
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), CreateListener {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs : TabLayout
    private lateinit var buttonAddProduct: FloatingActionButton
    private lateinit var buttonAddMarca: FloatingActionButton
    private lateinit var buttonAddCategorie: FloatingActionButton
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fixElevationTabBar()
        loadTabandViewPager()
        funcionalityFloatingMenu()
        coordinatorLayout = findViewById(R.id.coordinatorMainActivity)
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
        }
        buttonAddMarca.setOnClickListener{
            val dialog = CreateDialog("Ingresa el nombre de la marca",CreateDialog.CREATE_MARCA, this)
            dialog.show(supportFragmentManager,null)
        }
        buttonAddCategorie.setOnClickListener{
            val dialog = CreateDialog("Ingresa el nombre de la categoría", CreateDialog.CREATE_CATEGORIA, this)
            dialog.show(supportFragmentManager,null)
        }
    }

    override fun createMarca(marca: Marca) {
        MarcaRepository(application).insert(marca)
        Snackbar.make(coordinatorLayout,"Has añadido una marca", Snackbar.LENGTH_LONG).show()
    }

    override fun createCategoria(categoria: Categoria) {
        CategoriasRepository(application).insert(categoria)
        Snackbar.make(coordinatorLayout,"Has añadido una categoría", Snackbar.LENGTH_LONG).show()
    }

}
