package com.example.pillnotes.presentation

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentCatBinding
import com.example.pillnotes.domain.viewmodel.CatViewModel
import javax.inject.Inject

class CatFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var catViewModel: CatViewModel

    private lateinit var binding: FragmentCatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCat()
    }

    override fun onStart() {
        super.onStart()
        binding.imgCat.setOnClickListener {
            loadCat()
        }

        catViewModel.draw?.observe(this) { bitmap ->
            binding.imgCat.setImageBitmap(bitmap)
        }

        binding.sfCat.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                val canvas: Canvas = holder.lockCanvas()
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.color = resources.getColor(R.color.white, null)
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 3.0f
                canvas.drawPoint(100.0f, 100.0f, paint)
                val bitmapSource: Bitmap =
                    BitmapFactory.decodeResource(context?.resources, R.mipmap.ic_app_star)
                val matrix = Matrix().apply {
                    postRotate(30f)
                    postScale(0.5f, 0.5f)
                }
                val bitmap = Bitmap.createBitmap(
                    bitmapSource,
                    0,
                    0,
                    bitmapSource.width,
                    bitmapSource.height,
                    matrix,
                    true
                )
                canvas.drawBitmap(bitmap, 200f, 200f, paint)
                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })
    }

    private fun loadCat() {
        catViewModel.getCatRandomBitmap()
    }
}