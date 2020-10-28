package com.areeb.crossgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //1:yellow 2:red 0:blank

    int activePlayer = 1;
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
    int counter = 0;
    Button playAgainButton;
    TextView winnerTextView, currentPlayerTextView;
    String winner = "";
    final String YELLOWS_TURN = "Yellow's turn";
    final String REDS_TURN = "Red's turn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing xml views
        playAgainButton = findViewById(R.id.playAgainButton);
        winnerTextView = findViewById(R.id.winnerTextView);
        currentPlayerTextView = findViewById(R.id.currentPlayerTextView);
    }

    public void putDisk(View view)
    {
        //initialize image view
        ImageView imageView = (ImageView) view;

        //get clicked image tag
        int clickedTag = Integer.parseInt(imageView.getTag().toString());

        //do if clicked image is empty
        if (gameState[clickedTag] == 0 && gameActive) {

            //set defaults
            counter++;
            imageView.setTranslationY(-1500);
            imageView.animate().translationYBy(1500).rotation(3600).setDuration(300);
            gameState[clickedTag] = activePlayer;

            //choose current active player
            if (activePlayer == 1) {
                imageView.setImageResource(R.drawable.yellow);
                currentPlayerTextView.setText(REDS_TURN);
                activePlayer = 2;
            } else {
                imageView.setImageResource(R.drawable.red);
                currentPlayerTextView.setText(YELLOWS_TURN);
                activePlayer = 1;
            }

            //find winning positions
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] != 0 && gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]) {

                    gameActive = false;

                    if (activePlayer == 1) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }

                    winner = winner + " won!!";
                    winnerTextView.setText(winner);

                    currentPlayerTextView.setText("");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }

            //reset the game if matches draws
            if (counter >= 9 && winner.equals(""))
            {
                winner = "Match draw!!";
                winnerTextView.setText(winner);

                playAgainButton.setVisibility(View.VISIBLE);
                winnerTextView.setVisibility(View.VISIBLE);
                currentPlayerTextView.setText("");
            }
        }
    }

    public void setDefaults(View view)
    {
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            if (imageView.getDrawable() != null)
                imageView.setImageDrawable(null);
        }

        currentPlayerTextView.setText(YELLOWS_TURN);

        activePlayer = 1;
        gameActive = true;
        counter = 0;
        Arrays.fill(gameState, 0);
        winner = "";
    }
}