package com.zeroplus.codeutility.utility.pxdpconverter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.zeroplus.codeutility.R
import com.zeroplus.codeutility.convertDpToPixel
import com.zeroplus.codeutility.convertPixelToDp
import com.zeroplus.codeutility.databinding.PxdpdialogBinding


class CustomPxDpDialog : DialogFragment(){

    var binding : PxdpdialogBinding? = null

     lateinit var context : Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        binding =  PxdpdialogBinding.inflate(inflater, container, false)
        return  binding!!.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onAttach(activity: Activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity)
        context = activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.closeImage?.setOnClickListener {
            onDestroyView()
        }

        binding?.pxdpButton?.setOnClickListener {

            var px = binding!!.pxdpInput.text.toString().toFloat()
            binding!!.pxdpText.text = convertPixelToDp(px,context).toString()
        }

        binding?.dppxButton?.setOnClickListener {

            var dp = binding!!.dppxInput.text.toString().toFloat()
            binding!!.dppxText.text = convertDpToPixel(dp,context).toString()
        }
    }
}