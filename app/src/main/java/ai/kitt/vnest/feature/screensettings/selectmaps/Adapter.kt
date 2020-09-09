package ai.kitt.vnest.feature.screensettings.selectmaps

import ai.kitt.vnest.R
import ai.kitt.vnest.feature.screensettings.selectmaps.model.MapItems
import ai.kitt.vnest.util.NavigationUtil
import android.view.View
import kotlinx.android.synthetic.main.item_settings_maps.view.*
import roxwin.tun.baseui.recyclerview.CommonAdapter

class Adapter(val defAppId: String, val onCLick: (item: MapItems) -> Unit) : CommonAdapter<MapItems>(R.layout.item_settings_maps) {
    companion object {
        val listItems = arrayOf(MapItems(NavigationUtil.MAPS_GOOGLE_MAP_APP_ID, "Google map"), MapItems(NavigationUtil.MAPS_NATIVEL_APP_ID, "Navitel"), MapItems(NavigationUtil.MAPS_VIET_MAP_APP_ID, "Vietmap"))
    }
    init {
        listItem.addAll(listItems)
    }

    override fun onBindItem(item: MapItems, itemView: View) {
        itemView.labelView.text = item.name
        itemView.icon.visibility = if (item.mapId.equals(defAppId, ignoreCase = true)) View.VISIBLE else View.GONE
        itemView.setOnClickListener {
            onCLick(item)
        }
    }
}