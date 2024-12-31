package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        binding.progressBar.isVisible = true  // Show loading progress initially

        lifecycleScope.launch {
            val response = try {
                // Make network request
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                // Handle network error
                Log.e(TAG, "IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launch
            } catch (e: HttpException) {
                // Handle HTTP error
                Log.e(TAG, "HttpException, unexpected response")
                binding.progressBar.isVisible = false
                return@launch
            }

            // Check if the response is successful and body is not null
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    todoAdapter.todos = it  // Update the adapter with the data
                }
            } else {
                Log.e(TAG, "Response not successful or body is null")
            }

            binding.progressBar.isVisible = false  // Hide progress bar after processing the response
        }
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}
