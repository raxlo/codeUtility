package com.zeroplus.codeutility.utility.progressbar.kotlin

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.zeroplus.codeutility.R

class DonutProgress : View {

    private var finishedPaint: Paint? = null
    private var unfinishedPaint: Paint? = null
    private var innerCirclePaint: Paint? = null

    protected var textPaint: Paint? = null
    protected var innerBottomTextPaint: Paint? = null

    private val finishedOuterRect = RectF()
    private val unfinishedOuterRect = RectF()

    private var attributeResourceId = 0
    private var bitmap: Bitmap? = null
    private var showText = false
    private var textSize = 0f
    private var textColor = 0
    private var innerBottomTextColor = 0
    private var progress = 0f
    private var max = 0
    private var finishedStrokeColor = 0
    private var unfinishedStrokeColor = 0
    private var startingDegree = 0
    private var finishedStrokeWidth = 0f
    private var unfinishedStrokeWidth = 0f
    private var innerBackgroundColor = 0
    private var prefixText = ""
    private var suffixText = "%"
    private var text: String? = null
    private var innerBottomTextSize = 0f
    private var innerBottomText: String? = null
    private var innerBottomTextHeight = 0f

    private var default_stroke_width = 0f
    private val default_finished_color = Color.rgb(66, 145, 241)
    private val default_unfinished_color = Color.rgb(204, 204, 204)
    private val default_text_color = Color.rgb(66, 145, 241)
    private val default_inner_bottom_text_color = Color.rgb(66, 145, 241)
    private val default_inner_background_color = Color.TRANSPARENT
    private val default_max = 100
    private val default_startingDegree = 0
    private var default_text_size = 0f
    private var default_inner_bottom_text_size = 0f
    private var min_size = 0
    private val clockWise = false


    private val INSTANCE_STATE = "saved_instance"
    private val INSTANCE_TEXT_COLOR = "text_color"
    private val INSTANCE_TEXT_SIZE = "text_size"
    private val INSTANCE_TEXT = "text"
    private val INSTANCE_INNER_BOTTOM_TEXT_SIZE = "inner_bottom_text_size"
    private val INSTANCE_INNER_BOTTOM_TEXT = "inner_bottom_text"
    private val INSTANCE_INNER_BOTTOM_TEXT_COLOR = "inner_bottom_text_color"
    private val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
    private val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
    private val INSTANCE_MAX = "max"
    private val INSTANCE_PROGRESS = "progress"
    private val INSTANCE_SUFFIX = "suffix"
    private val INSTANCE_PREFIX = "prefix"
    private val INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width"
    private val INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width"
    private val INSTANCE_BACKGROUND_COLOR = "inner_background_color"
    private val INSTANCE_STARTING_DEGREE = "starting_degree"
    private val INSTANCE_INNER_DRAWABLE = "inner_drawable"

    constructor(context: Context?) : super(context){
        default_text_size = sp2px(resources, 18f)
        min_size = dp2px(resources, 100f).toInt()
        default_stroke_width = dp2px(resources, 10f)
        default_inner_bottom_text_size = sp2px(resources, 18f)
        val attributes = context?.theme
            ?.obtainStyledAttributes(null, R.styleable.DonutProgress, 0, 0)
        if (attributes != null) {
            initByAttributes(attributes)
        }
        attributes?.recycle()
        //Init the inner bitmap, we don't want to do this inside onDraw().
        initInnerBitmap(context)
        initPainters()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        default_text_size = sp2px(resources, 18f)
        min_size = dp2px(resources, 100f).toInt()
        default_stroke_width = dp2px(resources, 10f)
        default_inner_bottom_text_size = sp2px(resources, 18f)
        val attributes = context?.theme
            ?.obtainStyledAttributes(attrs, R.styleable.DonutProgress, 0, 0)
        if (attributes != null) {
            initByAttributes(attributes)
        }
        attributes?.recycle()
        //Init the inner bitmap, we don't want to do this inside onDraw().
        initInnerBitmap(context)
        initPainters()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        default_text_size = sp2px(resources, 18f)
        min_size = dp2px(resources, 100f).toInt()
        default_stroke_width = dp2px(resources, 10f)
        default_inner_bottom_text_size = sp2px(resources, 18f)
        val attributes = context.theme
            .obtainStyledAttributes(attrs, R.styleable.DonutProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        //Init the inner bitmap, we don't want to do this inside onDraw().
        initInnerBitmap(context)
        initPainters()
    }

    protected fun initPainters() {
        if (showText) {
            textPaint = TextPaint()
            (textPaint as TextPaint).setColor(textColor)
            (textPaint as TextPaint).setTextSize(textSize)
            (textPaint as TextPaint).setAntiAlias(true)
            innerBottomTextPaint = TextPaint()
            (innerBottomTextPaint as TextPaint).setColor(innerBottomTextColor)
            (innerBottomTextPaint as TextPaint).setTextSize(innerBottomTextSize)
            (innerBottomTextPaint as TextPaint).setAntiAlias(true)
        }
        finishedPaint = Paint()
        finishedPaint!!.setColor(finishedStrokeColor)
        finishedPaint!!.setStyle(Paint.Style.STROKE)
        finishedPaint!!.setAntiAlias(true)
        finishedPaint!!.setStrokeWidth(finishedStrokeWidth)
        unfinishedPaint = Paint()
        unfinishedPaint!!.setColor(unfinishedStrokeColor)
        unfinishedPaint!!.setStyle(Paint.Style.STROKE)
        unfinishedPaint!!.setAntiAlias(true)
        unfinishedPaint!!.setStrokeWidth(unfinishedStrokeWidth)
        innerCirclePaint = Paint()
        innerCirclePaint!!.setColor(innerBackgroundColor)
        innerCirclePaint!!.setAntiAlias(true)
    }

    protected fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor = attributes
            .getColor(R.styleable.DonutProgress_donut_finished_color, default_finished_color)
        unfinishedStrokeColor = attributes
            .getColor(R.styleable.DonutProgress_donut_unfinished_color, default_unfinished_color)
        showText = attributes.getBoolean(R.styleable.DonutProgress_donut_show_text, true)
        attributeResourceId = attributes
            .getResourceId(R.styleable.DonutProgress_donut_inner_drawable, 0)
        setMax(attributes.getInt(R.styleable.DonutProgress_donut_max, default_max))
        setProgress(attributes.getFloat(R.styleable.DonutProgress_donut_progress, 0f))
        finishedStrokeWidth = attributes
            .getDimension(
                R.styleable.DonutProgress_donut_finished_stroke_width,
                default_stroke_width
            )
        unfinishedStrokeWidth = attributes
            .getDimension(
                R.styleable.DonutProgress_donut_unfinished_stroke_width,
                default_stroke_width
            )
        if (showText) {
            if (attributes.getString(R.styleable.DonutProgress_donut_prefix_text) != null) {
                prefixText = attributes.getString(R.styleable.DonutProgress_donut_prefix_text)!!
            }
            if (attributes.getString(R.styleable.DonutProgress_donut_suffix_text) != null) {
                suffixText = attributes.getString(R.styleable.DonutProgress_donut_suffix_text)!!
            }
            if (attributes.getString(R.styleable.DonutProgress_donut_text) != null) {
                text = attributes.getString(R.styleable.DonutProgress_donut_text)
            }
            textColor = attributes
                .getColor(R.styleable.DonutProgress_donut_text_color, default_text_color)
            textSize = attributes
                .getDimension(R.styleable.DonutProgress_donut_text_size, default_text_size)
            innerBottomTextSize = attributes
                .getDimension(
                    R.styleable.DonutProgress_donut_inner_bottom_text_size,
                    default_inner_bottom_text_size
                )
            innerBottomTextColor = attributes
                .getColor(
                    R.styleable.DonutProgress_donut_inner_bottom_text_color,
                    default_inner_bottom_text_color
                )
            innerBottomText = attributes
                .getString(R.styleable.DonutProgress_donut_inner_bottom_text)
        }
        innerBottomTextSize = attributes
            .getDimension(
                R.styleable.DonutProgress_donut_inner_bottom_text_size,
                default_inner_bottom_text_size
            )
        innerBottomTextColor = attributes
            .getColor(
                R.styleable.DonutProgress_donut_inner_bottom_text_color,
                default_inner_bottom_text_color
            )
        innerBottomText = attributes.getString(R.styleable.DonutProgress_donut_inner_bottom_text)
        startingDegree = attributes
            .getInt(R.styleable.DonutProgress_donut_circle_starting_degree, default_startingDegree)
        innerBackgroundColor = attributes
            .getColor(
                R.styleable.DonutProgress_donut_background_color,
                default_inner_background_color
            )
    }

    protected fun initInnerBitmap(context: Context?) {
        if (attributeResourceId != 0) {
            bitmap = getBitmap(context, attributeResourceId)
        }
    }

    protected fun initInnerBitmap() {
        initInnerBitmap(context)
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    fun isShowText(): Boolean {
        return showText
    }

    fun setShowText(showText: Boolean) {
        this.showText = showText
    }

    fun getFinishedStrokeWidth(): Float {
        return finishedStrokeWidth
    }

    fun setFinishedStrokeWidth(finishedStrokeWidth: Float) {
        this.finishedStrokeWidth = finishedStrokeWidth
        this.invalidate()
    }

    fun getUnfinishedStrokeWidth(): Float {
        return unfinishedStrokeWidth
    }

    fun setUnfinishedStrokeWidth(unfinishedStrokeWidth: Float) {
        this.unfinishedStrokeWidth = unfinishedStrokeWidth
        this.invalidate()
    }

    private fun getProgressAngle(): Float {
        return getProgress() / max.toFloat() * 360f
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        if (this.progress > getMax()) {
            this.progress %= getMax().toFloat()
        }
        invalidate()
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        if (max > 0) {
            this.max = max
            invalidate()
        }
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        this.invalidate()
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        this.invalidate()
    }

    fun getFinishedStrokeColor(): Int {
        return finishedStrokeColor
    }

    fun setFinishedStrokeColor(finishedStrokeColor: Int) {
        this.finishedStrokeColor = finishedStrokeColor
        this.invalidate()
    }

    fun getUnfinishedStrokeColor(): Int {
        return unfinishedStrokeColor
    }

    fun setUnfinishedStrokeColor(unfinishedStrokeColor: Int) {
        this.unfinishedStrokeColor = unfinishedStrokeColor
        this.invalidate()
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
        this.invalidate()
    }

    fun getSuffixText(): String? {
        return suffixText
    }

    fun setSuffixText(suffixText: String?) {
        this.suffixText = suffixText!!
        this.invalidate()
    }

    fun getPrefixText(): String? {
        return prefixText
    }

    fun setPrefixText(prefixText: String?) {
        this.prefixText = prefixText!!
        this.invalidate()
    }

    fun getInnerBackgroundColor(): Int {
        return innerBackgroundColor
    }

    fun setInnerBackgroundColor(innerBackgroundColor: Int) {
        this.innerBackgroundColor = innerBackgroundColor
        this.invalidate()
    }


    fun getInnerBottomText(): String? {
        return innerBottomText
    }

    fun setInnerBottomText(innerBottomText: String?) {
        this.innerBottomText = innerBottomText
        this.invalidate()
    }


    fun getInnerBottomTextSize(): Float {
        return innerBottomTextSize
    }

    fun setInnerBottomTextSize(innerBottomTextSize: Float) {
        this.innerBottomTextSize = innerBottomTextSize
        this.invalidate()
    }

    fun getInnerBottomTextColor(): Int {
        return innerBottomTextColor
    }

    fun setInnerBottomTextColor(innerBottomTextColor: Int) {
        this.innerBottomTextColor = innerBottomTextColor
        this.invalidate()
    }

    fun getStartingDegree(): Int {
        return startingDegree
    }

    fun setStartingDegree(startingDegree: Int) {
        this.startingDegree = startingDegree
        this.invalidate()
    }

    fun getAttributeResourceId(): Int {
        return attributeResourceId
    }

    fun setAttributeResourceId(attributeResourceId: Int) {
        this.attributeResourceId = attributeResourceId
        initInnerBitmap()
        this.invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))

        //TODO calculate inner circle height and then position bottom text at the bottom (3/4)
        innerBottomTextHeight = (height - height * 3 / 4).toFloat()
    }

    private fun measure(measureSpec: Int): Int {
        var result: Int
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = min_size
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val delta = Math.max(finishedStrokeWidth, unfinishedStrokeWidth)
        finishedOuterRect[delta, delta, width - delta] = height - delta
        unfinishedOuterRect[delta, delta, width - delta] = height - delta
        val innerCircleRadius = (width - Math
            .min(finishedStrokeWidth, unfinishedStrokeWidth) + Math
            .abs(finishedStrokeWidth - unfinishedStrokeWidth)) / 2f
        canvas.drawCircle(
            width / 2.0f, height / 2.0f, innerCircleRadius,
            innerCirclePaint!!
        )
        if (!clockWise) {
            canvas.drawArc(
                finishedOuterRect, -(360f - getStartingDegree()), -getProgressAngle(), false,
                finishedPaint!!
            )
            canvas.drawArc(
                unfinishedOuterRect,
                -(360f - getStartingDegree()) - getProgressAngle(),
                -(360f - getProgressAngle()),
                false,
                unfinishedPaint!!
            )
        } else {
            canvas.drawArc(
                finishedOuterRect, getStartingDegree().toFloat(), getProgressAngle(), false,
                finishedPaint!!
            )
            canvas.drawArc(
                unfinishedOuterRect,
                getStartingDegree() + getProgressAngle(),
                360 - getProgressAngle(),
                false,
                unfinishedPaint!!
            )
        }
        if (showText) {
            val text = if (text != null) text else prefixText + progress + suffixText
            if (!TextUtils.isEmpty(text)) {
                val textHeight = textPaint!!.descent() + textPaint!!.ascent()
                canvas.drawText(
                    text!!, (width - textPaint!!
                        .measureText(text)) / 2.0f, (width - textHeight) / 2.0f, textPaint!!
                )
            }
            if (!TextUtils.isEmpty(getInnerBottomText())) {
                innerBottomTextPaint!!.textSize = innerBottomTextSize
                val bottomTextBaseline = height - innerBottomTextHeight - (textPaint!!.descent() + textPaint!!.ascent()) / 2
                canvas.drawText(
                    getInnerBottomText()!!,
                    (width - innerBottomTextPaint!!
                        .measureText(getInnerBottomText())) / 2.0f,
                    bottomTextBaseline,
                    innerBottomTextPaint!!
                )
            }
        }
        if (bitmap != null) {
            canvas.drawBitmap(
                bitmap!!, (width - bitmap!!
                    .width) / 2.0f, (height - bitmap!!.height) / 2.0f, null
            )
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor())
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize())
        bundle.putFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE, getInnerBottomTextSize())
        bundle.putFloat(
            INSTANCE_INNER_BOTTOM_TEXT_COLOR,
            getInnerBottomTextColor().toFloat()
        )
        bundle.putString(INSTANCE_INNER_BOTTOM_TEXT, getInnerBottomText())
        bundle.putInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR, getInnerBottomTextColor())
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor())
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor())
        bundle.putInt(INSTANCE_MAX, getMax())
        bundle.putInt(INSTANCE_STARTING_DEGREE, getStartingDegree())
        bundle.putFloat(INSTANCE_PROGRESS, getProgress())
        bundle.putString(INSTANCE_SUFFIX, getSuffixText())
        bundle.putString(INSTANCE_PREFIX, getPrefixText())
        bundle.putString(INSTANCE_TEXT, getText())
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, getFinishedStrokeWidth())
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, getUnfinishedStrokeWidth())
        bundle.putInt(INSTANCE_BACKGROUND_COLOR, getInnerBackgroundColor())
        bundle.putInt(INSTANCE_INNER_DRAWABLE, getAttributeResourceId())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val bundle = state
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR)
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE)
            innerBottomTextSize = bundle.getFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE)
            innerBottomText = bundle.getString(INSTANCE_INNER_BOTTOM_TEXT)
            innerBottomTextColor = bundle.getInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR)
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR)
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH)
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH)
            innerBackgroundColor = bundle.getInt(INSTANCE_BACKGROUND_COLOR)
            attributeResourceId = bundle.getInt(INSTANCE_INNER_DRAWABLE)
            initInnerBitmap()
            initPainters()
            setMax(bundle.getInt(INSTANCE_MAX))
            setStartingDegree(bundle.getInt(INSTANCE_STARTING_DEGREE))
            setProgress(bundle.getFloat(INSTANCE_PROGRESS))
            prefixText = bundle.getString(INSTANCE_PREFIX)!!
            suffixText = bundle.getString(INSTANCE_SUFFIX)!!
            text = bundle.getString(INSTANCE_TEXT)
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    fun setDonut_progress(percent: String) {
        if (!TextUtils.isEmpty(percent)) {
            setProgress(percent.toInt().toFloat())
        }
    }
}