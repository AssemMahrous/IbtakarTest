package tes.sa.net.ibtakar.ibtakartest.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tes.sa.net.ibtakar.ibtakartest.MvpApp;
import tes.sa.net.ibtakar.ibtakartest.R;
import tes.sa.net.ibtakar.ibtakartest.data.AppDataManager;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.adapters.PeopleAdapter;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BaseActivity;
import tes.sa.net.ibtakar.ibtakartest.ui.details.DetailsActivity;
import tes.sa.net.ibtakar.ibtakartest.utils.ItemClickListener;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.PERSON;

public class MainActivity extends BaseActivity implements ItemClickListener, MainMvpView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.popular_people_list)
    RecyclerView popular_people_list;
    PeopleAdapter peopleAdapter;
    List<Result> results = new ArrayList<>();
    MainPresenter presenter;
    int page = 1;
    Boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppDataManager dataManager = ((MvpApp) getApplicationContext()).getDataManager();
        presenter = new MainPresenter<>(dataManager);
        presenter.onAttach(this);
        setSupportActionBar(toolbar);

        initRecyclerView();

    }

    @Override
    public void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        popular_people_list.setLayoutManager(gridLayoutManager);
        peopleAdapter = new PeopleAdapter(this, results);
        popular_people_list.setAdapter(peopleAdapter);
        popular_people_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        getData();
    }

    private void initLoading() {
        results.add(null);
        peopleAdapter.notifyItemInserted(results.size() - 1);
        getData();
    }

    @Override
    public void setData(List<Result> result) {
        if (results.size() > 0) {
            results.remove(results.size() - 1);
            peopleAdapter.notifyItemRemoved(results.size());
        }
        results.addAll(result);
        peopleAdapter.notifyDataSetChanged();
        loading = false;
    }

    @Override
    public void getData() {
        presenter.getData(page);
    }

    @Override
    public void endPagination() {
        page = 0;
    }

    @Override
    public void incrementPagination() {
        page++;
    }

    @OnClick(R.id.search_bar)
    public void searchClick(View view) {
    }

    @Override
    public void OnItemClick(Result result) {
        Intent intent = DetailsActivity.getStartIntent(this);
        intent.putExtra(PERSON, result);
        startActivity(intent);
    }
}
