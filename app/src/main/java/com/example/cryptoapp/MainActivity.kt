package com.example.cryptoapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var searchEditText: EditText
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var arrayModel: ArrayList<Model>
    lateinit var adapter: AdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar2)

        arrayModel = ArrayList()
        adapter = AdapterClass(this, arrayModel)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        fetchData()


    }

    private fun fetchData() {
        progressBar.isVisible = true

        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,url,null,Response.Listener<JSONObject> {
                responce ->
                try {
                    val dataArray = responce.getJSONArray("data")
                    for (i in 0 until dataArray.length()){
                        val dataObject =  dataArray.getJSONObject(i)
                        val symbol = dataObject.getString("symbol")
                        val name = dataObject.getString("name")
                        val quote = dataObject.getJSONObject("quote")
                        val USD = quote.getJSONObject("USD")
                        val price = String.format("$ %.2f", USD.getDouble("price"))

                        arrayModel.add(Model(symbol,name,price))
                    }
                    adapter.notifyDataSetChanged()
                }
                catch (e: Exception){
                    progressBar.isVisible = false
                    Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },Response.ErrorListener {
                Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getHeaders(): Map<String, String> {
               val header = HashMap<String,String>()
                header ["X-CMC_PRO_API_KEY"] = "c68f4cd5-ab57-4e45-967e-acbcd3477606"
                return header
            }

        }
        queue.add(jsonObjectRequest)
    }

}
