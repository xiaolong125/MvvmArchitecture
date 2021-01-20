package com.turdroid.library.adapter

import com.chad.library.adapter.base.entity.SectionEntity

interface SectionFooterEntity : SectionEntity {

    companion object {
        const val NORMAL_TYPE = -100
        const val HEADER_TYPE = -99
        const val FOOTER_TYPE = -98
    }

    val isFooter: Boolean

    override val itemType: Int
        get() = if (isHeader) HEADER_TYPE else if (isFooter) FOOTER_TYPE else NORMAL_TYPE

}