package com.example.quoteoftheday

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.quoteoftheday.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listQuotes = mutableListOf(
        R.string.quote_string,
        R.string.quote_string2,
        R.string.quote_string3,
        R.string.quote_string4,
        R.string.quote_string5,
        R.string.quote_string6,
        R.string.quote_string7
    )
    private var quoteNumber = 0
    private var mainText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteOnAppLoaded()
        clickNewQuote()
    }

    private fun clickNewQuote() {
        binding.fabNewQuote.setOnClickListener {
            binding.fabNewQuote.isEnabled = false

            if (quoteNumber == listQuotes.size) {
                quoteOnAppLoaded()
            } else {
                typeText(getString(listQuotes[quoteNumber]))
                ++quoteNumber
            }
        }
    }

    private fun quoteOnAppLoaded() {
        binding.fabNewQuote.isEnabled = false
        quoteNumber = 0
        listQuotes.shuffle()
        typeText(getString(listQuotes[quoteNumber]))
        ++quoteNumber
    }

    private fun typeText(text: String) {
        mainText=""
        val textDelay = 50L

        GlobalScope.launch(Dispatchers.IO) {
            val sb = StringBuilder()
            val updatedText = ""

            for (i in text.indices) {
                mainText = sb.append(updatedText + text[i]).toString()
                Thread.sleep(textDelay)
            }
        }
        val handler = Handler()
        Log.d("Main", "Handler started")
        val runnable = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                binding.tvText.text = "$mainText|"
                handler.postDelayed(this,10)

                if (text == mainText) {
                    handler.removeCallbacks(this)
                    binding.tvText.text = mainText
                    binding.fabNewQuote.isEnabled = true
                }
            }
        }
        handler.postDelayed(runnable,0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.nav_share -> {

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, mainText)
                startActivity(Intent.createChooser(shareIntent,"Share this quote!"))

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
