package v.challengeyourself.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import v.challengeyourself.R;
import v.challengeyourself.model.Challenge;
import v.challengeyourself.storage.ChallengeStorage;

/**
 * Created by penguinni on 18.12.16.
 */

public class ChallengesRecycleAdapter
    extends RecyclerView.Adapter<ChallengesRecycleAdapter.ChallengeViewHolder> {

    public ChallengesRecycleAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.storage = new ChallengeStorage(context);
    }

    private final Context context;
    private final ChallengeStorage storage;
    private final LayoutInflater layoutInflater;
    private List<Challenge> challenges = new ArrayList<>();

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
        notifyDataSetChanged();
    }

    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ChallengeViewHolder.newInstance(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, final int position) {
        final Challenge challenge = challenges.get(position);
        Log.d("ChallengeChosen", String.valueOf(challenge.id));

        holder.challengeName.setText(challenge.challenge);
        holder.deadTime.setText(challenge.deadTime);
        holder.details.setText(challenge.details);

        switch (challenge.closed) {
            case 0:
                Log.d("Case", "0");
                holder.checkBox.setChecked(false);
                holder.checkBox.setClickable(true);
                break;
            case 2:
                Log.d("Case", "2");
                holder.checkBox.setChecked(true);
                holder.checkBox.setClickable(false);
                break;
            default:
                Log.d("Case", "default");
                break;
        }

        boolean user = false;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    storage.changeState(challenge, 2);
                    challenge.closed = 2;
                    challenges.set(position, challenge);
                } else {
                    storage.changeState(challenge, 0);
                    challenge.closed = 0;
                    challenges.set(position, challenge);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.challenges.size();
    }

    static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        final TextView challengeName, deadTime, details;
        final CheckBox checkBox;

        private ChallengeViewHolder(View itemView) {
            super(itemView);
            challengeName = (TextView) itemView.findViewById(R.id.challenge_name);
            deadTime = (TextView) itemView.findViewById(R.id.dead_time);
            details = (TextView) itemView.findViewById(R.id.description);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

        static ChallengeViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_challenge, parent, false);
            return new ChallengeViewHolder(view);
        }
    }
}
