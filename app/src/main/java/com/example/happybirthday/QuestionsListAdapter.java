package com.example.happybirthday;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.QuestionViewHolder>{

    private Question[] questions;

    public QuestionsListAdapter(Question[] questions){
        this.questions = questions;
    }


    public class QuestionViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private TextView title;
        private ImageButton submitBT;
        private EditText guess;
        private ImageView checkbox;

        public QuestionViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.question_title);
            submitBT = view.findViewById(R.id.submit_button);
            guess = view.findViewById(R.id.guess);
            checkbox = view.findViewById(R.id.question_checkbox);

            submitBT.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Question quest = questions[getAdapterPosition()];
            String guess_ventured = guess.getText().toString();
            if(guess_ventured.equals(quest.getAnswer())){
                quest.setAnswered(true);
                checkbox.setImageResource(R.drawable.thumbs_up_foreground);

            }
        }
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.question;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutID, parent, false);
        Log.d("Adapter","Created View HOlder");
        return new QuestionsListAdapter.QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
       holder.title.setText(questions[position].getQuestion());
       if(questions[position].getIsAnswered()){
           holder.checkbox.setImageResource(R.drawable.thumbs_up_foreground);
       } else {
           holder.checkbox.setImageResource(R.drawable.question_mark_foreground);
       }
    }

    @Override
    public int getItemCount() {
        if(questions == null) return 0;
        return questions.length;
    }
}
