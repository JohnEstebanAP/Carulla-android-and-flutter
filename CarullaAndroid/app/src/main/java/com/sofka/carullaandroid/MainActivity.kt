package com.sofka.carullaandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sofka.carullaandroid.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {
            buttonFlutter.setOnClickListener {
                startActivity(
                    FlutterActivity.createDefaultIntent(this@MainActivity)
                )
            }

        }


    }


}