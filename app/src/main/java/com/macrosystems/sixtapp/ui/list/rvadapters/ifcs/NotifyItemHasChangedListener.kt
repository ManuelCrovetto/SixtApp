package com.macrosystems.sixtapp.ui.list.rvadapters.ifcs

import com.macrosystems.sixtapp.data.model.CarDetails

interface NotifyItemHasChangedListener {
    fun itemHasChanged(position: Int)
}