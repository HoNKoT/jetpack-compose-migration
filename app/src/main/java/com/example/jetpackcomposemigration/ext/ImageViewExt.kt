package com.example.jetpackcomposemigration.ext

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File

fun ImageView.loadImage(
    path: String?,
    width: Int,
    height: Int,
    progressView: View? = null
) {
    if (path == null || path.isEmpty()) return
    val picasso = if (path.startsWith("http")) {
        Picasso.get().load(path)
    } else {
        Picasso.get().load(File(path))
    }
    picasso
        .resize(
            width,
            height
        )
        .centerInside()
        .into(
            this,
            object : Callback {
                override fun onSuccess() {
                    progressView?.isGone = true
                }

                override fun onError(e: Exception?) {
                    // NOP
                }
            }
        )
}
