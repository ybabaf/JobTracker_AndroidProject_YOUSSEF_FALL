package fr.esilv.a4.ibo6.yousseffall.jobtracker.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R
import fr.esilv.a4.ibo6.yousseffall.jobtracker.adapter.JobOffersAdapter
import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.JobOffer
import fr.esilv.a4.ibo6.yousseffall.jobtracker.network.JobOfferAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(findViewById(R.id.activity_main_toolbar))

        //Whenever we swipe down (to refresh the page) it will recall fetchJobOffers() to get the latest offers available
        refreshLayout.setOnRefreshListener {
            fetchJobOffers()
        }

        //to fetch the offers whenever the ui is displayed
        fetchJobOffers()
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // - Inflate the menu and add it to the Toolbar
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //3 - Handle actions on menu items
        return when (item.getItemId()) {
            R.id.menu_activity_options -> {
                Toast.makeText(
                    this,
                    "Il n'y a rien à paramétrer ici, passez votre chemin...",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            R.id.menu_activity_search -> {
                Toast.makeText(
                    this,
                    "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
*/
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
                val jobOffers = response.body()
                println("\n\n $response")
                //println("message " + response.message())



                // --- Debug --
                println("$$$!$!$!$$!$!!$$!$!$$$!$!$!$$!$!!!$!$!$!!$$!$!!$!$$!$!$$!!")
                println(":: Response Body ::")
                println(jobOffers)



                jobOffers?.let{
                    showJobOffers(jobOffers)
                }

            }

        } )
    }


    private fun showJobOffers(jobOffers: List<JobOffer>) {
        recyclerViewOffers.layoutManager = LinearLayoutManager(this)
        recyclerViewOffers.adapter = JobOffersAdapter(jobOffers)
    }
}