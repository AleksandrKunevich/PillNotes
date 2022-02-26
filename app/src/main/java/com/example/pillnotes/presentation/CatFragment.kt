package com.example.pillnotes.presentation

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentCatBinding
import com.example.pillnotes.domain.stars.ModelStar
import com.example.pillnotes.domain.stars.PointStar
import com.example.pillnotes.domain.viewmodel.CatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatFragment : Fragment() {

    companion object {
        const val TAG = "!!!!!!!!!!"
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var catViewModel: CatViewModel

    private lateinit var binding: FragmentCatBinding
    private var scopeStar: Job? = null

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
        Log.e(TAG, "onStart: ")
        binding.imgCat.setOnClickListener {
            loadCat()
        }

        catViewModel.draw?.observe(this) { bitmap ->
            binding.imgCat.setImageBitmap(bitmap)
        }

        binding.sfCat.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                scopeStar = CoroutineScope(Dispatchers.IO).launch {
                    var time = System.currentTimeMillis()
                    val model = ModelStar()
                    while (true) {
                        try {
                            val canvas: Canvas = holder.lockCanvas()
                            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                            paint.style = Paint.Style.FILL
                            paint.color = resources.getColor(R.color.black, null)
                            canvas.drawRect(
                                0f,
                                0f,
                                canvas.width.toFloat(),
                                canvas.height.toFloat(),
                                paint
                            )
                            paint.color = resources.getColor(R.color.white, null)
                            paint.style = Paint.Style.STROKE
                            paint.strokeWidth = 3.0f
                            val timeElapsed = System.currentTimeMillis() - time
                            time = System.currentTimeMillis()
                            model.update(timeElapsed)
                            for (point: PointStar in model.getPoints()) {
                                val sx: Float =
                                    canvas.width / 2f + canvas.width / 2f * point.x / point.z
                                val sy: Float =
                                    canvas.height / 2f + canvas.height / 2f * point.y / point.z
                                if (sx < canvas.width && sx >= 0 && sy < canvas.height && sy >= 0) {
                                    val bitmapSource: Bitmap =
                                        BitmapFactory.decodeResource(
                                            context?.resources,
                                            R.mipmap.ic_app_star
                                        )
                                    val matrix = Matrix().apply {
                                        postRotate(point.r)
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
                                    canvas.drawBitmap(bitmap, sx, sy, paint)
                                }
                            }
                            holder.unlockCanvasAndPost(canvas)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                scopeStar?.start()
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
        scopeStar?.start()
    }

    override fun onPause() {
        super.onPause()
        scopeStar?.cancel()
    }

    private fun loadCat() {
        catViewModel.getCatRandomBitmap()
    }
}