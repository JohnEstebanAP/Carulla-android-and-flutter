package com.sofka.carullaandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sofka.carullaandroid.databinding.ActivityMainBinding
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

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

        val dartEntrypoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "bottomMain"
            )

        val engine = FlutterEngineGroup(this).createAndRunEngine(this, dartEntrypoint)

        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", engine)

        with(binding) {

            countView = textCounter
            countView.text = DataModel.instance.counter.toString()

            buttonAdd.setOnClickListener {
                DataModel.instance.counter = DataModel.instance.counter + 1
            }

            buttonFlutter.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, SingleFlutterActivity::class.java)
                )
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