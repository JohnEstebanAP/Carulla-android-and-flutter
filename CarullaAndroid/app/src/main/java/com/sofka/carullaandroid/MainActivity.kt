package com.sofka.carullaandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sofka.carullaandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DataModelObserver {
    private lateinit var binding: ActivityMainBinding

    private lateinit var countView: TextView

    private companion object {
        /** A count that makes every other MainActivity have 1 or 2 Flutter instances. */
        var mainActivityCount = 0
    }

    private val mainActivityIdentifier: Int = mainActivityCount

    init {
        mainActivityCount += 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //intancio mi observer
        DataModel.instance.addObserver(this)

        with(binding) {

            countView = textCounter
            countView.text = DataModel.instance.counter.toString()

            buttonAdd.setOnClickListener {
                DataModel.instance.counter = DataModel.instance.counter + 1
            }

            buttonFlutter.setOnClickListener {
                val nextClass =
                    if (mainActivityIdentifier % 2 == 0) SingleFlutterActivity::class.java else DoubleFlutterActivity::class.java
                val flutterIntent = Intent(this@MainActivity, nextClass)
                startActivity(flutterIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DataModel.instance.removeObserver(this)
    }

    override fun onCountUpdate(newCount: Int) {
        countView.text = newCount.toString()
    }
}