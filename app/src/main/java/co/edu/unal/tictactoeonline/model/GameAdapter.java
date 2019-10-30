package co.edu.unal.tictactoeonline.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import co.edu.unal.tictactoeonline.AndroidTicTacToeActivity;
import co.edu.unal.tictactoeonline.R;

public class GameAdapter extends
        RecyclerView.Adapter<GameAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private Context mCtx;
    private List<Game> mGames;
    private String ID_GAME = "ID_GAME";
    DatabaseReference ref;

    // Pass in the contact array into the constructor
    public GameAdapter(Context mCtx, List<Game> games) {
        this.mCtx = mCtx;
        this.mGames = games;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameUserView;
        public TextView gameKeyview;
        public Button joinGameButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameUserView = (TextView) itemView.findViewById(R.id.textGameOwner);
            gameKeyview = (TextView) itemView.findViewById(R.id.textGameKey);
            joinGameButton = (Button) itemView.findViewById(R.id.buttonJoinGame);

        }
    }



    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.select_game_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GameAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Game game = mGames.get(position);

        // Set item views based on your views and data model
        TextView textViewOwner = viewHolder.nameUserView;
        textViewOwner.setText(game.getOwner());
        TextView  textKeyGame = viewHolder.gameKeyview;
        textKeyGame.setText(game.getKey());
        final String finKey = textKeyGame.getText().toString();
        Button button = viewHolder.joinGameButton;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, AndroidTicTacToeActivity.class);
                intent.putExtra(ID_GAME,finKey);
                mCtx.startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGames.size();
    }
}
