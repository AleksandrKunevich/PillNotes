package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
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
import kotlinx.coroutines.withContext

private const val PARSED_RESULT_UNKNOWN = "UNKNOWN"
private const val TEXT_CODE = "TEXT_CODE"
private const val TYPE_CODE = "TYPE_CODE"

class ScannerFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    var lastScanTime: Long = 0
    private lateinit var binding: FragmentScannerBinding
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onResume() {
        super.onResume()
        binding.codeScannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.codeScannerView.pause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.codeScannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                onScanResult(result)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            }
        })
    }

    private fun onScanResult(barcodeResult: BarcodeResult) {
        val parsedResult: ParsedResult? = try {
            ResultParser.parseResult(barcodeResult.result)
        } catch (e: NotFoundException) {
            null
        }
        val text = parsedResult?.displayResult ?: barcodeResult.result
        val type = parsedResult?.type?.name ?: PARSED_RESULT_UNKNOWN
        val bundle = bundleOf(TEXT_CODE to text.toString(), TYPE_CODE to type)
        scope.launch {
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.scanner_to_home, bundle)
            }
        }
    }
}