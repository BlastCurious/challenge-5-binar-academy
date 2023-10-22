package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.challenge_4_ilyasa_adam_naufal.R

class DialogFragment : DialogFragment() {
	@SuppressLint("PrivateResource")
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
		AlertDialog.Builder(requireContext())
			.setMessage(getString(R.string.order_confirmation))
			.setPositiveButton(getString(R.string.ok_order)) {_, _ -> }
			.create()

	companion object {
		const val TAG = "PurchaseConfirmationDialog"
	}

}