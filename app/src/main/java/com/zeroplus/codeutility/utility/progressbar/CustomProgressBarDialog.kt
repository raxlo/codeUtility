package com.zeroplus.codeutility.utility.progressbar

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zeroplus.codeutility.databinding.ProgressbardialogBinding
import com.zeroplus.codeutility.databinding.Segmenteddialog2Binding

class CustomProgressBarDialog : DialogFragment() {

    var binding : ProgressbardialogBinding? = null

    lateinit var context : Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        binding =  ProgressbardialogBinding.inflate(inflater, container, false)
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

        binding?.arcProgress?.setProgress(25f)
        binding?.circleProgress?.setProgress(50)
        binding?.dountProgress?.setProgress(75f)
    }
}