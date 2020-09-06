package roxwin.tun.baseui.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.DialogUtils

class ConfirmDialogControl(
        @NonNull private val context: Context,
        private var _title: String?,
        private var _messages: String?,
        private var positiveButton: String?,
        private var negativeButton: String?,
        private var _listener: DialogUtils.ConfirmDialogListener?,
        private var cancelable: Boolean
) : Cloneable {
    var listener: DialogUtils.ConfirmDialogListener?
        get() = _listener
        set(value) {
            _listener = value
        }
    var messages: String?
        get() = _messages
        set(value) {
            _messages = value
            alertDialog.setMessage(_messages)
        }
    var title: String?
        get() = _title
        set(value) {
            _title = value
            alertDialog.setTitle(_title)
        }
    val alertDialog: AlertDialog =
            MaterialAlertDialogBuilder(
                    context,
                    R.style.Material_Alert_ThemeDefault
            )
                    .setCancelable(cancelable)
                    .create()

    init {
        alertDialog.setTitle(_title)
        alertDialog.setMessage(_messages)

        listener?.let {
            setPositiveButton(positiveButton) {
                it.allowClick()
            }
            setNegativeButton(negativeButton) {
                it.cancelClick()
            }
        }
    }

    constructor(
            @NonNull context: Context,
            title: String?,
            messages: String?,
            confirmButtonMessage: String?
    ) : this(
            context,
            title,
            messages,
            confirmButtonMessage,
            null,
            null,
            false
    ) {
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, confirmButtonMessage) { _, _ ->
            dismiss()
        }
    }

    fun show() {
        alertDialog.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        alertDialog.show()
    }

    fun show(messages: String?) {
        alertDialog.setMessage(messages)
        show()
    }

    fun show(title: String?, messages: String?) {
        alertDialog.setMessage(messages)
        this.title = title
        show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    fun setPositiveButton(messages: String?, listener: () -> Unit?) =
            setButton(AlertDialog.BUTTON_POSITIVE, messages, listener)

    fun setDefaultSingleButton() {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT) {
            alertDialog.dismiss()
        }
        setNegativeButton(null)
    }

    fun setDefaultDoubleButton(listener: () -> Unit?) {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT, listener)
        setNegativeButton(BUTTON_NEGATIVE_DEFAULT) { alertDialog.dismiss() }
    }

    fun setDefaultDoubleButton(allowListener: () -> Unit?, cancelListener: () -> Unit?) {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT, allowListener)
        setNegativeButton(BUTTON_NEGATIVE_DEFAULT, cancelListener)
    }

    fun setPositiveButton(messages: String?) = setButton(AlertDialog.BUTTON_POSITIVE, messages) {
        listener?.allowClick()
    }

    fun setNegativeButton(title: String?, listener: () -> Unit?) =
            setButton(AlertDialog.BUTTON_NEGATIVE, title, listener)

    fun setNegativeButton(title: String?) =
            setButton(AlertDialog.BUTTON_NEGATIVE, title) { listener?.cancelClick() }

    fun setOnDismissListener(listener: () -> Unit?) {
        alertDialog.setOnDismissListener {
            listener()
        }
    }

    fun setOnShowListener(onDialogStateListener: OnDialogStateListener) {
        alertDialog.setOnShowListener {
            onDialogStateListener.onState(it)
        }
    }

    private fun setButton(where: Int, messages: String?, listener: () -> Unit?) = alertDialog.setButton(
            where,
            messages
    ) { _: DialogInterface?, _: Int -> listener() }

    class Builder(@NonNull private val context: Context) {
        private var title: String? = null
        private var messages: String? = null
        private var positiveButton: String? = null
        private var negativeButton: String? = null
        private var onAllowClick: (() -> Unit)? = null
        private var onCancelClick: (() -> Unit)? = null
        private var isCancelable: Boolean = true
        private var onConfirmClick: OnConfirmDialogButtonClickListener? = null
        private var onCancelClickListener: OnConfirmDialogButtonClickListener? = null

        private var confirmDialogControl: ConfirmDialogControl? = null
        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun message(messages: String?): Builder {
            this.messages = messages
            return this
        }

        fun setPositiveButton(btnTitle: String?, onClick: () -> Unit): Builder {
            positiveButton = btnTitle
            this.onAllowClick = onClick
            return this
        }

        fun setPositiveButton(btnTitle: String?, onClickListener: OnConfirmDialogButtonClickListener): Builder {
            positiveButton = btnTitle
            onConfirmClick = onClickListener
            return this
        }

        fun setNegativeButton(btnTitle: String?, onClickListener: OnConfirmDialogButtonClickListener): Builder {
            onCancelClickListener = onClickListener
            return this
        }

        fun setPositiveButton(btnTitle: String?): Builder {
            positiveButton = btnTitle
            return this
        }

        fun setNegativeButton(btnTitle: String?, onClick: () -> Unit): Builder {
            negativeButton = btnTitle
            this.onCancelClick = onClick
            return this
        }

        fun setNegativeButton(btnTitle: String?): Builder {
            negativeButton = btnTitle
            return this
        }

        fun isCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun build(): ConfirmDialogControl {
            confirmDialogControl = ConfirmDialogControl(
                    context,
                    title,
                    messages,
                    positiveButton,
                    negativeButton,
                    object : DialogUtils.ConfirmDialogListener {
                        override fun allowClick() {
                            when {
                                onAllowClick != null -> {
                                    onAllowClick?.let { it() }
                                }
                                onConfirmClick != null -> {
                                    onConfirmClick?.onClick()
                                }
                                else -> {
                                    confirmDialogControl?.dismiss()
                                }
                            }
                        }

                        override fun cancelClick() {
                            when {
                                onCancelClick != null -> {
                                    onCancelClick?.let { it() }
                                }
                                onCancelClickListener != null -> {
                                    onCancelClickListener?.onClick()
                                }
                                else -> {
                                    confirmDialogControl?.dismiss()
                                }
                            }
                        }

                    }, isCancelable)
            return confirmDialogControl!!
        }
    }

    interface OnConfirmDialogButtonClickListener {
        fun onClick()
    }

    interface OnDialogStateListener {
        fun onState(dialog: DialogInterface)
    }

    companion object {
        const val BUTTON_POSITIVE_DEFAULT = "OK"
        const val BUTTON_NEGATIVE_DEFAULT = "Cancel"
        const val WRONG_TITLE = "Some things went wrong"
        const val CONFIRM_TITLE = "Confirm"
    }


}