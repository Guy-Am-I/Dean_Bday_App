package com.example.happybirthday;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TopicsListAdapter extends RecyclerView.Adapter<TopicsListAdapter.TopicViewHolder> {

    private Topic[] topics;
    private final TopicsListAdapterOnClickHandler mClickHandler;

    public interface TopicsListAdapterOnClickHandler{
        void onClick(Question[] questions, int topicID);
        void showClue(String clue, int questions_answered, int total_questions);
    }

    public TopicsListAdapter(TopicsListAdapterOnClickHandler handler){
        mClickHandler = handler;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private TextView topic;
        private ImageView topic_image;
        private ImageButton clue_button;

        public TopicViewHolder(View view){
            super(view);
            topic = view.findViewById(R.id.topic);
            topic_image = view.findViewById(R.id.topic_image);
            clue_button = view.findViewById(R.id.show_clue);
            view.setOnClickListener(this);

            clue_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //show popup window
                    int pos = getAdapterPosition();
                    Topic topic_chosen = topics[pos];
                    Log.d("show clue", "answered: " + topic_chosen.getQuestions_answered() + "; out of: " + topic_chosen.getQuestions().length);
                    mClickHandler.showClue(topic_chosen.getClue(),
                            topic_chosen.getQuestions_answered(),
                            topic_chosen.getQuestions().length);
                }
            });

        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Question[] topic_questions = topics[adapterPosition].getQuestions();
            mClickHandler.onClick(topic_questions, topics[adapterPosition].getId());
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
        holder.topic_image.setImageResource(getTopicImage(topics[position].getImage_resource()));

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

    public int getTopicImage(String path){
        switch (path) {
            case "pop_culture":
                return R.drawable.pop_culture;
            case "books":
                return  R.drawable.books;
            case "math":
                return R.drawable.math;
            case "sports":
                return R.drawable.sports;
            case "computer":
                return R.drawable.computer;
            case "historical":
                return R.drawable.historical;
            case "geography":
                return R.drawable.geography;
            case "invention":
                return R.drawable.invention;
            case "quotes":
                return R.drawable.quotes;
            case "games":
                return R.drawable.games;
            case "music":
                return R.drawable.music;
            case "wildlife":
                return R.drawable.wildlife;
            case "guys_fav":
                return R.drawable.guys_fav;
            default:
                return R.drawable.guy_am_i;
        }
    }

}
