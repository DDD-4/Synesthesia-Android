package com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

sealed class FilterClicklEntity : ItemClickEntity() {
    class SelectMiddleCategory(val item: StyleMiddleItemViewModel) : FilterClicklEntity()
    class SelectStyleSet(val itemStyle: StyleSmallItemViewModel) : FilterClicklEntity()
    class AddStyle(val style: StyleSmallItemViewModel) : FilterClicklEntity()
    class DeleteStyle(val style: StyleSmallItemViewModel) : FilterClicklEntity()
    class SelectDone(val selectItem: List<StyleSmallItemViewModel>) : FilterClicklEntity()
}