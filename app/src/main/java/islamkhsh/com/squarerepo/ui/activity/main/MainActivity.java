package islamkhsh.com.squarerepo.ui.activity.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import islamkhsh.com.squarerepo.ui.adapter.FilteredRepoAdapter;
import islamkhsh.com.squarerepo.ui.adapter.RepoAdapter;
import islamkhsh.com.squarerepo.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainView {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.search_view)
    public MaterialSearchView searchView;
    @BindView(R.id.repo_list)
    public RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel mainViewModel;
    private RepoAdapter repoAdapter;
    private FilteredRepoAdapter filteredRepoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupRepoRecycler();
        setupSearchView();
        setupSwipeRefresh();
    }


    @Override
    public void setViewModel() {
        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public void setupRepoRecycler() {
        repoAdapter = new RepoAdapter(this);
        filteredRepoAdapter = new FilteredRepoAdapter(this, new ArrayList<Repo>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(repoAdapter);

        //for change adapters
        mainViewModel.getInSearchMode().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean inSearch) {
                if (inSearch)
                    recyclerView.setAdapter(filteredRepoAdapter);
                else
                    recyclerView.setAdapter(repoAdapter);
            }
        });

        //for original adapter
        mainViewModel.getRepoList(this).observe(this, new Observer<PagedList<Repo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Repo> repos) {
                repoAdapter.submitList(repos);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //for search adapter
        mainViewModel.getResultList().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                filteredRepoAdapter.setFilteredList(repos);
            }
        });

    }

    @Override
    public void setupSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.refreshCache(MainActivity.this);
                setupRepoRecycler();
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainViewModel.setRepoPagedList(repoAdapter.getCurrentList());
                mainViewModel.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                mainViewModel.setInSearchMode(true);
            }

            @Override
            public void onSearchViewClosed() {
                mainViewModel.setInSearchMode(false);
            }
        });
    }


}
