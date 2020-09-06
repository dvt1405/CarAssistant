package roxwin.tun.baseui.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ArrayRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import roxwin.tun.baseui.R

class BottomSheetOptionsMenu : BottomSheetDialogFragment {
    @ArrayRes
    private var itemId: Int? = null
    private var onItemClickListener: OnItemClickListener? = null
    private var itemList: Array<CharSequence>? = null

    constructor() : super() {
    }

    constructor(
        @ArrayRes itemId: Int,
        onItemClickListener: OnItemClickListener
    ) : super() {
        this.itemId = itemId
        this.onItemClickListener = onItemClickListener
    }

    constructor(
        listItem: Array<String>,
        onItemClickListener: OnItemClickListener
    ) : super() {
        this.itemList = listItem.map { it }.toTypedArray()
        this.onItemClickListener = onItemClickListener
    }

    constructor(
        listItem: ArrayList<String>,
        onItemClickListener: OnItemClickListener
    ) : super() {
        this.itemList = listItem.map { it }.toTypedArray()
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        if (itemList.isNullOrEmpty()) {
            if (itemId != null) {
                this.itemList = requireContext().resources.getTextArray(itemId!!)
            }
        }
        if (savedInstanceState != null) {
            savedInstanceState.getStringArray(LIST_ITEM)?.let {
                itemList = it.map { it }.toTypedArray()
            }
            savedInstanceState.getParcelable<OnItemClickListener>(LISTENER)?.let {
                onItemClickListener = it
            }
        }
        itemList?.let {
            for (i in itemList!!.indices) {
                val textView = context?.let {
                    val styleRes =
                        if (i == 0) R.style.BottomSheetItem_FirstItem else R.style.BottomSheetItem
                    AppCompatTextView(
                        ContextThemeWrapper(
                            context,
                            styleRes
                        )
                    )
                }
                textView?.id = i
                view.container.addView(textView)
                textView?.text = itemList!![i]
                if (i < itemList!!.size - 1) {
                    val line = View(context)
                    line.setBackgroundColor(resources.getColor(R.color.colorGreyTransparent))
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        resources.displayMetrics.scaledDensity.toInt()
                    )
                    line.layoutParams = layoutParams
                    view.container.addView(line)
                }
                textView?.setOnClickListener {
                    onItemClickListener?.onClick(requireActivity(), i, this)
                    this.dismiss()
                }
            }
        }
        return view
    }

    fun getItemSize() = itemList?.size ?: 0
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
        view?.container?.children?.forEach {
            it.setOnClickListener {
                listener.onClick(requireActivity(),it.id,this)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray(LIST_ITEM, itemList?.map { it.toString() }?.toTypedArray())
        outState.putParcelable(LISTENER, onItemClickListener)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getStringArray(LIST_ITEM)?.let {
            itemList = it.map { it }.toTypedArray()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }

    override fun onStart() {
        super.onStart()
        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    interface OnItemClickListener : Parcelable {
        fun onClick(
            activity: FragmentActivity,
            where: Int,
            bottomSheetInterface: BottomSheetOptionsMenu
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {

        }

        override fun describeContents(): Int {
            return 0
        }
    }

    companion object {
        const val LIST_ITEM = "extras_list_item"
        const val LISTENER = "extras_listener"
    }
}