package com.zeroplus.codeutility.utility.seekbar.kotlin

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.zeroplus.codeutility.R


class SegmentedStepperSeekbar : LinearLayout, OnSeekBarChangeListener{

    private var mContext: Context? = null
    private var mSeekBar: SeekBar? = null
    private var mll_containertick: LinearLayout? = null
    private  var mll_containerticklables:LinearLayout? = null
    private var mImageView: ImageView? = null
    private lateinit var mTagArray: Array<String>
    private var mProgress = 0

    interface OnSelectedListner {
        fun onSelected(selectedText: String?)
    }

    var mOnSelectedListner: OnSelectedListner? = null

    constructor(context: Context?) : super(context){
        //super(context)
        mContext = context
        initlayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        //super(context, attrs)
        mContext = context
        initlayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
    ){
        //super(context, attrs, defStyle)
        mContext = context
        initlayout()
    }

    /**
     * initialize Layout
     */
    private fun initlayout() {
        val rootView = inflate(mContext, R.layout.segmented_stepper_seekbar, this)
        mSeekBar = rootView.findViewById<View>(R.id.segmented_stepper_seekbar) as SeekBar
        mll_containertick = rootView.findViewById<View>(R.id.ll_containertick) as LinearLayout
        mll_containerticklables =
            rootView.findViewById<View>(R.id.ll_containerticklables) as LinearLayout
        mSeekBar!!.progressDrawable.colorFilter =
            PorterDuffColorFilter(Color.parseColor("#00000000"), PorterDuff.Mode.MULTIPLY)
        mSeekBar!!.setOnSeekBarChangeListener(this)
    }

    /**
     * sets Up Radio Option Menu
     *
     * @param lableArray
     */
    fun setUpRadioOptionMenu(lableArray: Array<String>, tagArray: Array<String>) {
        mTagArray = tagArray
        for (radioButtonIndex in lableArray.indices) {
            mImageView = ImageView(mContext)
            mImageView!!.tag = tagArray[radioButtonIndex]
            mImageView!!.alpha = 0.7f
            mImageView!!.setImageResource(R.drawable.tick_unselected)
            val layoutParamsImageView = LayoutParams(
                0, LayoutParams.WRAP_CONTENT,
                (100 / lableArray.size).toFloat()
            )
            if (radioButtonIndex == 0) {
                mImageView!!.scaleType = ImageView.ScaleType.FIT_START
            }
            else if (radioButtonIndex == lableArray.size - 1) {
                mImageView!!.scaleType = ImageView.ScaleType.FIT_END
            }
            else {
                mImageView!!.scaleType = ImageView.ScaleType.FIT_CENTER
            }

            mImageView!!.layoutParams = layoutParamsImageView
            setLables(lableArray, tagArray, radioButtonIndex)
            mll_containertick!!.addView(mImageView)
        }
    }

    /**
     * sets Lables
     *
     * @param lableArray
     * @param tagArray
     * @param radioButtonIndex
     */
    private fun setLables(
        lableArray: Array<String>,
        tagArray: Array<String>,
        radioButtonIndex: Int
    ) {
        val textView = TextView(mContext)
        val layoutParamsTextView = LayoutParams(
            0, LayoutParams.WRAP_CONTENT,
            (100 / lableArray.size).toFloat()
        )
        textView.layoutParams = layoutParamsTextView
        textView.text = lableArray[radioButtonIndex]
        textView.tag = radioButtonIndex.toString() + ""
        if (radioButtonIndex == 0) {
            textView.gravity = Gravity.LEFT
        } else if (radioButtonIndex == lableArray.size - 1) {
            textView.gravity = Gravity.RIGHT
        } else {
            textView.gravity = Gravity.CENTER
        }
        mll_containerticklables?.addView(textView)
    }

    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        setProgress(seekBar.progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        mProgress = seekBar.progress
        Log.d("e", mProgress.toString())
        var intervalSize = mTagArray.size + mTagArray.size-2;

        var intervalRange = 100 / intervalSize

        for (i in 0 until mTagArray.size)
        {
            if(i == 0)
            {

                if (mProgress >= 0 && mProgress <= intervalRange) {
                    Log.d("e", "i == 0")
                    seekBar.progress = 0
                    break
                }
            }
            else if(i == mTagArray.size-1)
            {

                if (mProgress >= (100-intervalRange) && mProgress <= 100) {
                    Log.d("e", "i == mTagArray.size-1")
                    seekBar.progress = 100
                    break
                }
            }
            else
            {

                if (mProgress >= (i*2-1)*intervalRange && mProgress <= (i*2+1)*intervalRange) {
                    Log.d("e", "else")
                    seekBar.progress = i*2*intervalRange
                    break
                }
            }
        }

/*
        if (mProgress >=0 && mProgress < 26) {
            seekBar.progress = 0
        } else if (mTagArray.size > 2 && mProgress > 25 && mProgress < 76) {
            seekBar.progress = 50
        } else {
            seekBar.progress = 100
        }
        */
    }

    /**
     * sets Progress
     */
    fun setProgress(progress: Int) {
        mProgress = progress
    }

    /**
     * gets Progress
     */
    fun getProgress(): Int {
        return mProgress
    }

    /**
     * sets Background
     */
    fun setBackground(backgroundColor: String?) {
        mSeekBar!!.background.setColorFilter(
            Color.parseColor(backgroundColor),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    /**
     * sets Background Image
     */
    fun setBackgroundImage(backgroundImage: Int) {
        mSeekBar!!.setBackgroundResource(backgroundImage)
    }

    /**
     * sets On Selected Listner
     *
     * @param onSelectedListner
     */
    fun setOnSelectedListner(onSelectedListner: OnSelectedListner?) {
        mOnSelectedListner = onSelectedListner
    }

}