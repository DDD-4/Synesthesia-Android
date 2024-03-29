package com.ddd4.synesthesia.beer.presentation.base.event

import android.os.Parcelable
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import kotlinx.android.parcel.Parcelize

interface EventNotifier

interface ActionEventNotifier : EventNotifier {
    fun notifyActionEvent(entity: ActionEntity)
}

/**
 * select event 전송을 위한 인터페이스
 */
interface SelectEventNotifier : EventNotifier {
    fun notifySelectEvent(entity: ItemClickEntity)
}

interface SelectActionEventNotifier : SelectEventNotifier, ActionEventNotifier
