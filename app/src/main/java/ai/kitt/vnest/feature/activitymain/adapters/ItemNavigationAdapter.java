package ai.kitt.vnest.feature.activitymain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.entity.Poi;

import java.util.ArrayList;
import java.util.List;

public class ItemNavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Poi> listPoi = new ArrayList<>();
    private ItemCLickListener itemCLickListener;

    public ItemNavigationAdapter(ItemCLickListener itemCLickListener) {
        this.itemCLickListener = itemCLickListener;
    }

    public void setData(List<Poi> poiList) {
        listPoi.clear();
        listPoi.addAll(poiList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(listPoi.get(position), itemCLickListener);
    }

    @Override
    public int getItemCount() {
        return listPoi.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView addressTextView;
        private TextView distanceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title);
            addressTextView = itemView.findViewById(R.id.text_address);
            distanceTextView = itemView.findViewById(R.id.text_distance);
        }

        public void onBind(final Poi poi, final ItemCLickListener itemCLickListener) {
            titleTextView.setText(poi.getTitle());
            addressTextView.setText(poi.getAddress());
//            distanceTextView.setText(poi.getDistance()+"from here");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCLickListener.onItemClick(poi);
                }
            });
        }
    }

    public interface ItemCLickListener {
        public void onItemClick(Poi poi);
    }
}
