package com.example.codechallangebankapp.core.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.codechallangebankapp.core.utils.TaggeoManager


fun View.show() {
    this.visibility = View.VISIBLE
}

internal fun Activity.tagEventAdobe( tagEvent: TaggeoManager.AppointmentCategoryTag) {
    TaggeoManager()
        .trackEventAdobeScreen(this.baseContext,tagEvent)
}