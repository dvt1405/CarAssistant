package roxwin.tun.baseui.utils

import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtils<T>(private val oldList: ArrayList<T>, private val newList: ArrayList<T>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition]?.equals(newList[newItemPosition]) ?: false

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areTheSameContent(oldList[oldItemPosition], newList[oldItemPosition])

    abstract fun areTheSameContent(oldItem: T, newItem: T): Boolean
}