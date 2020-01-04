package com.example.happybirthday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicsListAdapter extends RecyclerView.Adapter<TopicsListAdapter.TopicViewHolder> {

    private String[] topics;
    public TopicsListAdapter(String[] topics){
        this.topics = topics;
        notifyDataSetChanged();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topic;

        public TopicViewHolder(View view){
            super(view);
            topic = view.findViewById(R.id.topic);
        }

    }

    @NonNull
    @Override
    public TopicsListAdapter.TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutID = R.layout.topic_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutID, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        holder.topic.setText(topics[position]);
    }

    @Override
    public int getItemCount() {
        if(topics == null) return 0;
        return topics.length;
    }
}
