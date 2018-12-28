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

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
    int page = 1;
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
    }

    @Override
    public void displayResult(List<Result> results) {
        ClearSearch();
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
        searchView.setQueryHint("Enter Movie name..");
        presenter.getResultsQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
