package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.*
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.R
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common.Utilities.Companion.getRandomColor
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common.*
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.databinding.ItemsCountryBinding
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.remote.Coutries

class CountriesAdapter(private val items: List<Coutries>) :
    RecyclerView.Adapter<CountriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: ItemsCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemsCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        try {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.good_anim)
            holder.binding.nameAndRegion.text = "${items[position].name}, ${items[position].region}"
            holder.binding.code.text = items[position].code
            holder.binding.capital.text = items[position].capital
            holder.binding.capital.setBackgroundColor(getRandomColor(holder.binding.root.context));
            holder.binding.root.click{
                it.snack("His currency is ${items[position].currency.name} and language is ${items[position].language.name} ")
            }
        } catch (e: Exception) {

            holder.binding.root.context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}