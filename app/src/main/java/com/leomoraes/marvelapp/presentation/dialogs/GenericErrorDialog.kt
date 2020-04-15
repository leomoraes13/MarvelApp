package com.leomoraes.marvelapp.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.leomoraes.marvelapp.R
import kotlinx.android.synthetic.main.dialog_error.*

class GenericErrorDialog(private val message: String) : DialogFragment() {
    private lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_error, container)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setStyle(STYLE_NO_FRAME, R.style.DialogError)
        isCancelable = false

        val parent = targetFragment ?: context
        assert(parent is Listener) { "$parent must implement GenericErrorDialog.Listener" }
        listener = parent as Listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTexts(view)
        setupButtons(view)
    }

    private fun setupTexts(view: View) {
        view.apply {
            headerTextView.text = context.getString(R.string.dialog_error_title)
            messageTextView.text = message
            positiveButton.text = context.getString(R.string.dialog_error_ok_button)
        }
    }

    private fun setupButtons(view: View) {
        view.apply {
            positiveButton.setOnClickListener {
                positiveButtonAction()
                dismiss()
            }
        }
    }

    fun positiveButtonAction() = listener.onDialogOkAction()

    interface Listener {
        fun onDialogOkAction()
    }
}