package com.sameer.katapp.ui.image_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sameer.katapp.R
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerActivity : AppCompatActivity() {
    val imageViewerViewModel: ImageViewerViewModel by viewModels()

    lateinit var catImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_viewer_activitiy)
        catImageView = findViewById(R.id.catImageView)

        imageViewerViewModel.catResult.observe(this, Observer {
            val imageResult = it ?: return@Observer
            if (imageResult.imageUrl != null){
                showImage(imageResult.imageUrl)
            }
            setResult(RESULT_OK)
        })

        catImageView.setOnClickListener {
            imageViewerViewModel.getNewImage()
        }

        imageViewerViewModel.getNewImage()
    }

    private fun showImage(imageUrl: String){
        Picasso.get().load(imageUrl).into(catImageView)
    }
}