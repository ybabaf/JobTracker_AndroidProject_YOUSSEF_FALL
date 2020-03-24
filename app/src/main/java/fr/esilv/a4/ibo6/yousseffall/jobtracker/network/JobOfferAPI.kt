package fr.esilv.a4.ibo6.yousseffall.jobtracker.network

import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.JobOffer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://jobs.github.com/" //Pass "positions.json?" as a parameter in the get request and "description=ruby" as query for example
const val TEST_BASE_URL = "https://jsonplaceholder.typicode.com/" //All the job offers in SF area with REACT in the description
const val TEST = "https://jobs.github.com/positions?description=python&location=new+york"
const val TEST_PARAMS = "positions.json?location=sf&description=react" //

interface JobOfferAPI {
    @GET("/posts")
    fun getPositions() : Call<List<JobOffer>>

    companion object {

        operator fun invoke() : JobOfferAPI{
            return Retrofit.Builder()
                .baseUrl(TEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JobOfferAPI::class.java)
        }
    }

}