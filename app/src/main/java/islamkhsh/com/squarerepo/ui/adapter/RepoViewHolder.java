package islamkhsh.com.squarerepo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import islamkhsh.com.squarerepo.R;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/26/2018.
 */

public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener {

    TextView repoName, repoDescription, repoOwnerUserName;
    CardView repoContainer;
    Context mContext;
    private Repo currentRepo;

    public RepoViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        repoName = itemView.findViewById(R.id.repo_name_tv);
        repoDescription = itemView.findViewById(R.id.repo_description_tv);
        repoOwnerUserName = itemView.findViewById(R.id.repo_owner_name);
        repoContainer = itemView.findViewById(R.id.repo_card);
        this.mContext = context;
    }

    public void setCurrentRepo(Repo currentRepo) {
        this.currentRepo = currentRepo;
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
        String selectedUrl = "";

        switch (menuItem.getItemId()) {
            case 1:
                selectedUrl = currentRepo.getHtml_url();
                break;
            case 2:
                selectedUrl = currentRepo.getOwner().getHtml_url();
                break;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(selectedUrl));
        mContext.startActivity(intent);

        return true;
    }


}
