package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.databinding.FragmentScannerBinding
import com.google.zxing.NotFoundException
import com.google.zxing.ResultPoint
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ResultParser
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val DIFF_BETWEEN_SCANS_MS = 1000

class ScannerFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    var lastScanTime: Long = 0
    private lateinit var binding: FragmentScannerBinding
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codeScannerView.resume()
        binding.codeScannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                onScanResult(result)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            }
        })
    }

    private fun onScanResult(barcodeResult: BarcodeResult) {
        if (barcodeResult.timestamp - DIFF_BETWEEN_SCANS_MS < lastScanTime) {
            return
        }
        lastScanTime = barcodeResult.timestamp

        scope.launch {
            val parsedResult: ParsedResult? = try {
                ResultParser.parseResult(barcodeResult.result)
            } catch (e: NotFoundException) {
                null
            }
            val text = parsedResult?.displayResult ?: barcodeResult.result
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.codeScannerView.pause()
    }
}