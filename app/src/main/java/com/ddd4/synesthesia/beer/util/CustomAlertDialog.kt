package com.ddd4.synesthesia.beer.util

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ddd4.synesthesia.beer.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomAlertDialog constructor(
    private val title: String? = null,
    private val message: String,
    private val posivie: String?,
    private val negative: String?,
    private val result: DialogInterface.OnClickListener? = null
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title ?: getString(R.string.app_name))
            .setMessage(message)
            .setPositiveButton(posivie ?: getString(R.string.ok), result)
            .setNegativeButton(negative ?: getString(R.string.cancel), null)
            .create()
    }

}