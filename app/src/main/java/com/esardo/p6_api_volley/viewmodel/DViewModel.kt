package com.esardo.p6_api_volley.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.esardo.p6_api_volley.MainActivity.Companion.context
import com.esardo.p6_api_volley.model.Digimon

class DViewModel:ViewModel() {

    var digimonLiveData: MutableLiveData<ArrayList<Digimon>?> = MutableLiveData()

    var digimonList:ArrayList<Digimon> = ArrayList()

    fun getAllDigimonObserver(): MutableLiveData<ArrayList<Digimon>?> {
        return digimonLiveData

    }

//region API calls
    //This function will receive an Array of all Digimon objects
    fun getAllArrayJSON(){
        //Create an instances of Volley's queue
        var queue = Volley.newRequestQueue(context)

        //URL
        val url = "https://digimon-api.vercel.app/api/digimon/"

        //jsonArrayRequest Request
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                //Response is the jsonArray retrieved from de URL
                digimonList.clear()
                //iterates through the response Array
                for (i in 0 until response.length()){
                    //gets the data from every object of the array
                    var item = response.getJSONObject(i)
                    val rName = item.getString("name")
                    val rLevel = item.getString("level")
                    val rImg = item.getString("img")
                    var digimon = Digimon(rName, rImg, rLevel)
                    //add that data to the List which will fill the RecyclerView
                    digimonList.add(digimon)

                }
                digimonLiveData.postValue(digimonList)
            },
            { error ->
                error.printStackTrace()
                digimonLiveData.postValue(null)
            }
        )

        //Adding our request to the Volley's queue
        queue.add(jsonArrayRequest)
    }

    //This function will receive an Array with a single object,
    // the one of the Digimon that has been written on the query
    fun getOneArrayJSON(query:String){
        var queue = Volley.newRequestQueue(context)

        val url = "https://digimon-api.vercel.app/api/digimon/name/$query"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                digimonList.clear()
                for (i in 0 until response.length()){
                    var item = response.getJSONObject(i)
                    val rName = item.getString("name")
                    val rLevel = item.getString("level")
                    val rImg = item.getString("img")
                    var digimon = Digimon(rName, rImg, rLevel)
                    digimonList.add(digimon)
                }
                digimonLiveData.postValue(digimonList)

            },
            { error ->
                error.printStackTrace()
                digimonLiveData.postValue(null)
            }

        )

        queue.add(jsonArrayRequest)
    }

    //This function will receive an Array of Digimon objects,
    //the ones who's levels match with the query
    fun getLevelArrayJSON(query:String){
        var queue = Volley.newRequestQueue(context)

        val url = "https://digimon-api.vercel.app/api/digimon/level/$query"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                digimonList.clear()
                for (i in 0 until response.length()){
                    var item = response.getJSONObject(i)
                    val rName = item.getString("name")
                    val rLevel = item.getString("level")
                    val rImg = item.getString("img")
                    var digimon = Digimon(rName, rImg, rLevel)
                    digimonList.add(digimon)
                }
                digimonLiveData.postValue(digimonList)

            },
            { error ->
                error.printStackTrace()
                digimonLiveData.postValue(null)
            }

        )

        queue.add(jsonArrayRequest)

    }
//endregion
}