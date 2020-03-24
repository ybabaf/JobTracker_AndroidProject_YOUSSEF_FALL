package fr.esilv.a4.ibo6.yousseffall.jobtracker.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R
import fr.esilv.a4.ibo6.yousseffall.jobtracker.activity.SeeOfferActivity
import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.JobOffer
import kotlinx.android.synthetic.main.layout_offer.view.*


class JobOffersAdapter(private val jobOffers : List<JobOffer>) : RecyclerView.Adapter<JobOffersAdapter.JobOfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobOfferViewHolder {
        return JobOfferViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_offer, parent, false)
        )
    }

    override fun getItemCount() = jobOffers.size

    override fun onBindViewHolder(holder: JobOfferViewHolder, position: Int) {

        val jobOffer = jobOffers[position]

        holder.view.textViewTitle.text = jobOffer.title
        holder.view.textViewCompany.text = jobOffer.company
        holder.view.textViewLocation.text = jobOffer.location //HtmlCompat.fromHtml(jobOffer.description, 0)

        holder.view.cardViewOffer.setOnClickListener(({
            val intent = Intent(holder.view.context, SeeOfferActivity::class.java)
            intent.putExtra("JOB_OFFER_TITLE", jobOffer.title)
            intent.putExtra("JOB_OFFER_COMP", jobOffer.company)
            intent.putExtra("JOB_OFFER_IMAGE", jobOffer.company_logo)
            intent.putExtra("JOB_OFFER_LOC", jobOffer.location)
            intent.putExtra("JOB_OFFER_TYPE", jobOffer.type)
            intent.putExtra("JOB_OFFER_DESC", jobOffer.description)
            holder.view.context.startActivity(intent)
        }))

        /*holder.view.buttonSeeOffer.setOnClickListener(({
            val intent = Intent(Context context, SeeOfferActivity::class.java)
            //intent.putExtra("jobOffer", jobOffer)
            startActivity(intent)
        }))*/

        //Will show only if the offer is 'new', need to write a function that changes the new variable from true to false based on the 'created_at' date
        //holder.view.textViewIsNew.visibility = if (jobOffer.isNew) View.VISIBLE else View.INVISIBLE
    }

    class JobOfferViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}