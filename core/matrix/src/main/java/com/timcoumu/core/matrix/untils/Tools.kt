package com.timcoumu.core.matrix.untils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.timcoumu.core.matrix.Matrix
import kotlinx.coroutines.CancellationException
import org.matrix.rustcomponents.sdk.Client
import org.matrix.rustcomponents.sdk.MediaSource

object Tools {
    private val TAG = "Tools"
    suspend fun getImage(url: String): Bitmap?{
        try {
            val bytes = Matrix.client.getMediaThumbnail(MediaSource.fromUrl(url), 100U, 100U)
            val bitmap = BitmapFactory.decodeByteArray(
                bytes,
                0,
                bytes.size
            )
            return bitmap
        }catch (e: CancellationException){
            throw e
        }catch (e: Exception){
            Log.e(TAG, "getImage: ${e}", )
            return null
        }

    }
}