package com.zeroplus.codeutility.utility.segmented.kotlin

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.zeroplus.codeutility.R

class SegmentedControl : RadioGroup {

    constructor(context: Context?) : super(context) {init()}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {init()}

    private fun init() {
        // Set background
        setBackgroundResource(R.drawable.segmented_control_bg)

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return adjustLayoutParams(super.generateLayoutParams(attrs))
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams? {
        return adjustLayoutParams(super.generateDefaultLayoutParams() as LayoutParams)
    }

    private fun adjustLayoutParams(params: LayoutParams): LayoutParams? {
        // Make all buttons the same size if the orientation is horizontal
        if (orientation == HORIZONTAL) {
            params.width = 0
            params.weight = 1f
        }
        return params
    }
}