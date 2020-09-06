package ai.kitt.vnest.feature.screenspeech.model;

import  ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.entity.Poi;

import java.util.List;

public class ItemListResult implements ResultItem {
    private List<Poi> poiList;

    public ItemListResult(List<Poi> poiList) {
        this.poiList = poiList;
    }

    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_list_result;
    }
}
