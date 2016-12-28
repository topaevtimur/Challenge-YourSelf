package v.challengeyourself.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import v.challengeyourself.R;
import v.challengeyourself.model.User;

/**
 * Created by AdminPC on 28.12.2016.
 */

public class FriendsRecycleAdapter extends RecyclerView.Adapter<FriendsRecycleAdapter.FriendViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;

    @NonNull
    private List<User> friends;

    @NonNull
    MyOnClickListener listener;

    public FriendsRecycleAdapter(Context context, MyOnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setFriend(List<User> friends){
        this.friends = friends;
        notifyDataSetChanged();
    }

    public static class  FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;
        private MyOnClickListener listener;

        public FriendViewHolder(View itemView, MyOnClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.person_name);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }
/*
        static FriendViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.item_friend, parent, false);
            return new FriendViewHolder(view);
        }
*/
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onClick(position);
        }
    }
  //  private final View.OnClickListener itemOnClickListener = new MyOnClickListener();


    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend, viewGroup, false);
        //v.setOnClickListener(itemOnClickListener);
        FriendViewHolder pvh = new FriendViewHolder(v, listener);
        return pvh;
    }


    @Override
    public void onBindViewHolder(FriendViewHolder friendViewHolder, int i) {
        //TODO set smth
        final  User friend = friends.get(i);
        if(friend != null) {
            friendViewHolder.name.setText(friend.getName());
        }
    }

    @Override
    public int getItemCount() {
         return friends.size();
    }
}
