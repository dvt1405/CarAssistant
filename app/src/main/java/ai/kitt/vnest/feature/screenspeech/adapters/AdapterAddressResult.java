package ai.kitt.vnest.feature.screenspeech.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.entity.Poi;
import ai.kitt.vnest.util.AppUtil;
import ai.kitt.vnest.util.GlideApp;

import java.util.List;

public class AdapterAddressResult extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Poi> poiList;
    private Boolean isCollapse;

    public AdapterAddressResult(List<Poi> poiList) {
        this.poiList = poiList;
        isCollapse = poiList.size() > 3;
    }
    public AdapterAddressResult(List<Poi> poiList, Boolean isCollapse) {
        this.poiList = poiList;
        this.isCollapse = isCollapse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.item_load_more) {
            return new LoadMoreViewHolder(view);
        }
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isCollapse && position == 3) {
            ((LoadMoreViewHolder) holder).onBind();
            return;
        }
        ((ChildViewHolder) holder).onBind(poiList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (isCollapse && position == 3) return R.layout.item_load_more;
        return R.layout.item_result;
    }

    @Override
    public int getItemCount() {
        return isCollapse ? 4 : poiList.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemAddress;
        private TextView itemDistance;
        private ImageView imageView;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemAddress = itemView.findViewById(R.id.total_item);
            itemDistance = itemView.findViewById(R.id.item_distance);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @SuppressLint("SetTextI18n")
        public void onBind(Poi poi) {
            try {
                if(poi.getTitle() == null || poi.getTitle().trim().isEmpty()) {
                    itemName.setHeight(0);
                }
                itemView.setOnClickListener(view1 -> {
                    AppUtil.displayPointToMap(poi, itemView.getContext());
                });
                if(poi.getImg() !=null && !poi.getImg().trim().isEmpty()){
                    GlideApp.with(itemView)
                            .load(poi.getImg().trim())
                            .error(R.drawable.load_more)
                            .into(imageView);
                } else {
                    GlideApp.with(itemView)
                            .load(R.drawable.load_more)
                            .into(imageView);
                }

                itemName.setText((poi.getTitle()!=null ? poi.getTitle() : "" ));
                itemName.setSelected(true);
                itemAddress.setText((poi.getAddress() == null) ? poi.getAddress() : "");
                imageView.setClipToOutline(true);
                itemDistance.setText(((int) poi.getDistance()) + "m");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        private TextView totalItem;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItem = itemView.findViewById(R.id.total_item);
        }

        @SuppressLint("SetTextI18n")
        public void onBind() {
            totalItem.setText(poiList.size() - 3 + "+");
            itemView.setOnClickListener(v -> {
                isCollapse = false;
                notifyDataSetChanged();
            });
        }
    }
}
