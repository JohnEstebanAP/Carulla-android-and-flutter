package com.sofka.carullaandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class SingleFlutterActivity : FlutterActivity(), EngineBindingsDelegate, DataModelObserver {

    lateinit var channel: MethodChannel

    /*
    override fun getDartEntrypointFunctionName(): String {
        return "topMain"
    }*/
    /*
    lateinit var engineBindings: EngineBindings
    override fun onCreate(savedInstanceState: Bundle?) {
        engineBindings = EngineBindings(this, this, entrypoint = "main")
        super.onCreate(savedInstanceState)
    }*/


    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        return FlutterEngineCache.getInstance().get("my_engine_id")
    }


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        this.channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "carulla-flutters")
        attach()
    }

    /*
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        this.channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "carulla-flutters")

        channel.invokeMethod("setCount", DataModel.instance.counter)
        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "incrementCount" -> {
                    DataModel.instance.counter = DataModel.instance.counter + 1
                    result.success(null)
                }
                "next" -> {
                    onNext()
                    result.success(null)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }*/

    /**
     * This setups the messaging connections on the platform channel and the DataModel.
     */
    fun attach() {
        DataModel.instance.addObserver(this)
        this.channel.invokeMethod("setCount", DataModel.instance.counter)
        this.channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "incrementCount" -> {
                    DataModel.instance.counter = DataModel.instance.counter + 1
                    result.success(null)
                }
                "next" -> {
                    onNext()
                    result.success(null)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    /**
     * This tears down the messaging connections on the platform channel and the DataModel.
     */
    fun detach() {
        DataModel.instance.removeObserver(this)
        this.channel.setMethodCallHandler(null)
    }


    override fun onDestroy() {
        super.onDestroy()
        detach()
    }


    override fun onNext() {
        onBackPressed()
    }

    override fun onCountUpdate(newCount: Int) {
        this.channel.invokeMethod("setCount", newCount)
    }
}