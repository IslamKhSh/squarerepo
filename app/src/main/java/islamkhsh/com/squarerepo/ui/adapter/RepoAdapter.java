package islamkhsh.com.squarerepo.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import islamkhsh.com.squarerepo.R;
import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class RepoAdapter extends PagedListAdapter<Repo, RepoViewHolder> {

    private static DiffUtil.ItemCallback<Repo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Repo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
            return oldItem.equals(newItem);
        }
    };
    private Context mContext;

    public RepoAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == Constants.FIRST_ROW)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_repo_first_item, parent,
                    false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_repo_item, parent,
                false);

        return new RepoViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, final int position) {

        holder.repoName.setText(getItem(position).getName());
        holder.repoDescription.setText(getItem(position).getDescription());
        holder.repoOwnerUserName.setText(" " + getItem(position).getOwner().getLogin());

       if(getItem(position).getFork())
           holder.repoContainer.setCardBackgroundColor(ContextCompat.getColor(mContext,android.R.color.white));
       else
           holder.repoContainer.setCardBackgroundColor(ContextCompat.getColor(mContext,android.R.color.holo_green_dark));

        holder.setCurrentRepo(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return Constants.FIRST_ROW;
        return super.getItemViewType(position);
    }

}
