package com.macrosystems.sixtapp.ui.list.rvadapters.ifcs

import com.macrosystems.sixtapp.data.model.CarDetails

interface Notify {
    fun itemHasChanged(position: Int)
}