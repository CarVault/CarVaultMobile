package com.app.carvault

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

class Util {
    companion object {
        fun bitmapImageFromString64 (image64: String?, resize: Boolean = false, newWidth: Int = 144, newHeight: Int = 144): Bitmap?{
            if (image64.isNullOrEmpty() || image64.isBlank()){
                return null
            }
            val newStr = image64.substring(image64.indexOfFirst { c -> c==',' } + 1)
            val bytes = Base64.decode(newStr, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            if (decodedImage != null) {
                return if (resize){
                    Bitmap.createScaledBitmap(decodedImage, newWidth, newHeight, true)
                }else{
                    decodedImage
                }
            }
            return null
        }

        fun convertToBase64(attachment: File): String {
            return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
        }

        fun string64FromBitmapImage(bm: Bitmap): String? {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 75, baos)
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
    }
}