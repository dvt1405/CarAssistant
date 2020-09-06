package ai.kitt.vnest.feature.activitymain.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;

import ai.kitt.vnest.R;

public class DefaultAssistantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static Pair<Integer, String>[] defaultItems = new Pair[]{new Pair<>(R.drawable.ic_ytb, "Youtube"),
            new Pair<>(R.drawable.ic_nav, "Maps"),
            new Pair<>(R.drawable.ic_fuel, "Fuel History"),
            new Pair<>(R.drawable.ic_round_settings_24, "Settings"),
            new Pair<>(R.drawable.ic_add, "Add more")
    };
    private ArrayList<Pair<Integer, String>> defList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int numItems = -1;

    public DefaultAssistantAdapter(OnItemClickListener onItemClickListener) {
        setDefaultList();
        this.onItemClickListener = onItemClickListener;
    }

    public DefaultAssistantAdapter(OnItemClickListener onItemClickListener, int numItems) {
        setDefaultList();
        this.onItemClickListener = onItemClickListener;
        this.numItems = numItems;
    }

    public void setDefaultList() {
        if (numItems == -1) {
            numItems = 5;
        }
        Pair<Integer, String>[] defItems = defaultItems;
        defList.addAll(Arrays.asList(defItems).subList(0, numItems));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_nav_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(defList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return defList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView itemName;
        private AppCompatImageView itemIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_names);
            itemIcon = itemView.findViewById(R.id.item_icon);
        }

        public void onBind(final Pair<Integer, String> item, final OnItemClickListener onItemClick) {
            itemName.setText(item.second);
            itemIcon.setImageDrawable(itemView.getContext().getDrawable(item.first));
            itemView.setOnClickListener(v -> onItemClick.onClick(item.second, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onClick(String text, int position);
    }
}
