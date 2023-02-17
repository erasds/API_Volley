package com.esardo.p6_api_volley

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esardo.p6_api_volley.adapter.DigimonAdapter
import com.esardo.p6_api_volley.databinding.ActivityMainBinding
import com.esardo.p6_api_volley.model.Digimon
import com.esardo.p6_api_volley.viewmodel.DViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DigimonAdapter
    private lateinit var viewModel: DViewModel

    private val digimonList = ArrayList<Digimon>()

    //Like this it can be imported from another class
    companion object {
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.root)

        context = applicationContext

        viewModel = ViewModelProvider(this)[DViewModel::class.java]
        viewModel.getAllDigimonObserver()
        //This will observe the digimonList of the DViewModel class and load the necessary data into the recyclerview
        //everytime that the activity starts
        viewModel.digimonLiveData.observe(this){
            digimonList.clear()
            if (it != null) {
                digimonList.addAll(it)
            }
            adapter.notifyDataSetChanged()
        }

        //this will get the text of the searchView and set it as the query variable
        binding.svDigimon.setOnQueryTextListener(this)

        //When button Load is clicked it calls to the getAllArrayJson function
        binding.btnLoad.setOnClickListener{
            viewModel.getAllArrayJSON()
            //Hides keyboard
            hideKeyboard()
        }

        initRecyclerView()
    }
//region Other functions
    //Setups the RecyclerView
    private fun initRecyclerView() {
        adapter = DigimonAdapter(digimonList)
        recyclerView = binding.rvDigimon
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    //Function to hide the Keyboard
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
//endregion

//region SearchView functions
    //It controls when the text of the SearchView changes
    override fun onQueryTextChange(newText:String?):Boolean {
        return true
    }

    //When a submit is made calls the API functions that are declared in the DViewModel class
    override fun onQueryTextSubmit(query: String?):Boolean {
        if(!query.isNullOrEmpty()){
            viewModel.getOneArrayJSON(query.lowercase())
            viewModel.getLevelArrayJSON(query.lowercase())
            hideKeyboard()
        }
        return true
    }
//endregion

}