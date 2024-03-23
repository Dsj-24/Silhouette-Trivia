package com.company.silhouettetrivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz_Page extends AppCompatActivity {

    ImageView question;
    TextView time, rightAns, wrong;
    TextView a, b, c, d;
    Button next, finish;

    FirebaseDatabase Database = FirebaseDatabase.getInstance();
    DatabaseReference DatabaseReference = Database.getReference().child("Questions");
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String CorrectAnswer;
    int questionCount;
    int questionNumber = 1;
    String userAnswer;
    int userCorrect;
    int userWrong;
    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME = 25000;
    Boolean timerContinue;
    long lefttime = TOTAL_TIME;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        time = findViewById(R.id.textViews);
        rightAns = findViewById(R.id.textViewCN);
        wrong = findViewById(R.id.textViewICQ);
        a = findViewById(R.id.textViewA);
        b = findViewById(R.id.textViewB);
        c = findViewById(R.id.textViewC);
        d = findViewById(R.id.textViewD);
        question = findViewById(R.id.imageViewQ);
        next = findViewById(R.id.buttonNext);
        finish = findViewById(R.id.buttonFG);
        game();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                game();

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Quiz_Page.this,Results_Page.class);
                i.putExtra("correct",userCorrect);
                i.putExtra("wrong",userWrong);
                startActivity(i);
                finish();

            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userAnswer = optionA;
                if (userAnswer.equals(CorrectAnswer)) {
                    a.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    rightAns.setText("" + userCorrect);

                } else {
                    a.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(" " + userWrong);
                    markAnswer();
                }

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userAnswer = optionB;
                if (userAnswer.equals(CorrectAnswer)) {
                    b.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    rightAns.setText("" + userCorrect);
                } else {
                    b.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(" " + userWrong);
                    markAnswer();
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userAnswer = optionC;
                if (userAnswer.equals(CorrectAnswer)) {
                    c.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    rightAns.setText("" + userCorrect);
                } else {
                    c.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(" " + userWrong);
                    markAnswer();
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userAnswer = optionD;
                if (userAnswer.equals(CorrectAnswer)) {
                    d.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    rightAns.setText("" + userCorrect);
                } else {
                    d.setBackgroundColor(Color.RED);
                    userWrong++;
                    wrong.setText(" " + userWrong);
                    markAnswer();
                }
            }
        });
    }

    public void startTimer() {
       countDownTimer = new CountDownTimer(lefttime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished;
                updatecountDowntext();


            }

            @Override
            public void onFinish() {
                timerContinue = false;
                pausetimer();
                question.setImageResource(R.drawable.timeup);
            }
        }.start();
        timerContinue = true;
    }

    public void resetTimer() {
        lefttime = TOTAL_TIME;
        updatecountDowntext();
    }

    public void updatecountDowntext() {
        int second = (int) (lefttime / 1000) % 60;
        time.setText("" + second);
    }

    public void pausetimer() {
        countDownTimer.cancel();
        timerContinue=false;
    }


    public void markAnswer(){
        if(CorrectAnswer.equals(optionA)){
            a.setBackgroundColor(Color.GREEN);
        } else if (CorrectAnswer.equals(optionB)) {
            b.setBackgroundColor(Color.GREEN);
        }
        else if (CorrectAnswer.equals(optionC)) {
            c.setBackgroundColor(Color.GREEN);
        }
        else if (CorrectAnswer.equals(optionD)) {
            d.setBackgroundColor(Color.GREEN);
        }

    }
    public void game(){
        startTimer();
        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);



        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                questionCount= (int)dataSnapshot.getChildrenCount();
                optionA=dataSnapshot.child(String.valueOf(questionNumber)).child("A").getValue().toString();
                optionB=dataSnapshot.child(String.valueOf(questionNumber)).child("B").getValue().toString();
                optionC=dataSnapshot.child(String.valueOf(questionNumber)).child("C").getValue().toString();
                optionD=dataSnapshot.child(String.valueOf(questionNumber)).child("D").getValue().toString();
                CorrectAnswer=dataSnapshot.child(String.valueOf(questionNumber)).child("R").getValue().toString();

                switch (questionNumber){
                    case 1: question.setImageResource(R.drawable.zoropng);
                    break;
                    case 2: question.setImageResource(R.drawable.ryukjpng);
                    break;
                    case 3: question.setImageResource(R.drawable.denjijpng);
                        break;
                    case 4: question.setImageResource(R.drawable.mikasapng);
                        break;
                    case 5: question.setImageResource(R.drawable.yutapng);
                        break;
                    case 6: question.setImageResource(R.drawable.thorfinnpng);
                        break;
                    case 7: question.setImageResource(R.drawable.sasukejpg);
                    break;
                    case 8: question.setImageResource(R.drawable.madarapng);
                        break;
                    case 9: question.setImageResource(R.drawable.luffypng);
                }
                a.setText(optionA);
                b.setText(optionB);
                c.setText(optionC);
                d.setText(optionD);
                if (questionNumber<questionCount){
                    questionNumber++;
                }
                else {
                    Toast.makeText(Quiz_Page.this, "YOU HAVE ANSWERED ALL QUESTIONS", Toast.LENGTH_SHORT).show();



                }


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Quiz_Page.this, "THERE WAS AN ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
}