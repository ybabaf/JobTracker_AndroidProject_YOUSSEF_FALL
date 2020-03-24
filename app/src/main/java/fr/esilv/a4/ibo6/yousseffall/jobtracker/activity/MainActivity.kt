package fr.esilv.a4.ibo6.yousseffall.jobtracker.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R
import fr.esilv.a4.ibo6.yousseffall.jobtracker.adapter.JobOffersAdapter
import fr.esilv.a4.ibo6.yousseffall.jobtracker.adapter.ProgrammingLanguageAdapter
import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.JobOffer
import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.ProgrammingLanguage
import fr.esilv.a4.ibo6.yousseffall.jobtracker.network.JobOfferAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var jobOffersSearch:MutableList<JobOffer> = ArrayList()
    var jobOffers:MutableList<JobOffer> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.activity_main_toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        //Whenever we swipe down (to refresh the page) it will recall fetchJobOffers() to get the latest offers available
        refreshLayout.setOnRefreshListener {
            fetchJobOffers()
        }

        showProgLanguages(fetchProgLanguages())
        //to fetch the offers whenever the ui is displayed
        fetchJobOffers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // - Inflate the menu and add it to the Toolbar
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_toolbar, menu)

        val searchItem = menu.findItem(R.id.menu_activity_search)
        if(searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        jobOffersSearch.clear()
                        val search = newText.toLowerCase()
                        jobOffers.forEach {
                            if (it.description.toLowerCase().contains(search)) {
                                jobOffersSearch.add(it)
                            }
                        }
                        showJobOffers(jobOffersSearch)
                    } else {
                        showJobOffers(jobOffers)
                    }
                    return true
                }

            })
        }

        return true
    }

    //Fetches all Offers (Positions) from the API
    private fun fetchJobOffers() {
        refreshLayout.isRefreshing = true;

        JobOfferAPI().getPositions().enqueue(object: Callback<List<JobOffer>> {

            override fun onFailure(call: Call<List<JobOffer>>, t: Throwable) {
                refreshLayout.isRefreshing = false;
                // --- Debug ---
                println("$$$!$!$!$$!$!!$$!$!$$$!$!$!$$!$!!!$!$!$!!$$!$!!$!$$!$!$$!!")
                println(":: API call failed ::")
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<JobOffer>>, response: Response<List<JobOffer>>) {
                refreshLayout.isRefreshing = false;
                jobOffers = response.body() as ArrayList
                println("\n\n $response")
                //println("message " + response.message())
                // --- Debug --
                println("$$$!$!$!$$!$!!$$!$!$$$!$!$!$$!$!!!$!$!$!!$$!$!!$!$$!$!$$!!")
                println(":: Response Body ::")
                println(jobOffers)

                jobOffers.let{
                    showJobOffers(jobOffers)
                }

            }

        } )
    }

    fun fetchProgLanguages(): List<ProgrammingLanguage> {
        //Creating languages manually to test
        val python = ProgrammingLanguage("Python", R.drawable.python)
        val java = ProgrammingLanguage("Java", R.drawable.java)
        val ruby = ProgrammingLanguage("Ruby", R.drawable.ruby)
        val csharp = ProgrammingLanguage("C#", R.drawable.csharp)
        val javascript = ProgrammingLanguage("JavaScript", R.drawable.javascript)
        val swift = ProgrammingLanguage("Swift", R.drawable.swift)
        val angular = ProgrammingLanguage("AngularJS", R.drawable.angular)
        val node = ProgrammingLanguage("nodeJS", R.drawable.nodejs)

        val progLanguages = listOf(python, java, ruby, csharp, javascript, swift, angular,  node)
        return progLanguages
    }


    private fun showJobOffers(jobOffers: List<JobOffer>) {
        recyclerViewOffers.layoutManager = LinearLayoutManager(this)
        recyclerViewOffers.adapter = JobOffersAdapter(jobOffers)
    }

    private fun showProgLanguages(progLanguages: List<ProgrammingLanguage>) {
        recyclerViewLanguages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewLanguages.adapter = ProgrammingLanguageAdapter(progLanguages)
    }
}