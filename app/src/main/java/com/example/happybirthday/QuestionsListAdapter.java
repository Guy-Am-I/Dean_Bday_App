package com.example.happybirthday;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.QuestionViewHolder>{

    private Question[] questions;

    public QuestionsListAdapter(Question[] questions){
        this.questions = questions;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public QuestionViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.question_title);
        }

    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.question;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutID, parent, false);
        return new QuestionsListAdapter.QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.title.setText(questions[position].getQuestion());
    }

    @Override
    public int getItemCount() {
        if(questions == null) return 0;
        return questions.length;
    }
}
