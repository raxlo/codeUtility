package com.zeroplus.codeutility


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.zeroplus.codeutility.databinding.ActivityMainBinding
import com.zeroplus.codeutility.utility.pxdpconverter.CustomPxDpDialog
import com.zeroplus.codeutility.utility.segmented.CustomSegmentedDialog
import com.zeroplus.codeutility.utility.segmented.CustomSegmentedDialog2


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var codeSelectID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val code_description = resources.getStringArray(R.array.code_description)
/*
        ArrayAdapter.createFromResource(
            this,
            R.array.code_name,
            R.layout.cunsom_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spinner.adapter=adapter
        }*/



        val adapter = ArrayAdapter.createFromResource(this, R.array.code_name, android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                codeSelectID = position
                binding.textView.text = code_description[codeSelectID]
            }
        }

        binding.button.setOnClickListener {
            when (codeSelectID) {
                0 -> { CustomPxDpDialog().show(supportFragmentManager, "customPxDpDialog")}
                1 -> { CustomSegmentedDialog2().show(supportFragmentManager, "customSegmentedDialog2")}
            }
        }
/*
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        */
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    */
}