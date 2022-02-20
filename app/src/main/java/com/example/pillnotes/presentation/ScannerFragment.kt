package com.example.pillnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentScannerBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.domain.util.FlashLightUtils
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
import javax.inject.Inject

class ScannerFragment : Fragment() {

    companion object {
        private const val PARSED_RESULT_UNKNOWN = "UNKNOWN"
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var flashLight: FlashLightUtils

    private lateinit var binding: FragmentScannerBinding
    private val scope = CoroutineScope(Dispatchers.IO)
    private var flashLightStatus = false

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val bundle = bundleOf(
                Constants.NOTE_TASK_CODE to arguments?.getParcelable<NoteTask>(Constants.NOTE_TASK_CODE)
            )
            findNavController().navigate(R.id.scanner_to_newNote, bundle)
        }

    }

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

        binding.apply {
            codeScannerView.decodeContinuous(object : BarcodeCallback {
                override fun barcodeResult(result: BarcodeResult) {
                    onScanResult(result)
                }

                override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
                }
            })
            flashButton.setOnClickListener {
                flashLightStatus = !flashLightStatus
                flashLight.toggleFlashLight(flashLightStatus, binding)
            }
            requireActivity().onBackPressedDispatcher.addCallback(callback)
        }
    }

    private fun onScanResult(barcodeResult: BarcodeResult) {
        val parsedResult: ParsedResult? = try {
            ResultParser.parseResult(barcodeResult.result)
        } catch (e: NotFoundException) {
            null
        }
        var text = parsedResult?.displayResult ?: barcodeResult.result
        if (text == null) {
            text = PARSED_RESULT_UNKNOWN
        }
        val type = parsedResult?.type?.name ?: PARSED_RESULT_UNKNOWN
        val bundle = bundleOf(
            Constants.TEXT_CODE to text.toString(),
            Constants.TYPE_CODE to type,
            Constants.NOTE_TASK_CODE to arguments?.getParcelable<NoteTask>(Constants.NOTE_TASK_CODE)
        )
        scope.launch {
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.scanner_to_newNote, bundle)
            }
        }
    }
}