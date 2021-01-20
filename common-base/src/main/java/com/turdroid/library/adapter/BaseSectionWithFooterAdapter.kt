package com.tordroid.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.turdroid.library.adapter.SectionFooterEntity

abstract class BaseSectionWithFooterAdapter<T : SectionFooterEntity, VH : BaseViewHolder>
@JvmOverloads constructor(@LayoutRes private val sectionHeadResId: Int,
                          @LayoutRes private val sectionFootResId: Int,
                          @LayoutRes private val layoutResId: Int,
                          data: MutableList<T>? = null)
    : BaseMultiItemQuickAdapter<T, VH>(data) {

    init {
        addItemType(SectionFooterEntity.HEADER_TYPE, sectionHeadResId)
        addItemType(SectionFooterEntity.FOOTER_TYPE, sectionFootResId)
        addItemType(SectionFooterEntity.NORMAL_TYPE, layoutResId)
    }

    /**
     * 重写此处，设置 Header
     * @param helper ViewHolder
     * @param item data
     */
    protected abstract fun convertHeader(helper: VH, item: T)

    protected abstract fun convertFooter(helper: VH, item: T)

    /**
     * 重写此处，设置 Diff Header
     * @param helper VH
     * @param item T?
     * @param payloads MutableList<Any>
     */
    protected open fun convertHeader(helper: VH, item: T, payloads: MutableList<Any>) {}

    protected open fun convertFooter(helper: VH, item: T, payloads: MutableList<Any>) {}


    override fun isFixedViewType(type: Int): Boolean {
        return super.isFixedViewType(type) || type == SectionFooterEntity.HEADER_TYPE || type == SectionFooterEntity.FOOTER_TYPE
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder.itemViewType == SectionFooterEntity.HEADER_TYPE) {
//            setFullSpan(holder)
            convertHeader(holder, getItem(position - headerLayoutCount))
        } else if (holder.itemViewType == SectionFooterEntity.FOOTER_TYPE){
            convertFooter(holder, getItem(position - footerLayoutCount))
        }else{
            super.onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        if (holder.itemViewType == SectionFooterEntity.HEADER_TYPE) {
            convertHeader(holder, getItem(position - headerLayoutCount), payloads)
        } else if (holder.itemViewType == SectionFooterEntity.FOOTER_TYPE){
            convertFooter(holder, getItem(position - footerLayoutCount), payloads)
        }else{
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}