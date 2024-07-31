package com.example.codechallangebankapp.core.utils

import android.content.Context
import com.example.codechallangebankapp.R

internal class TaggeoManager {

    fun trackEventScreen(
        contex: Context,
        tagEvent: AppointmentCategoryTag
    ) {
        val dataTrackMenu = HashMap<String, String>()
        dataTrackMenu["eventName"] = contex.getString(tagEvent.eventName)
        dataTrackMenu["pageName"] = contex.getString(tagEvent.pageName)
        dataTrackMenu["section"] = contex.getString(tagEvent.screenName)
        //MobileCore.trackAction("TrackEventScreen", dataTrackMenu)
        //MobileCore.trackState("TrackEventScreenState", dataTrackMenu)
    }

    fun trackEventAdobeScreen(
        context: Context,
        tagEvent: AppointmentCategoryTag,
        motive: String? = "",
        folio: String? = ""
    ) {
        val dataTrackMenu = HashMap<String, String>()
        dataTrackMenu["eventName"] = context.getString(tagEvent.eventName)
        dataTrackMenu["pageName"] = context.getString(tagEvent.pageName)
        dataTrackMenu["section"] = context.getString(tagEvent.screenName)
        dataTrackMenu["typeInteraction"] = context.getString(tagEvent.typeInteraction)
        dataTrackMenu["typeElement"] = context.getString(tagEvent.typeElement)
        if (motive != "") {
            dataTrackMenu["category"] = context.getString(tagEvent.category).replace("$","$motive")
        } else {
            dataTrackMenu["category"] = context.getString(tagEvent.category)
        }
        if (folio != "") dataTrackMenu["folio"] = folio.toString()
        //MobileCore.trackAction("TrackEventClick", dataTrackMenu)
        //MobileCore.trackState("TrackEventClickState", dataTrackMenu)
    }


    enum class AppointmentCategoryTag(
        val eventName: Int, val pageName: Int, val screenName: Int,
        val typeInteraction: Int,val typeElement: Int, val category: Int) {
        HOME_SCREEN(R.string.isu_event_name, R.string.isu_home_app , R.string.isu_home,R.string.isu_clic, R.string.isu_button, R.string.isu_schedule_appointment),
        SCHEDULE_APPOINTMENT_CLICK(R.string.isu_clic_element,R.string.isu_home_app, R.string.isu_home,R.string.isu_clic,R.string.isu_button,R.string.isu_schedule_appointment),
        CONTINUE_APPOINTMENT_SCREEN(R.string.isu_event_name, R.string.isu_home_app , R.string.isu_home,R.string.isu_clic, R.string.isu_button, R.string.isu_schedule_appointment),
        CONTINUE_APPOINTMENT_CLICK(R.string.isu_clic_element,R.string.isu_home_app, R.string.isu_home,R.string.isu_clic,R.string.isu_button,R.string.isu_continue_appointment),
    }

}