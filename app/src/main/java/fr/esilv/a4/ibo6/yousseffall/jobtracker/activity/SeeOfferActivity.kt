package fr.esilv.a4.ibo6.yousseffall.jobtracker.activity

import android.nfc.Tag
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Picasso
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R

class SeeOfferActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_offer)

        /*
        Getting extra values from intent
         */
        val intent = intent
        val title = intent.getStringExtra("JOB_OFFER_TITLE")
        val company = intent.getStringExtra("JOB_OFFER_COMP")
        val image = intent.getStringExtra("JOB_OFFER_IMAGE")
        val location = intent.getStringExtra("JOB_OFFER_LOC")
        val type = intent.getStringExtra("JOB_OFFER_TYPE")
        val description = intent.getStringExtra("JOB_OFFER_DESC")

        findViewById<TextView>(R.id.textViewTitle).text = title
        findViewById<TextView>(R.id.textViewCompany).text = company
        findViewById<TextView>(R.id.textViewLocation).text = location
        findViewById<TextView>(R.id.textViewType).text = type
        val txtViewDesc = findViewById<TextView>(R.id.textViewDescription)
        txtViewDesc.text = HtmlCompat.fromHtml(description, 0)
        txtViewDesc.movementMethod = ScrollingMovementMethod()
        val imageView = findViewById<ImageView>(R.id.imageLogoEnt)
        Picasso.get().load(image).into(imageView);


    }

}