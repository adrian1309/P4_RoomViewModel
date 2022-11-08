package com.example.p4_roomviewmodel.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.p4_roomviewmodel.R
import com.example.p4_roomviewmodel.databinding.ItemPersonBinding
import com.example.p4_roomviewmodel.domain.model.Person

class PersonAdapter(
    val actions: PersonActions,
):
    ListAdapter<Person, PersonAdapter.ItemViewholder>(DiffCallback()) {


    interface PersonActions {
        fun onDeletePersonAdapter(position: Int)
        fun onShowPersonDetailsAdapter(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_person, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPersonBinding.bind(itemView)


        fun bind(person: Person) = with(binding) {
            tvNamePersonItem.text = person.name
            tvPhonePersonItem.text = person.phone.toString()
            ivPersonDelete.setOnClickListener {
                //la posicion la sabe el propio viewholder
                actions.onDeletePersonAdapter(absoluteAdapterPosition)
            }
            ivPersonSee.setOnClickListener {
                actions.onShowPersonDetailsAdapter(absoluteAdapterPosition)
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.name == newItem.name && oldItem.phone == newItem.phone
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}

    /*
class PersonAdapter(
    private var listPerson: List<Person>,
    private val onPersonDelete: (Int) -> Unit,
    private val onShowPersonDetails: (Int) -> Unit,
    ) : RecyclerView.Adapter<PersonsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //ProductoViewHolder(ItemProductoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false))
        return PersonsViewHolder(layoutInflater.inflate(R.layout.item_person, parent, false))
    }

    //este es el que sabe la posicion de cada item para cuando lo queramos borrar por ejemplo

    //pregunta: si este es el que sabe la posicion del item, porque tenemos que pasar un ID del MainActivity
    //al recycler.
    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {
        holder.render(listPerson[position], onPersonDelete, onShowPersonDetails)
    }

    override fun getItemCount(): Int = listPerson.size

    fun refreshList(list: List<Person>){
        listPerson = list
        notifyDataSetChanged()
    }
}


class PersonsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemPersonBinding.bind(view)

    fun render(person: Person, onPersonDelete: (Int) -> Unit, onShowPersonDetails: (Int) -> Unit) {

        with(binding){
            tvNamePersonItem.text = person.name
            tvPhonePersonItem.text = person.phone.toString()
            ivPersonDelete.setOnClickListener {
                //la posicion la sabe el propio viewholder
                onPersonDelete(absoluteAdapterPosition)
            }
            ivPersonSee.setOnClickListener {
                onShowPersonDetails(absoluteAdapterPosition)
            }
        }

    }
}

     */