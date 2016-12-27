
package v.challengeyourself.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import v.challengeyourself.R;
import v.challengeyourself.model.Challenge;

import static v.challengeyourself.Constants.DATE_FORMAT;

/**
 * Created by AdminPC on 18.12.2016.
 */

public class DoneChallengesAdapter extends RecyclerView.Adapter<DoneChallengesAdapter.ChallengeViewHolder> {

    public DoneChallengesAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Challenge> challenges = new ArrayList<>();

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
        notifyDataSetChanged();
    }

    @Override
    public DoneChallengesAdapter.ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DoneChallengesAdapter.ChallengeViewHolder.newInstance(layoutInflater, parent);
    }


    @Override
    public void onBindViewHolder(DoneChallengesAdapter.ChallengeViewHolder holder, int position) {
        final Challenge challenge = challenges.get(position);
        holder.challengeName.setText(challenge.challenge);
        holder.deadTime.setText(challenge.deadTime);
        holder.details.setText(challenge.details);
    }

    @Override
    public int getItemCount() {
        return this.challenges.size();
    }

    static class ChallengeViewHolder extends RecyclerView.ViewHolder {

        final TextView challengeName, deadTime, details;

        private ChallengeViewHolder(View itemView) {
            super(itemView);
            challengeName = (TextView) itemView.findViewById(R.id.challenge_name);
            deadTime = (TextView) itemView.findViewById(R.id.dead_time);
            details = (TextView) itemView.findViewById(R.id.description);
        }

        static DoneChallengesAdapter.ChallengeViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.done_item_challenge, parent, false);
            return new DoneChallengesAdapter.ChallengeViewHolder(view);
        }
    }
}

