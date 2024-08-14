package com.deviz.qrscannerlite

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.deviz.qrscannerlite.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import android.Manifest
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import com.journeyapps.barcodescanner.ViewfinderView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var barcodeView: DecoratedBarcodeView

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startScanning()
            } else {
                Timber.d("Camera permission denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        barcodeView = binding.barcodeScanner

        val resultView = findViewById<TextView>(com.google.zxing.client.android.R.id.zxing_status_view)
        resultView.visibility = View.GONE

//        val viewfinderView = barcodeView.viewFinder as ViewfinderView

//        viewfinderView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                val width = viewfinderView.width
//                val height = viewfinderView.height
//
//                val density = resources.displayMetrics.density
//                val widthInDp = width / density
//                val heightInDp = height / density
//
//                Timber.d("QR 코드 스캔 영역 크기 (dp): width = $widthInDp, height = $heightInDp")
//
//                // 레이아웃 리스너 제거
//                viewfinderView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })


        checkCameraPermission()

        viewModel.isToggleBtnSelected.observe(this, Observer { isSelected ->
            updateButtonStates(isSelected)
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

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        barcodeView.decodeContinuous { result ->
            result?.let {
                Timber.d("QR Code Scanned: ${it.text}")
                barcodeView.pause()
            }
        }
        barcodeView.resume()
        Timber.d("Scanning started")
    }

    override fun onResume() {
        super.onResume()
        if (::barcodeView.isInitialized) {
            barcodeView.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::barcodeView.isInitialized) {
            barcodeView.pause()
        }
    }
}