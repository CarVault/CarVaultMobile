package com.app.carvault

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class Util {
    companion object {
        fun bitmapImageFromString64 (image64: String?, resize: Boolean = false, newWidth: Int = 144, newHeight: Int = 144): Bitmap?{
            if (image64.isNullOrEmpty() || image64.isBlank()){
                return null
            }
            val newStr = image64.drop(22)
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
    }
}