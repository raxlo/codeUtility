package com.zeroplus.codeutility.utility.segmented.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.zeroplus.codeutility.R;

@SuppressLint("AppCompatCustomView")
public class SegmentedControlButton extends RadioButton {
    public SegmentedControlButton( Context context ) {
        super( context );
    }

    public SegmentedControlButton( Context context, AttributeSet attrs ) {
        // It would be great to simply pass in R.style.SegmentedControlButton here, but that won't work due to:
        // https://code.google.com/p/android/issues/detail?id=12683
        super( context, attrs, R.attr.segmentedControlButtonStyle );
    }

    public SegmentedControlButton( Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }
}