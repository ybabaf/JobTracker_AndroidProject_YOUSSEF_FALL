package fr.esilv.a4.ibo6.yousseffall.jobtracker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esilv.a4.ibo6.yousseffall.jobtracker.R
import fr.esilv.a4.ibo6.yousseffall.jobtracker.model.ProgrammingLanguage
import kotlinx.android.synthetic.main.layout_language.view.*
import kotlinx.android.synthetic.main.layout_offer.view.*

class ProgrammingLanguageAdapter(private val programmingLanguages : List<ProgrammingLanguage>) : RecyclerView.Adapter<ProgrammingLanguageAdapter.ProgrammingLanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgrammingLanguageViewHolder {
        return ProgrammingLanguageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_language, parent, false)
        )
    }
    override fun getItemCount() = programmingLanguages.size

    override fun onBindViewHolder(holder: ProgrammingLanguageViewHolder, position: Int) {
        val programmingLanguage = programmingLanguages[position]
        holder.view.textViewTitleLang.text = programmingLanguage.name
        holder.view.languageImage.setImageResource(programmingLanguage.image)
    }


    class ProgrammingLanguageViewHolder(val view: View) : RecyclerView.ViewHolder(view)



}