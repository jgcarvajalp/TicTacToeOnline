package co.edu.unal.tictactoeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.tictactoeonline.model.Game;
import co.edu.unal.tictactoeonline.model.GameAdapter;

public class SelectGameActivity extends AppCompatActivity {

    private RecyclerView rvGames;
    private GameAdapter adapter;
    private List<Game> gameList;
    private Button buttonJoinGame;
    private TextView textkey;
    DatabaseReference ref;
    private String ID_GAME = "ID_GAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);


        rvGames = findViewById(R.id.recyclerViewSelGame);
        rvGames.setHasFixedSize(true);
        rvGames.setLayoutManager(new LinearLayoutManager(this));
        gameList = new ArrayList<Game>();
        adapter = new GameAdapter(this,gameList);
        rvGames.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("game");
        ref.addListenerForSingleValueEvent(valuerEventListener);


    }

    ValueEventListener valuerEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Game ga = data.getValue(Game.class);
                    gameList.add(ga);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
        }
    };
}
