package islamkhsh.com.squarerepo.ui.activity.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import islamkhsh.com.squarerepo.R;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import islamkhsh.com.squarerepo.ui.adapter.RepoAdapter;
import islamkhsh.com.squarerepo.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainView {
    private MainViewModel mainViewModel;
    private RepoAdapter repoAdapter;
    private PagedList<Repo> repoList;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.search_view)
    public MaterialSearchView searchView;

    @BindView(R.id.repo_list)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupRepoRecycler();
        setupSearchView();
    }

    @Override
    public void setViewModel() {
        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void setupRepoRecycler() {
       repoAdapter = new RepoAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(repoAdapter);

        mainViewModel.getRepoList().observe(this, new Observer<PagedList<Repo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Repo> repos) {
                repoList = repos;
                repoAdapter.submitList(repos);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setupSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                repoAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                repoAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                repoAdapter.submitList(repoList);
            }
        });
    }
}
