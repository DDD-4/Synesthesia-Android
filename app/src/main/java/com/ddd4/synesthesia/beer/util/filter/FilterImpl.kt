package com.ddd4.synesthesia.beer.util.filter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FilterImpl : FilterSetting {
    /**
     *  SharedPreference 에 저장된 Filter 선택 목록을 가져와
     *  Combine 하여 FilterModel 로 반환
     */

    // 임시 데이터 Fetch Test
    override fun getStyleFilterFlow(): Flow<List<String>?> {
        return flowOf(
            arrayListOf("TEST_STYLE_0", "TEST_STYLE_3")
        )
    }

    override fun getAromaFilterFlow(): Flow<List<String>?> {
        return flowOf(
            arrayListOf("TEST_AROMA_4", "TEST_AROMA_1", "TEST_AROMA_3")
        )
    }

    override fun getAbvFilterFlow(): Flow<Pair<Int, Int>?> {
        return flowOf(
            Pair(0, 10)
        )
    }

    override fun getCountryFilterFlow(): Flow<List<String>?> {
        return flowOf(
            null
        )
    }
}