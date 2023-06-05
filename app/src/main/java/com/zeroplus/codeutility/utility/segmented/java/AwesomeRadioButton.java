package com.zeroplus.codeutility.utility.segmented.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.zeroplus.codeutility.R;
import com.zeroplus.codeutility.utility.segmented.java.font.BootstrapTextView;
import com.zeroplus.codeutility.utility.segmented.java.utils.BootstrapText;
import com.zeroplus.codeutility.utility.segmented.java.utils.IconResolver;

@SuppressLint("AppCompatCustomView")
public class AwesomeRadioButton extends RadioButton implements BootstrapTextView {

    private BootstrapText bootstrapText;

    public AwesomeRadioButton(Context context) {
        super(context);
    }

    public AwesomeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AwesomeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AwesomeRadioButton);
        String markdownText;

        try {
            markdownText = typedArray.getString(R.styleable.AwesomeRadioButton_awesome_text);
        } finally {
            typedArray.recycle();
        }
        if (markdownText != null) {
            setMarkdownText(markdownText);
        }
        updateBootstrapState();
    }

    @Override
    public void setBootstrapText(@Nullable BootstrapText bootstrapText) {
        this.bootstrapText = bootstrapText;
        updateBootstrapState();
    }

    @Nullable
    @Override
    public BootstrapText getBootstrapText() {
        return bootstrapText;
    }

    @Override
    public void setMarkdownText(@Nullable String text) {
        String textSpace = text + " ";
        setBootstrapText(IconResolver.resolveMarkdown(getContext(), textSpace, isInEditMode()));
    }

    protected void updateBootstrapState() {
        if (bootstrapText != null) {
            setText(bootstrapText);
        }
    }
}
