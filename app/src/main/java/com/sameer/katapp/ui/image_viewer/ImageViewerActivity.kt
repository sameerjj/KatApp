package com.sameer.katapp.ui.image_viewer

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sameer.katapp.R
import com.sameer.katapp.data.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerActivity : AppCompatActivity() {
    val imageViewerViewModel: ImageViewerViewModel by viewModels()

    lateinit var catImageView: ImageView
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_viewer_activitiy)
        catImageView = findViewById(R.id.catImageView)
        nextButton = findViewById(R.id.nextButton)

        imageViewerViewModel.catResult.observe(this, Observer {
            val imageResult = it ?: return@Observer
            when (imageResult){
                is Resource.Success -> showImage(imageResult.data.imageUrl)
                is Resource.Error -> TODO()
            }
        })

        nextButton.setOnClickListener {
            imageViewerViewModel.getNewImage()
        }

        imageViewerViewModel.getNewImage()
    }

    private fun showImage(imageUrl: String){
        Picasso.get().load(imageUrl).into(catImageView)
    }
}