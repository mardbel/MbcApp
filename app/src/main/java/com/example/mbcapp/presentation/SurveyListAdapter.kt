package com.example.mbcapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbcapp.R
import com.example.mbcapp.databinding.SurveyViewHolderBinding
import com.example.mbcapp.model.Survey
import com.example.mbcapp.model.SurveyData

class SurveyListAdapter(val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<SurveyListAdapter.SurveyViewHolder>() {

    private var mItems: List<SurveyData> = listOf()

    fun setItems(items: List<SurveyData>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SurveyViewHolder(
            layoutInflater.inflate(R.layout.survey_view_holder, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class SurveyViewHolder(
        view: View,
        val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = SurveyViewHolderBinding.bind(view)

        fun bind(item: SurveyData) {
            binding.surveyNameTv.text = item.attributes.title
            /*Glide.with(binding.imgHomeHolder.context).load(item.images.firstOrNull()?.url ?: R.string.image_place_holder)
                .into(binding.imgHomeHolder)*/
            binding.root.setOnClickListener {
                onItemClick(item.attributes.title)
            }
        }
    }

}