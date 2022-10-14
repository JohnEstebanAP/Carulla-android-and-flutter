package com.sofka.carullaandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class SingleFlutterActivity : FlutterActivity(), EngineBindingsDelegate {

    private val engineBindings: EngineBindings by lazy {
        EngineBindings(this, this, entrypoint = "main")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineBindings.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
        engineBindings.detach()
    }

    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        return engineBindings.engine
    }

    override fun onNext() {
        val flutterIntent = Intent(this, MainActivity::class.java)
        startActivity(flutterIntent)
    }
}