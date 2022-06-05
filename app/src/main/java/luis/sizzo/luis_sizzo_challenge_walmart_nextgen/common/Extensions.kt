package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view.adapters.CountriesAdapter

fun RecyclerView.settingsGrid(adapter: CountriesAdapter){
    this.layoutManager = GridLayoutManager(this.context, 2)
    this.adapter = adapter
}

fun RecyclerView.settingsLinearVertical(adapter: CountriesAdapter){
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

fun Context.toast(message: String, lenght: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, lenght).show()
}

fun View.snack(message: String, lenght: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, lenght).show()
}

fun View.click(listener: (View) -> Unit){
    this.setOnClickListener{
        listener(it)
    }
}