package com.example.happybirthday;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.QuestionViewHolder>{

    private Question[] questions;
    private final QuestionsListAdapterSubmitHandler mSubmitHandler;

    public QuestionsListAdapter(QuestionsListAdapterSubmitHandler mHandler){
        this.mSubmitHandler = mHandler;
    }


    public interface QuestionsListAdapterSubmitHandler{
        void submitAnswer(boolean isCorrect, String info);
        void saveData(int questionID, int topicID);
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
                // DONE save data when answered correctly (questions answered) in shared preferences
                mSubmitHandler.submitAnswer(true, quest.getInfo());
                //update saved data for topic questions answered and question id answered
                mSubmitHandler.saveData(quest.getId(), quest.getParentTopicID());

            }
            else {
                mSubmitHandler.submitAnswer(false, quest.getInfo());
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
        return new QuestionsListAdapter.QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
       holder.title.setText(questions[position].getQuestion());
       if(questions[position].getIsAnswered()){
           holder.checkbox.setImageResource(R.drawable.thumbs_up_foreground);
           holder.guess.setText(questions[position].getAnswer());
       } else {
           holder.checkbox.setImageResource(R.drawable.question_mark_foreground);
       }

    }

    @Override
    public int getItemCount() {
        if(questions == null) return 0;
        return questions.length;
    }

    public void setQuestionsData(Question[] questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

}
