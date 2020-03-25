package fr.esilv.a4.ibo6.yousseffall.jobtracker.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Picasso
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R
import kotlinx.android.synthetic.main.activity_see_offer.*
import org.nibor.autolink.LinkExtractor
import org.nibor.autolink.LinkSpan
import org.nibor.autolink.LinkType
import java.util.*


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


        val imageView = findViewById<ImageView>(R.id.imageLogoEnt)
        Picasso.get().load(image).into(imageView);
        findViewById<TextView>(R.id.textViewTitle).text = title
        findViewById<TextView>(R.id.textViewCompany).text = company
        findViewById<TextView>(R.id.textViewLocation).text = location
        findViewById<TextView>(R.id.textViewType).text = type

        //TextView Description
        val txtViewDesc = findViewById<TextView>(R.id.textViewDescription)
        txtViewDesc.text = HtmlCompat.fromHtml(description, 0)
        txtViewDesc.movementMethod = ScrollingMovementMethod()

        /*
      When the "Apply" btn is clicked, we launch the webview
       */
        val howToApplyHtml = intent.getStringExtra("JOB_OFFER_URL")
        val applyUrl = applyUrl(howToApplyHtml)
        btnApply.setOnClickListener(({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(applyUrl))
            startActivity(browserIntent)
        }))
    }

    private fun applyUrl(how_to_apply: String) : String {
        val whereToApplyString = HtmlCompat.fromHtml(how_to_apply, 0) //Get rid of the html tags
        val linkExtractor: LinkExtractor = LinkExtractor.builder() //Extracting the url from the string with the autolink lib
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
            .build()
        val links: Iterable<LinkSpan> = linkExtractor.extractLinks(whereToApplyString)
        val link = links.iterator().next()
        return whereToApplyString.substring(link.getBeginIndex(), link.getEndIndex()); //Returns only the link from the string
    }

}