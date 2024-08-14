package com.deviz.qrscannerlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.deviz.qrscannerlite.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.isScanSelected.observe(this, Observer { isScanSelected ->
            Timber.d("check_isScan: $isScanSelected")
            updateButtonStates(isScanSelected)
        })

        binding.btnScan.setOnClickListener {
            viewModel.onScanClick()
        }

        binding.btnGenerate.setOnClickListener {
            viewModel.onGenerateClick()
        }
    }

    private fun updateButtonStates(isScanSelected: Boolean) {
        binding.btnScan.isSelected = isScanSelected
        binding.btnGenerate.isSelected = !isScanSelected
    }
}