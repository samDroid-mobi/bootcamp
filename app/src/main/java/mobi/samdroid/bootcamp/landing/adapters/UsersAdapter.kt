package mobi.samdroid.bootcamp.landing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mobi.samdroid.bootcamp.base.IClickListener
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.databinding.ViewHolderUserBinding
import mobi.samdroid.bootcamp.landing.viewholders.UserViewHolder

class UsersAdapter(private val users: ArrayList<SUser>): RecyclerView.Adapter<UserViewHolder>() {
    var inter: IClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ViewHolderUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])

        holder.binding.imageButtonPhone.setOnClickListener {
            inter?.onPhoneClicked(users[position])
        }

        holder.binding.root.setOnClickListener {
            inter?.onItemClick(users[position])
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}