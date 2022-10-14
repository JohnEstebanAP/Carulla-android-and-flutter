package com.sofka.carullaandroid

import android.app.Application
import io.flutter.embedding.engine.FlutterEngineGroup

class App : Application() {

    lateinit var engines: FlutterEngineGroup

    override fun onCreate() {
        print("Hola kotlin attach")
        super.onCreate()
        engines = FlutterEngineGroup(this);
    }
}