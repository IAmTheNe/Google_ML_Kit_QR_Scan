package com.example.scanqr

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.scanqr.databinding.ActivityMainBinding
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(private val context: Context, private val binding: ActivityMainBinding) :
    ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val img = image.image
        if (img != null) {
            val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

            // Process image searching for barcodes
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .enableAllPotentialBarcodes()
                .build()

            val scanner = BarcodeScanning.getClient(options)

            val result = scanner.process(inputImage)
                .addOnSuccessListener {
                    readBarcodeData(it)
                }
                .addOnFailureListener {}
                .addOnCompleteListener {
                    image.close()
                }
        }


    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        var result: String? = ""
        for (barcode in barcodes) {
            val text = barcode.displayValue
            result = text

            Toast.makeText(context, barcode.boundingBox?.bottom.toString(), Toast.LENGTH_SHORT).show()

        }



        binding.result.text = result
    }
}