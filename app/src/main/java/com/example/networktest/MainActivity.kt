package com.example.networktest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.TextView
import com.example.networktest.model.presentation.BookResponse
import com.example.networktest.model.remote.executeBookSearch
import com.example.networktest.model.remote.isDeviceConnected
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO executeBookSearch on main thread

        if (isDeviceConnected()){
            executeNetworkCall()
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

    private fun showError() {
        Snackbar.make(findViewById(R.id.tv_display),
        "No network, retry",
        Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
            Log.d(TAG, "showError: Retried!")
        }.show()
    }

    private fun executeNetworkCall() {
        BookNetwork(findViewById(R.id.tv_display)).execute()
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
