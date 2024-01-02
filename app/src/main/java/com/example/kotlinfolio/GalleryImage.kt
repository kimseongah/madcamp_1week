package com.example.kotlinfolio

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.prolificinteractive.materialcalendarview.CalendarDay

data class GalleryImage(
    var img: Bitmap,
    var title: String,
    var description: String,
    var date: CalendarDay
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Bitmap::class.java.classLoader) ?: Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(CalendarDay::class.java.classLoader) ?: CalendarDay.today() // 기본값 설정
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(img, flags)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(date, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryImage> {
        override fun createFromParcel(parcel: Parcel): GalleryImage {
            return GalleryImage(parcel)
        }

        override fun newArray(size: Int): Array<GalleryImage?> {
            return arrayOfNulls(size)
        }
    }
}