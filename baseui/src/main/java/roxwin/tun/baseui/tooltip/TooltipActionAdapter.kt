package roxwin.tun.baseui.tooltip

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import roxwin.tun.baseui.R

class TooltipActionAdapter(private val context: Context, private var listItem: List<ActionItem>) :
    BaseAdapter() {
    private var _onItemClickListener: OnItemClickListener? = null
    private val layoutInflater = LayoutInflater.from(context)
    var onItemClickListener: OnItemClickListener?
        get() = _onItemClickListener
        set(value) {
            _onItemClickListener = value
        }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holderView =
            ViewHolder(layoutInflater.inflate(R.layout.default_tooltip_item, parent, false))
        holderView.textView = holderView.view.findViewById(R.id.default_btn_item_tooltip)
        holderView.view.setOnClickListener {
            _onItemClickListener?.onClick(listItem[position])
        }
        return holderView.view
    }

    override fun getItem(position: Int): Any = listItem[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = listItem.size

    fun setOnItemClickListener(listener: (item: ActionItem) -> Unit) {
        _onItemClickListener = object : OnItemClickListener {
            override fun onClick(item: ActionItem) {
                listener(item)
            }
        }
    }

    class ViewHolder(val view: View) {
        lateinit var textView: TextView
    }

    interface OnItemClickListener {
        fun onClick(item: ActionItem)
    }
}