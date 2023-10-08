package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize//import без .android
enum class Level: Parcelable {

    TEST, EASY, NORMAL, HARD
}