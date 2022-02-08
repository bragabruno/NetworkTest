package com.example.networktest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.TextView
import com.example.networktest.model.presentation.BookResponse
import com.example.networktest.model.remote.Api
import com.example.networktest.model.remote.executeBookSearch
import com.example.networktest.model.remote.isDeviceConnected
import com.example.networktest.view.BookListFragment
import com.example.networktest.view.SearchBookFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO executeBookSearch on main thread

        if (isDeviceConnected()){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_search, SearchBookFragment())
                .commit()
//            executeNetworkCall()
//            val policy = ThreadPolicy.Builder()
//                .permitAll() // Does not detect or log anything
//                .detectAll()
//                .detectNetwork()
//                .penaltyDeathOnNetwork()
//                .penaltyLog()
//                .build()
//            StrictMode.setThreadPolicy(policy)
//            executeBookSearch("Harry Potter")
        }
        else
            showError()
    }

    fun executeRetrofit(bookTitle: String, bookType: String, maxResult: Int) {
        Api.initRetrofit().getBookByTitle(bookTitle, bookType)
            .enqueue(object : Callback<BookResponse>{
                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    if (response.isSuccessful)
                        inflateDisplayFragment(response.body())
                    else
                        showError()
                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun inflateDisplayFragment(dataSet: BookResponse?) {
        dataSet?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_display, BookListFragment.newInstance(it))
                .commit()

        }
    }

    private fun showError() {
        Snackbar.make(findViewById(R.id.container_display),
        "No network, retry",
        Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
            Log.d(TAG, "showError: Retried!")
        }.show()
    }


    class BookNetwork(private val display: TextView): AsyncTask<String, Void, BookResponse>(){
        /**
         * Happen in the work thread
         * 'new Thread(new Runnable(){public void run(){}})
         */
        override fun doInBackground(vararg p0: String): BookResponse {
//            display.text = p0.toString()
            return executeBookSearch("Harry Potter")
        }

        /**
         * Happens in the Main Thread
         */
        override fun onPostExecute(result: BookResponse?) {
            super.onPostExecute(result)
            display.text = result.toString() ?: " "
        }
    }
}
