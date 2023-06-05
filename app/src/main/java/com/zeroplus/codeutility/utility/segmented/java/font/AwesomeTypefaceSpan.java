package com.zeroplus.codeutility.utility.segmented.java.font;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

import com.zeroplus.codeutility.utility.segmented.java.utils.TypefaceProvider;

public class AwesomeTypefaceSpan extends TypefaceSpan {

    private final Context context;
    private final IconSet iconSet;

    public AwesomeTypefaceSpan(Context context, IconSet iconSet) {
        super(iconSet.fontPath().toString());
        this.context = context.getApplicationContext();
        this.iconSet = iconSet;
    }

    @Override public void updateDrawState(@NonNull TextPaint ds) {
        ds.setTypeface(TypefaceProvider.getTypeface(context, iconSet));
    }

    @Override public void updateMeasureState(@NonNull TextPaint paint) {
        paint.setTypeface(TypefaceProvider.getTypeface(context, iconSet));
    }
}
