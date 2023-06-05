package com.zeroplus.codeutility.utility.segmented

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.zeroplus.codeutility.R
import com.zeroplus.codeutility.databinding.Segmenteddialog2Binding
import com.zeroplus.codeutility.utility.segmented.kotlin.SegmentedGroup


class CustomSegmentedDialog2 : DialogFragment(),RadioGroup.OnCheckedChangeListener,
View.OnClickListener {
    var binding : Segmenteddialog2Binding? = null

    lateinit var context : Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        binding =  Segmenteddialog2Binding.inflate(inflater, container, false)
        return  binding!!.root
    }

    override fun onStart() {
        super.onStart()
         val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        // val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
         dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onAttach(activity: Activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity)
        context = activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addSegmented?.setOnClickListener(this@CustomSegmentedDialog2);
        binding?.removeSegmented?.setOnClickListener(this@CustomSegmentedDialog2);
        binding?.segmented5?.setOnCheckedChangeListener(this);
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.button21 -> Toast.makeText(activity, "One", Toast.LENGTH_SHORT).show()
            R.id.button22 -> Toast.makeText(activity, "Two", Toast.LENGTH_SHORT).show()
            R.id.button31 -> Toast.makeText(activity, "One", Toast.LENGTH_SHORT).show()
            R.id.button32 -> Toast.makeText(activity, "Two", Toast.LENGTH_SHORT).show()
            R.id.button33 -> Toast.makeText(activity, "Three", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_segmented -> binding?.let { addButton(it.segmented5) }
            R.id.remove_segmented -> binding?.let { removeButton(it.segmented5) }
            else -> {}
        }
    }

    private fun addButton(group: SegmentedGroup) {
        val radioButton =
            context.layoutInflater.inflate(R.layout.radio_button_item, null) as RadioButton
        radioButton.text = "Button " + (group.getChildCount() + 1)
        group.addView(radioButton)
        group.updateBackground()
    }

    private fun removeButton(group: SegmentedGroup) {
        if (group.getChildCount() < 1) return
        group.removeViewAt(group.getChildCount() - 1)
        group.updateBackground()

        //Update margin for last item
        if (group.getChildCount() < 1) return
        val layoutParams = RadioGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 0)
        group.getChildAt(group.getChildCount() - 1).setLayoutParams(layoutParams)
    }

}