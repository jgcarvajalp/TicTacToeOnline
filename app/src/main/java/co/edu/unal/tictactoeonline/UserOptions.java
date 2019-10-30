package co.edu.unal.tictactoeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.unal.tictactoeonline.model.Game;

public class UserOptions extends AppCompatActivity {

    private TextView userEmail;
    private Game game;
    private String ID_GAME = "ID_GAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
        userEmail = (TextView) findViewById(R.id.textCuentaUsuario);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            userEmail.setText(email);
        }

        game = new Game();

    }

    public void createGame(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("game");
        String key = myRef.push().getKey();

        game.setOwner(userEmail.getText().toString().trim());
        game.setStatus("C");
        game.setCompetitor("");
        game.setTurn("");
        game.setB1("");
        game.setB2("");
        game.setB3("");
        game.setB4("");
        game.setB5("");
        game.setB6("");
        game.setB7("");
        game.setB8("");
        game.setB9("");
        game.setKey(key);
        game.setResult("");
        game.setNumTies(0);
        game.setNumHuman(0);
        game.setNumAndroid(0);
        game.setNumGamesPlayed(0);
        myRef.child(key).setValue(game);
        // [END write_message]

        Intent intent = new Intent(UserOptions.this, AndroidTicTacToeActivity.class);
        intent.putExtra(ID_GAME,key);
        startActivity(intent);
    }

    public void joinGame(View view){
        Intent intent = new Intent(UserOptions.this, SelectGameActivity.class);
        startActivity(intent);
    }

}
