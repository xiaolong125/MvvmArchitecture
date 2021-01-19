package com.turdroid.architecture

import android.util.SparseArray
import androidx.lifecycle.ViewModel

class DataBindingConfig(
    val layout: Int,
    val vmVariableId: Int,
    val stateViewModel: ViewModel) {
    val bindingParams: SparseArray<Any> = SparseArray()

    fun addBindingParam(variableId: Int, any: Any): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }

}