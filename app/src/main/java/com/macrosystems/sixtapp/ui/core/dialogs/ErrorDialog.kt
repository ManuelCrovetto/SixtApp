package com.macrosystems.sixtapp.ui.core.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.databinding.ErrorDialogBinding
import com.macrosystems.sixtapp.ui.core.dialogs.dialogifcs.OnClickDialog

class ErrorDialog (context: Context, private var onClickDialog: OnClickDialog) : AppCompatDialog(context),
    View.OnClickListener {
    private lateinit var binding: ErrorDialogBinding
    private val mContext = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater = LayoutInflater.from(mContext)
        binding = ErrorDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOkErrorDialog.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnOkErrorDialog->{
                onClickDialog.onClicked()
                this.cancel()
            }
        }
    }


}