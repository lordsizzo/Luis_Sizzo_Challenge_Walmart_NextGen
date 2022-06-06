package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common.*
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.databinding.ActivityMainBinding
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.UI_State
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.remote.Coutries
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view.adapters.CountriesAdapter
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view_model.MainActivityViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var howShowIt = true
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initOberserver()
        initViews()
    }

    private fun initViews() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getCountries()
        }

        binding.listView.click {
            howShowIt = false
            viewModel.getStateView(false)
        }
        binding.tableView.click {
            howShowIt = true
            viewModel.getStateView(true)
        }
    }

    private fun initOberserver() {
        viewModel.stateView.observe(this) {
            Log.d("MainActivity", "Result of observe: $it")
            howShowIt = it
            if (it) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.listView.visibility = View.VISIBLE
                binding.tableView.visibility = View.GONE

            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.listView.visibility = View.GONE
                binding.tableView.visibility = View.VISIBLE
            }
        }
        viewModel.getStateView(howShowIt)
        viewModel.getCoutriesResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            binding.shimmerViewContainer.startShimmerAnimation()
                            Toast.makeText(
                                applicationContext,
                                "Loading Details...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UI_State.SUCCESS<*> -> {

                            Log.d("MainActivity", "${result.response}")
                            val country = result.response as? List<Coutries>

                            country?.let {
                                CountriesAdapter(it).apply {
                                    if (howShowIt)
                                        binding.recyclerView.settingsLinearVertical(this)
                                    else
                                        binding.recyclerView.settingsGrid(this)
                                    binding.swipeRefresh.isRefreshing = false
                                }
                                binding.shimmerViewContainer.stopShimmerAnimation();
                                binding.shimmerViewContainer.setVisibility(View.GONE);
                                Toast.makeText(
                                    applicationContext,
                                    "Countries have been received...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } ?: showError("Error at casting")
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> showError(error) }
                        }
                    }
                } catch (e: Exception) {
                    showError(e.toString())
                }
            }
        }
        viewModel.getCountries()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}