package islamkhsh.com.squarerepo.ui.adapter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import islamkhsh.com.squarerepo.R;
import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.common.util.PagedListProviderUtil;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class RepoAdapter extends PagedListAdapter<Repo,RepoAdapter.RepoViewHolder> implements Filterable {

    private Context mContext;

    public RepoAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

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

        return new RepoViewHolder(view);
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

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return Constants.FIRST_ROW;
        return super.getItemViewType(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Repo> filteredList = new ArrayList<>();

                if (charSequence.length() == 0)
                    filteredList.addAll(getCurrentList());
                else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    filteredList.clear();

                    for (Repo repo : getCurrentList()) {
                        if (repo.getName().toLowerCase().contains(filterPattern))
                            filteredList.add(repo);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                getCurrentList().removeAll(getCurrentList());
//                getCurrentList().addAll((List<Repo>) filterResults.values);
//                notifyDataSetChanged();
            }
        };
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {

        TextView repoName,repoDescription,repoOwnerUserName;
        CardView repoContainer;


        public RepoViewHolder(View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repo_name_tv);
            repoDescription = itemView.findViewById(R.id.repo_description_tv);
            repoOwnerUserName = itemView.findViewById(R.id.repo_owner_name);
            repoContainer = itemView.findViewById(R.id.repo_card);

            repoContainer.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle(R.string.long_click_menu_title);

            MenuItem repoUrl = contextMenu.add(Menu.NONE, 1, 1, R.string.meue_item_repo);
            MenuItem ownerUrl = contextMenu.add(Menu.NONE, 2, 2, R.string.meue_item_repo_owner);

            repoUrl.setOnMenuItemClickListener(this);
            ownerUrl.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String selectedUrl="";

            switch (menuItem.getItemId()){
                case 1:
                    selectedUrl = getItem(getAdapterPosition()).getHtml_url();
                    break;
                case 2:
                    selectedUrl = getItem(getAdapterPosition()).getOwner().getHtml_url();
                    break;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(selectedUrl));
            mContext.startActivity(intent);

            return true;
        }
    }


}
