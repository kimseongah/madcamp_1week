import android.os.Parcel
import android.os.Parcelable
import com.prolificinteractive.materialcalendarview.CalendarDay

data class Person(
    var no: Int,
    var name: String,
    var phoneNumber: String,
    var data: String,
    var date: CalendarDay,
    var imagePath: String = "none"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(CalendarDay::class.java.classLoader) ?: CalendarDay.today(),
        parcel.readString() ?: "none"
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(no)
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeString(data)
        parcel.writeParcelable(date, flags)
        parcel.writeString(imagePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
