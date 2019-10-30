package co.edu.unal.tictactoeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import co.edu.unal.tictactoeonline.model.Game;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private TicTacToeGame mGame;
    private boolean mGameOver;
    private TextView mHumanRes;
    private TextView mTiesRes;
    private TextView mAndroidRes;
    private int numHumanWon;
    private int numTies;
    private int numAndroidWon;
    private int numPlayedGames;
    private boolean androidTurn = false;
    private boolean humanTurn = false;
    static final int DIALOG_QUIT_ID = 1;
    static final int DIALOG_ABOUT_ID = 2;
    private BoardView mBoardView;
    private SharedPreferences mPrefs;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    Handler handler = new Handler();
    private boolean mSoundOn;
    private char mComputerTurn;

    private int mSelectedIndex;
    ImageView image;
    private String ID_GAME = "ID_GAME";
    private String mIdString;
    private String userEmail;
    DatabaseReference ref;
    private Game game;
    private boolean mReadyToPlay = false;
    private char turn;


    ValueEventListener valuerEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                game = dataSnapshot.getValue(Game.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



                if(game.getStatus().equals("C")){
                    if(!TextUtils.isEmpty(game.getOwner()) && !TextUtils.isEmpty(game.getCompetitor())){
                        mReadyToPlay = true;
                        Toast.makeText(AndroidTicTacToeActivity.this, "¡Listos para jugar!",
                                Toast.LENGTH_SHORT).show();
                        mHumanRes.setText(game.getOwner().substring(0,3) + ": " + numHumanWon);
                        mAndroidRes.setText(game.getCompetitor().substring(0,3) + ": " + numAndroidWon);
                        //
                        numHumanWon = 0;
                        numTies = 0;
                        numAndroidWon = 0;
                        numPlayedGames = 1;
                        //
                        startNewGame();
                    }else{
                        mReadyToPlay = false;
                        mInfoTextView.setText("¡Falta un jugador!");
                        if(user.getEmail().equals(game.getOwner())){
                            mHumanRes.setText(game.getOwner().substring(0,3) + ": " + numHumanWon);
                            mAndroidRes.setText("P2: ");
                        }else{
                            mHumanRes.setText("P1: ");
                            mAndroidRes.setText(user.getEmail().substring(0,3) + ": " + numAndroidWon);
                            ref.child("competitor").setValue(String.valueOf(user.getEmail()));
                        }
                        mGameOver =  true;
                    }

                }else{
                    if(game.getB1().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 0);
                    }else if(game.getB1().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 0);
                    }

                    if(game.getB2().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 1);
                    }else if(game.getB2().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 1);
                    }

                    if(game.getB3().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 2);
                    }else if(game.getB3().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 2);
                    }

                    if(game.getB4().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 3);
                    }else if(game.getB4().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 3);
                    }

                    if(game.getB5().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 4);
                    }else if(game.getB5().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 4);
                    }

                    if(game.getB6().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 5);
                    }else if(game.getB6().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 5);
                    }

                    if(game.getB7().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 6);
                    }else if(game.getB7().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 6);
                    }

                    if(game.getB8().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 7);
                    }else if(game.getB8().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 7);
                    }

                    if(game.getB9().equals(String.valueOf(TicTacToeGame.HUMAN_PLAYER))){
                        mGame.setMove(TicTacToeGame.HUMAN_PLAYER, 8);
                    }else if(game.getB9().equals(String.valueOf(TicTacToeGame.COMPUTER_PLAYER))){
                        mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, 8);
                    }

                    if(game.getResult().equals(game.getOwner())){
                        String defaultMessage = (" - Ganó " + game.getOwner());
                        mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                        numHumanWon = game.getNumHuman();
                        mHumanRes.setText(game.getOwner().substring(0,3) + ": " + numHumanWon);
                    }else if(game.getResult().equals(game.getCompetitor())){

                        String defaultMessage = (" - Ganó " + game.getCompetitor());
                        mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                        numAndroidWon = game.getNumAndroid();
                        mAndroidRes.setText(game.getCompetitor().substring(0,3) + ": " + numAndroidWon);

                    }else if(game.getResult().equals("tie")){
                        String defaultMessage = (" - Empate ");
                        mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                        numTies = game.getNumTies();
                        mTiesRes.setText(getResources().getString(R.string.num_ties) + numTies);
                    }else{
                        mInfoTextView.setText("Turno de " + game.getTurn());
                    }
                    mBoardView.invalidate();   // Redraw the board

                }


            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanRes = (TextView) findViewById(R.id.humanRes);
        mTiesRes = (TextView) findViewById(R.id.tiesRes);
        mAndroidRes = (TextView) findViewById(R.id.androidRes);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mIdString = intent.getStringExtra(ID_GAME);

        ref = FirebaseDatabase.getInstance().getReference("game").child(mIdString);
        ref.addValueEventListener(valuerEventListener);


        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        numHumanWon = 0;
        numTies = 0;
        numAndroidWon = 0;
        numPlayedGames = 1;
        /*mHumanRes.setText(R.string.human_won + numHumanWon);
        mAndroidRes.setText(R.string.android_won + numAndroidWon);*/
        mTiesRes.setText(R.string.num_ties + numTies);

        mBoardView.setOnTouchListener(mTouchListener);

        // Restore the scores from the persistent preference data source
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level",
                getResources().getString(R.string.difficulty_harder));
        if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

        //startNewGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sword);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.swish);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    private void startNewGame(){
        mGame.clearBoard();
        //mBoardView.invalidate();

        /*ref.child("status").setValue("P");
        ref.child("b1").setValue("");
        ref.child("b2").setValue("");
        ref.child("b3").setValue("");
        ref.child("b4").setValue("");
        ref.child("b5").setValue("");
        ref.child("b6").setValue("");
        ref.child("b7").setValue("");
        ref.child("b8").setValue("");
        ref.child("b9").setValue("");
        ref.child("result").setValue("");*/

        if(numPlayedGames % 2 == 0){
            mInfoTextView.setText("Turno de " + game.getCompetitor());
            //ref.child("turn").setValue(String.valueOf(game.getCompetitor()));
            Map<String, Object> newGameUpdates = new HashMap<>();
            newGameUpdates.put("turn", game.getCompetitor());
            newGameUpdates.put("status", "P");
            newGameUpdates.put("b1", "");
            newGameUpdates.put("b2", "");
            newGameUpdates.put("b3", "");
            newGameUpdates.put("b4", "");
            newGameUpdates.put("b5", "");
            newGameUpdates.put("b6", "");
            newGameUpdates.put("b7", "");
            newGameUpdates.put("b8", "");
            newGameUpdates.put("b9", "");
            newGameUpdates.put("result", "");
            ref.updateChildren(newGameUpdates);

        }else{
            mInfoTextView.setText("Turno de " + game.getOwner());
            Map<String, Object> newGameUpdates = new HashMap<>();
            newGameUpdates.put("turn", game.getOwner());
            newGameUpdates.put("status", "P");
            newGameUpdates.put("b1", "");
            newGameUpdates.put("b2", "");
            newGameUpdates.put("b3", "");
            newGameUpdates.put("b4", "");
            newGameUpdates.put("b5", "");
            newGameUpdates.put("b6", "");
            newGameUpdates.put("b7", "");
            newGameUpdates.put("b8", "");
            newGameUpdates.put("b9", "");
            newGameUpdates.put("result", "");
            ref.updateChildren(newGameUpdates);
         //   ref.child("turn").setValue(String.valueOf(game.getOwner()));
        }
        mBoardView.invalidate();
        mGameOver = false;
    }

    private boolean validateTurn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!game.getStatus().equals("P")){
            return false;
        }else{
            return game.getTurn().equals(user.getEmail());
        }

    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mGameOver)	{
                if(validateTurn()){

                    if(game.getTurn().equals(game.getOwner())){
                        setMove(TicTacToeGame.HUMAN_PLAYER, pos);
                        //If no winner yet, let the computer make a move
                        int winner = mGame.checkForWinner();

                        if(winner == 0){
                            mInfoTextView.setText("Turno de " + game.getCompetitor());
                            ref.child("turn").setValue(String.valueOf(game.getCompetitor()));
                        }else if(winner == 1){
                            mInfoTextView.setText(R.string.result_tie);
                            numTies++;
                            mTiesRes.setText(getResources().getString(R.string.num_ties) + numTies);
                            ref.child("result").setValue("tie");
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numTies").setValue(numTies);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                        else if (winner == 2) {
                            numHumanWon++;
                            mHumanRes.setText(game.getOwner().substring(0,3) + ": " + numHumanWon);
                            String defaultMessage = (" - Ganó " + game.getOwner());
                            mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                            ref.child("result").setValue(game.getOwner());
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numHuman").setValue(numHumanWon);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                        else{
                            String defaultMessage = (" - Ganó " + game.getCompetitor());
                            mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                            numAndroidWon++;
                            mAndroidRes.setText(game.getCompetitor().substring(0,3) + ": " + numAndroidWon);
                            ref.child("result").setValue(game.getCompetitor());
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numAndroid").setValue(numAndroidWon);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                    }

                    if(game.getTurn().equals(game.getCompetitor())){
                        setMove(TicTacToeGame.COMPUTER_PLAYER, pos);
                        //If no winner yet, let the computer make a move
                        int winner = mGame.checkForWinner();

                        if(winner == 0){
                            mInfoTextView.setText("Turno de " + game.getOwner());
                            ref.child("turn").setValue(String.valueOf(game.getOwner()));
                        }else if(winner == 1){
                            mInfoTextView.setText(R.string.result_tie);
                            numTies++;
                            mTiesRes.setText(getResources().getString(R.string.num_ties) + numTies);
                            ref.child("result").setValue("tie");
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numTies").setValue(numTies);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                        else if (winner == 2) {
                            numHumanWon++;
                            mHumanRes.setText(game.getOwner().substring(0,3) + ": " + numHumanWon);
                            String defaultMessage = (" - Ganó " + game.getOwner());
                            mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                            ref.child("result").setValue(game.getOwner());
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numHuman").setValue(numHumanWon);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                        else{
                            String defaultMessage = (" - Ganó " + game.getCompetitor());
                            mInfoTextView.setText(mPrefs.getString("victory_message" + defaultMessage, defaultMessage));
                            ref.child("result").setValue(game.getCompetitor());
                            numAndroidWon++;
                            mAndroidRes.setText(game.getCompetitor().substring(0,3) + ": " + numAndroidWon);
                            mGameOver = true;
                            numPlayedGames++;
                            ref.child("numAndroid").setValue(numAndroidWon);
                            ref.child("numGamesPlayed").setValue(numPlayedGames);
                        }
                    }

                }
            }

// So we aren't notified of continued events when finger is moved
            return false;
        }



    };

    private boolean setMove(char player, int location){
        if(mGame.getBoardOccupant(location) == ' '){
            mGame.setMove(player, location);
            mBoardView.invalidate();   // Redraw the board
            if(player == TicTacToeGame.HUMAN_PLAYER){
                if(mSoundOn){
                    mHumanMediaPlayer.start();
                }
            }else{
                if(mSoundOn){
                    mComputerMediaPlayer.start();
                }
            }

            switch (location){
                case 0:
                    ref.child("b1").setValue(String.valueOf(player));

                    break;

                case 1:
                    ref.child("b2").setValue(String.valueOf(player));

                    break;

                case 2:
                    ref.child("b3").setValue(String.valueOf(player));

                    break;

                case 3:
                    ref.child("b4").setValue(String.valueOf(player));

                    break;

                case 4:
                    ref.child("b5").setValue(String.valueOf(player));

                    break;

                case 5:
                    ref.child("b6").setValue(String.valueOf(player));

                    break;

                case 6:
                    ref.child("b7").setValue(String.valueOf(player));

                    break;

                case 7:
                    ref.child("b8").setValue(String.valueOf(player));

                    break;

                case 8:
                    ref.child("b9").setValue(String.valueOf(player));

                    break;
            }

            return true;
        }else{
            return false;
        }

    }

    /*private boolean setMove(char player, int location) {
        if(mSoundOn){
            if(player == TicTacToeGame.HUMAN_PLAYER){
                mHumanMediaPlayer.start();
            }
        }

        if(player == TicTacToeGame.COMPUTER_PLAYER){
            final int loc = location;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, loc);
                    mBoardView.invalidate();   // Redraw the board
                    if(mSoundOn){
                        mComputerMediaPlayer.start();
                    }


                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mComputerTurn = TicTacToeGame.HUMAN_PLAYER;
                        mInfoTextView.setText(R.string.turn_human);
                    }
                    else if(winner == 1){
                        mInfoTextView.setText(R.string.result_tie);
                        numTies++;
                        mTiesRes.setText(getResources().getString(R.string.num_ties) + numTies);
                        mGameOver = true;
                        numPlayedGames++;
                    }
                    else if (winner == 2) {
                        numHumanWon++;
                        mHumanRes.setText(getResources().getString(R.string.human_won) + numHumanWon);
                        String defaultMessage = getResources().getString(R.string.result_human_wins);
                        mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                        mGameOver = true;
                        numPlayedGames++;
                    }
                    else{
                        mInfoTextView.setText(R.string.result_computer_wins);
                        numAndroidWon++;
                        mAndroidRes.setText(getResources().getString(R.string.android_won) + numAndroidWon);
                        mGameOver = true;
                        numPlayedGames++;
                    }
                }
            }, 1000);
            return true;
        }

        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();   // Redraw the board
            return true;
        }
        return false;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;

            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
            case R.id.about:
                showDialog(DIALOG_ABOUT_ID);
                return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id) {

            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog

                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();

                break;

            case DIALOG_ABOUT_ID:
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();


                break;
        }

        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            // Apply potentially new settings

            mSoundOn = mPrefs.getBoolean("sound", true);


            String difficultyLevel = mPrefs.getString("difficulty_level",
                    getResources().getString(R.string.difficulty_harder));

            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            else
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }




}
