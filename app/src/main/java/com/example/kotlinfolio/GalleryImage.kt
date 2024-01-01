package com.example.kotlinfolio

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.prolificinteractive.materialcalendarview.CalendarDay

data class GalleryImage(
    var img: Int,
    var title: String,
    var description: String,
    var uri: Uri?,
    var date: CalendarDay
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readParcelable(CalendarDay::class.java.classLoader) ?: CalendarDay.today() // 기본값 설정
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(img)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(uri, flags)
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

