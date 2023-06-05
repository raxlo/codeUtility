package com.zeroplus.codeutility.utility.segmented.kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Build
import android.util.AttributeSet
import android.util.StateSet
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.zeroplus.codeutility.R


class SegmentedGroup : RadioGroup {

    private var mMarginDp = 0
    //private var resources: Resources? = null
    private var mTintColor = 0
    private var mUnCheckedTintColor = 0
    private var mCheckedTextColor = Color.WHITE
    private var mLayoutSelector: LayoutSelector? = null
    private var mCornerRadius: Float? = null
    private var mCheckedChangeListener: OnCheckedChangeListener? = null
    private var mDrawableMap: HashMap<Int, TransitionDrawable>? = null
    private var mLastCheckId = 0

    constructor(context: Context?) : super(context){
        //super(context)
        //resources = getResources()
        mTintColor = getResources().getColor(R.color.radio_button_selected_color)
        mUnCheckedTintColor = getResources().getColor(R.color.radio_button_unselected_color)
        mMarginDp = getResources().getDimension(R.dimen.radio_button_stroke_border).toInt()
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius)
        mLayoutSelector = LayoutSelector(mCornerRadius!!)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        //super(context, attrs)
        //resources = getResources()
        mTintColor = getResources().getColor(R.color.radio_button_selected_color)
        mUnCheckedTintColor = getResources().getColor(R.color.radio_button_unselected_color)
        mMarginDp = getResources().getDimension(R.dimen.radio_button_stroke_border).toInt()
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius)
        if (attrs != null) {
            initAttrs(attrs)
        }
        mLayoutSelector = LayoutSelector(mCornerRadius!!)
    }

    /* Reads the attributes from the layout */
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedGroup,
            0, 0
        )
        try {
            mMarginDp = typedArray.getDimension(
                R.styleable.SegmentedGroup_sc_border_width,
                getResources().getDimension(R.dimen.radio_button_stroke_border)
            ).toInt()
            mCornerRadius = typedArray.getDimension(
                R.styleable.SegmentedGroup_sc_corner_radius,
                getResources().getDimension(R.dimen.radio_button_conner_radius)
            )
            mTintColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_tint_color,
                getResources().getColor(R.color.radio_button_selected_color)
            )
            mCheckedTextColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_checked_text_color,
                getResources().getColor(android.R.color.white)
            )
            mUnCheckedTintColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_unchecked_tint_color,
                getResources().getColor(R.color.radio_button_unselected_color)
            )
        } finally {
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //Use holo light for default
        updateBackground()
    }

    fun setTintColor(tintColor: Int) {
        mTintColor = tintColor
        updateBackground()
    }

    fun setTintColor(tintColor: Int, checkedTextColor: Int) {
        mTintColor = tintColor
        mCheckedTextColor = checkedTextColor
        updateBackground()
    }

    fun setUnCheckedTintColor(unCheckedTintColor: Int, unCheckedTextColor: Int) {
        mUnCheckedTintColor = unCheckedTintColor
        updateBackground()
    }

    fun updateBackground() {
        mDrawableMap = java.util.HashMap()
        val count = super.getChildCount()
        for (i in 0 until count) {
            val child = getChildAt(i)
            updateBackground(child)

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break
            val initParams = child.layoutParams as LayoutParams
            val params = LayoutParams(initParams.width, initParams.height, initParams.weight)
            // Check orientation for proper margins
            if (orientation == HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0)
            } else {
                params.setMargins(0, 0, 0, -mMarginDp)
            }
            child.layoutParams = params
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateBackground(view: View) {

        val checked = mLayoutSelector!!.getSelected()
        val unchecked = mLayoutSelector!!.getUnselected()
        //Set text color
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(mTintColor, mCheckedTextColor)
        )
        (view as Button).setTextColor(colorStateList)

        //Redraw with tint color
        val checkedDrawable = getResources().getDrawable(checked).mutate()
        val uncheckedDrawable = getResources().getDrawable(unchecked).mutate()
        (checkedDrawable as GradientDrawable).setColor(mTintColor)
        checkedDrawable.setStroke(mMarginDp, mTintColor)
        (uncheckedDrawable as GradientDrawable).setStroke(mMarginDp, mTintColor)
        uncheckedDrawable.setColor(mUnCheckedTintColor)
        //Set proper radius
        checkedDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)
        uncheckedDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)
        val maskDrawable = getResources().getDrawable(unchecked).mutate() as GradientDrawable
        maskDrawable.setStroke(mMarginDp, mTintColor)
        maskDrawable.setColor(mUnCheckedTintColor)
        maskDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)
        val maskColor =
            Color.argb(50, Color.red(mTintColor), Color.green(mTintColor), Color.blue(mTintColor))
        maskDrawable.setColor(maskColor)
        val pressedDrawable = LayerDrawable(arrayOf<Drawable>(uncheckedDrawable, maskDrawable))
        val drawables = arrayOf<Drawable>(uncheckedDrawable, checkedDrawable)
        val transitionDrawable = TransitionDrawable(drawables)
        if ((view as RadioButton).isChecked) {
            transitionDrawable.reverseTransition(0)
        }
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(
                -android.R.attr.state_checked,
                android.R.attr.state_pressed
            ), pressedDrawable
        )
        stateListDrawable.addState(StateSet.WILD_CARD, transitionDrawable)
        mDrawableMap!![view.getId()] = transitionDrawable

        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable)
        } else {
            view.setBackgroundDrawable(stateListDrawable)
        }
        super.setOnCheckedChangeListener { group, checkedId ->
            val current = mDrawableMap!![checkedId]
            current!!.reverseTransition(200)
            if (mLastCheckId != 0) {
                val last = mDrawableMap!![mLastCheckId]
                last?.reverseTransition(200)
            }
            mLastCheckId = checkedId
            mCheckedChangeListener?.onCheckedChanged(group, checkedId)
        }
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        mDrawableMap!!.remove(child.id)
    }

    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mCheckedChangeListener = listener
    }

    /*
     * This class is used to provide the proper layout based on the view.
     * Also provides the proper radius for corners.
     * The layout is the same for each selected left/top middle or right/bottom button.
     * float tables for setting the radius via Gradient.setCornerRadii are used instead
     * of multiple xml drawables.
     */

    private inner class LayoutSelector(cornerRadius: Float) {
        private var children: Int
        private var child: Int

        /* Returns the selected layout id based on view */
        val SELECTED_LAYOUT = R.drawable.radio_checked

        /* Returns the unselected layout id based on view */
        val UNSELECTED_LAYOUT = R.drawable.radio_unchecked

        private val r //this is the radios read by attributes or xml dimens
                : Float
        private val r1 = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 0.1f, getResources().getDisplayMetrics()
        ) //0.1 dp to px
        private val rLeft // left radio button
                : FloatArray
        private val rRight // right radio button
                : FloatArray
        private val rMiddle // middle radio button
                : FloatArray
        private val rDefault // default radio button
                : FloatArray
        private val rTop // top radio button
                : FloatArray
        private val rBot // bot radio button
                : FloatArray
        private lateinit var radii // result radii float table
                : FloatArray

        init {
            children = -1 // Init this to force setChildRadii() to enter for the first time.
            child = -1 // Init this to force setChildRadii() to enter for the first time
            r = cornerRadius
            rLeft = floatArrayOf(r, r, r1, r1, r1, r1, r, r)
            rRight = floatArrayOf(r1, r1, r, r, r, r, r1, r1)
            rMiddle = floatArrayOf(r1, r1, r1, r1, r1, r1, r1, r1)
            rDefault = floatArrayOf(r, r, r, r, r, r, r, r)
            rTop = floatArrayOf(r, r, r, r, r1, r1, r1, r1)
            rBot = floatArrayOf(r1, r1, r1, r1, r, r, r, r)
        }

        fun getChildren(): Int {
            return this@SegmentedGroup.getChildCount()
        }

        fun getChildIndex(view: View): Int {
            return this@SegmentedGroup.indexOfChild(view)
        }

        private fun setChildRadii(newChildren: Int, newChild: Int) {

            // If same values are passed, just return. No need to update anything
            if (children == newChildren && child == newChild) return

            // Set the new values
            children = newChildren
            child = newChild

            // if there is only one child provide the default radio button
            radii = if (children == 1) {
                rDefault
            } else if (child == 0) { //left or top
                if (getOrientation() == HORIZONTAL) rLeft else rTop
            } else if (child == children - 1) {  //right or bottom
                if (getOrientation() == HORIZONTAL) rRight else rBot
            } else {  //middle
                rMiddle
            }
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        fun getChildRadii(view: View): FloatArray {
            val newChildren = getChildren()
            val newChild = getChildIndex(view)
            setChildRadii(newChildren, newChild)
            return radii
        }

        /* Returns the selected layout id based on view */
        fun getSelected(): Int {
            return SELECTED_LAYOUT
        }

        /* Returns the unselected layout id based on view */
        fun getUnselected(): Int {
            return UNSELECTED_LAYOUT
        }
    }
}