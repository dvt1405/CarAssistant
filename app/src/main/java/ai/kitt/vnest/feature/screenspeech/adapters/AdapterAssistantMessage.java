package ai.kitt.vnest.feature.screenspeech.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import ai.kitt.vnest.R;
import ai.kitt.vnest.feature.screenspeech.model.ItemAssistant;
import ai.kitt.vnest.feature.screenspeech.model.ItemListResult;
import ai.kitt.vnest.feature.screenspeech.model.ResultItem;

import java.util.ArrayList;

public class AdapterAssistantMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ResultItem> mListItem = new ArrayList<>();

    public ArrayList<ResultItem> getListItem() {
        return mListItem;
    }

    public void setListItem(ArrayList<ResultItem> mListItem) {
        this.mListItem = mListItem;
        notifyDataSetChanged();
    }

    public void addItem(ResultItem resultItem) {
        mListItem.add(resultItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case R.layout.item_mess_from_user:
                viewHolder = new UserViewHolder(view);
                break;
            case R.layout.item_mess_from_assistant:
                viewHolder = new AssistantViewHolder(view);
                break;
            case R.layout.item_list_result:
                viewHolder = new ResultViewHolder(view);
                break;
            default:
                throw new IllegalArgumentException("No item view type found!");
        }
        ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BindViewHolder) holder).onBind(mListItem.get(position));
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mListItem.get(position).getItemViewType();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder implements BindViewHolder {
        private TextView textFromUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFromUser = itemView.findViewById(R.id.text_message_body);
        }


        @Override
        public void onBind(ResultItem item) {
            textFromUser.setText(((ItemAssistant) item).getText());
        }
    }

    static class AssistantViewHolder extends RecyclerView.ViewHolder implements BindViewHolder {
        private TextView textFromAssistant;

        public AssistantViewHolder(@NonNull View itemView) {
            super(itemView);
            textFromAssistant = itemView.findViewById(R.id.text_message_body);
        }

        @Override
        public void onBind(ResultItem item) {
            ItemAssistant itemAssistant = (ItemAssistant) item;
            textFromAssistant.setText(((ItemAssistant) item).getText());

        }
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder implements BindViewHolder {
        private RecyclerView mRecyclerView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.mRecyclerView);
        }


        @Override
        public void onBind(ResultItem item) {
            ItemListResult itemListResult = (ItemListResult) item;
            AdapterAddressResult adapterResult = new AdapterAddressResult(itemListResult.getPoiList());
            mRecyclerView.setAdapter(adapterResult);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), itemView.getResources().getInteger(R.integer.list_result_span_count)));
            mRecyclerView.scrollToPosition(1);
            itemView.setOnClickListener( view -> {

            });
        }
    }

    interface BindViewHolder {
        public void onBind(ResultItem item);
    }
}
