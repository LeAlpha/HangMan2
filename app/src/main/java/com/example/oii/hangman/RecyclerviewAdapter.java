package com.example.oii.hangman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private ArrayList<String> word = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private ArrayList<String> player = new ArrayList<>();
    private Context context;

    public RecyclerviewAdapter(ArrayList<String> words, ArrayList<Integer> score, ArrayList<String> player, Context context) {
        this.word = words;
        this.score = score;
        this.player = player;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text1.setText(word.get(i));
        viewHolder.text2.setText(score.get(i).toString());
        viewHolder.text3.setText(player.get(i));

        if(i == 1){viewHolder.image.setImageResource(R.drawable.silver);}
        if(i == 2){viewHolder.image.setImageResource(R.drawable.bronze);}
    }

    @Override
    public int getItemCount() {
        return word.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text1, text2, text3;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.listImage);
            text1 = itemView.findViewById(R.id.listTextWord);
            text2 = itemView.findViewById(R.id.listTextPlayer);
            text3 = itemView.findViewById(R.id.listTextScore);
        }
    }
}
