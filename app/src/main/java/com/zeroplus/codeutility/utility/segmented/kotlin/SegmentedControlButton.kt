package com.zeroplus.codeutility.utility.segmented.kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton

@SuppressLint("AppCompatCustomView")
class SegmentedControlButton : RadioButton {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

}