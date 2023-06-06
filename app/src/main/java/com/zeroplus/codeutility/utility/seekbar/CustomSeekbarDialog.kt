package com.zeroplus.codeutility.utility.seekbar

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.zeroplus.codeutility.R
import com.zeroplus.codeutility.databinding.SeekbardialogBinding


class CustomSeekbarDialog : DialogFragment() {

    var binding : SeekbardialogBinding? = null

    lateinit var context : Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        binding =  SeekbardialogBinding.inflate(inflater, container, false)
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

        binding?.segmentedstepperseekbar?.setUpRadioOptionMenu(
            resources.getStringArray(R.array.arr_options),
            resources.getStringArray(R.array.arr_optionstag)
        )

    }
}