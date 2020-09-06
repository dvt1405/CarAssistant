package roxwin.tun.baseui.recyclerview

import java.text.FieldPosition

interface SectionsAdapter {
    enum class ItemType(val value: Int) {
        TITLE(0), ITEM(1), REFRESH_ITEM(-1)
    }

    fun getItemType(position: Int): ItemType
    data class Item<I, V>(val item: I, val type: V)
}