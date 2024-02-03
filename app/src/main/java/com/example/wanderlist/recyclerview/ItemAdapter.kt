package com.example.wanderlist.recyclerview
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wanderlist.databinding.ItemLayoutBinding
import com.example.wanderlist.retrofit.user.Result

class ItemAdapter(private var itemList: ArrayList<Result>, private var width : Int) : androidx.recyclerview.widget.ListAdapter<Result, ItemAdapter.ItemViewHolder>(DiffUtil())
 {

        var onItemClick : ((Result)->Unit)? = null

    inner class ItemViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.userDpHome
        val name = binding.userNameHome
        val email = binding.userMailHome
        val displayUser = binding.cardView
    }

    fun setFilteredList(list: ArrayList<Result>){
        itemList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val imageUrl = itemList[position].picture.large
        val name = itemList[position].name.first + " " + itemList[position].name.last
        val email = itemList[position].email

        holder.binding.apply {
            val imageView = userDpHome

            if (position % 2 == 0) {
                imageView.layoutParams.width = width * 3 / 4 - 20
                imageView.requestLayout()
                imageView.load(imageUrl)
            }
            else {
                imageView.layoutParams.width = width * 1 / 4 - 20
                imageView.requestLayout()
                imageView.load(imageUrl)
            }
        }
        holder.name.text = name
        holder.email.text = email
        holder.displayUser.setOnClickListener{
            onItemClick?.invoke(itemList[position])
        }
    }
}

class DiffUtil : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}