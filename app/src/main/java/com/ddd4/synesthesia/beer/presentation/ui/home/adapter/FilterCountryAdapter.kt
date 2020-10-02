package com.ddd4.synesthesia.beer.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.databinding.ItemFilterCountryBinding
import com.ddd4.synesthesia.beer.util.MutableLiveDataList

class FilterCountryAdapter(
    private val checkedList: MutableLiveDataList<String>
) : RecyclerView.Adapter<FilterCountryAdapter.CountryViewHolder>() {

    var items: List<String> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            ItemFilterCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding, checkedList)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CountryViewHolder(
        private val binding: ItemFilterCountryBinding,
        private val checkedList: MutableLiveDataList<String>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.apply {
                country = item

                if (checkedList.isNotEmpty() && checkedList.contains(item)) {
                    checkbox.isChecked = true
                }

                checkbox.setOnClickListener {
                    if (checkbox.isChecked) {
                        checkedList.add(item)
                    } else {
                        if (checkedList.isNotEmpty() && checkedList.contains(item)) checkedList.remove(
                            item
                        )
                    }
                }
            }
        }
    }

}
