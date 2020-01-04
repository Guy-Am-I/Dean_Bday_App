package com.example.happybirthday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicsListAdapter extends RecyclerView.Adapter<TopicsListAdapter.TopicViewHolder> {

    private Topic[] topics;
    private final TopicsListAdapterOnClickHandler mClickHandler;

    public interface TopicsListAdapterOnClickHandler{
        void onClick(Question[] questions);
    }

    public TopicsListAdapter(TopicsListAdapterOnClickHandler handler){
        mClickHandler = handler;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private TextView topic;

        public TopicViewHolder(View view){
            super(view);
            topic = view.findViewById(R.id.topic);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Question[] topic_questions = topics[adapterPosition].getQuestions();
            mClickHandler.onClick(topic_questions);
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
        holder.topic.setText(topics[position].getTitle());
    }

    @Override
    public int getItemCount() {
        if(topics == null) return 0;
        return topics.length;
    }
    public void setTopicData(Topic[] topicData) {
        this.topics = topicData;
        notifyDataSetChanged();
    }

}
