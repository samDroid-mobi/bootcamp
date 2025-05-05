package mobi.samdroid.bootcamp.landing.viewholders

import androidx.recyclerview.widget.RecyclerView
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.databinding.ViewHolderUserBinding

class UserViewHolder(val binding: ViewHolderUserBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: SUser) {
        binding.textViewName.text = user.username
        binding.textViewPassword.text = user.password
    }
}