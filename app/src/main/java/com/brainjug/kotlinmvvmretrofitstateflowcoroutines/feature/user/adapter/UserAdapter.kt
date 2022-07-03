package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.RowUserItemBinding
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.response.User
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util.AutoUpdatableAdapter
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import kotlin.random.Random

class UserAdapter() :
    RecyclerView.Adapter<UserAdapter.BusinessListViewHolder>(),
    AutoUpdatableAdapter {

    var listAll = ArrayList<User>()

    class BusinessListViewHolder(private val binding: RowUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            user: User
        ) {
            binding.viewImage.setBackgroundColor(Int.randomColor())
            binding.txtImg.text = getInitials(user.name?:"")

            binding.user = user
            binding.executePendingBindings()
        }

        private fun getInitials(inputStr: String): String{
            return inputStr
                .split(' ')
                .mapNotNull { it.firstOrNull()?.toString() }
                .reduce { acc, s -> acc + s }
        }

        private fun Int.Companion.randomColor(): Int {
            return Color.argb(255,
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256))
        }
    }

    private var differ: List<User> by Delegates.observable(
        emptyList()
    ) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    fun submitList(newList: List<User>) {
        differ = newList as ArrayList<User>
        listAll = differ as ArrayList<User>
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BusinessListViewHolder {
        val binding = RowUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BusinessListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BusinessListViewHolder,
        position: Int
    ) {
        val filterItem = differ[position]
        holder.bind(filterItem)
    }

    override fun getItemCount(): Int = differ.size

    fun filter(searchChar: String) {
        differ = if (searchChar.isEmpty()) {
            differ = emptyList()
            listAll
        } else {
            val resultList = ArrayList<User>()
            for (row in listAll) {
                if (row.name?.lowercase(Locale.ROOT)?.contains(searchChar.lowercase(Locale.ROOT)) == true
                ) {
                    resultList.add(row)
                }
            }
            resultList
        }
    }
}
