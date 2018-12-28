package tes.sa.net.ibtakar.ibtakartest.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tes.sa.net.ibtakar.ibtakartest.MvpApp;
import tes.sa.net.ibtakar.ibtakartest.R;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.adapters.PeopleAdapter;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BaseActivity;
import tes.sa.net.ibtakar.ibtakartest.ui.details.DetailsActivity;
import tes.sa.net.ibtakar.ibtakartest.utils.ItemClickListener;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.PERSON;

public class SearchActivity extends BaseActivity implements SearchMvpView, ItemClickListener {
    SearchPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.person_search)
    RecyclerView person_search;
    int page = 2;
    Boolean loading = false;
    PeopleAdapter peopleAdapter;
    List<Result> results = new ArrayList<>();
    private SearchView searchView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        AppDataManager dataManager = ((MvpApp) getApplicationContext()).getDataManager();
        presenter = new SearchPresenter<>(dataManager);
        presenter.onAttach(this);

        setViews();
    }

    @Override
    public void setViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        person_search.setLayoutManager(gridLayoutManager);
        peopleAdapter = new PeopleAdapter(this, results);
        person_search.setAdapter(peopleAdapter);
        person_search.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (loading || page == 0 || results.size() == 0)
                    return;
                int totalItemCount = gridLayoutManager.getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                        .findLastVisibleItemPosition();
                if (lastVisibleItem == totalItemCount - 1) {
                    loading = true;
                    initLoading();
                }
            }
        });
    }

    private void initLoading() {
        results.add(null);
        peopleAdapter.notifyItemInserted(results.size() - 1);
        getData();
    }

    private void getData() {
        presenter.getDataPaginated(searchView.getQuery().toString(), page);
    }

    @Override
    public void displayResult(List<Result> results) {
        ClearSearch();
        addResults(results);
    }

    private void addResults(List<Result> results) {
        this.results.addAll(results);
        notifyChange();
    }

    private void ClearSearch() {
        this.results.clear();
        notifyChange();
    }

    private void notifyChange() {
        peopleAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void concatResult(List<Result> results) {
        if (this.results.size() > 0) {
            this.results.remove(this.results.size() - 1);
            peopleAdapter.notifyItemRemoved(this.results.size());
        }

        addResults(results);
        loading = false;
    }

    @Override
    public void incrementPagination() {
        page++;
    }

    @Override
    public void stopPagination() {
        page = 0;
    }

    @Override
    public void initializePagination() {
        page = 2;
    }

    @Override
    public void OnItemClick(Result result) {
        Intent intent = DetailsActivity.getStartIntent(this);
        intent.putExtra(PERSON, result);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getResources().getString(R.string.search_people));
        presenter.getResultsQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
