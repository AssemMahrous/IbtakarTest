package tes.sa.net.ibtakar.ibtakartest.ui.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tes.sa.net.ibtakar.ibtakartest.R;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Person;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Profile;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.ui.adapters.PhotosAdapter;
import tes.sa.net.ibtakar.ibtakartest.ui.base.BaseActivity;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.BASE_IMAGE_URL;
import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.PERSON;

public class DetailsActivity extends BaseActivity implements DetailsMvpView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.person_image)
    SimpleDraweeView person_image;
    @BindView(R.id.birth_value)
    TextView birth_value;
    @BindView(R.id.known_value)
    TextView known_value;
    @BindView(R.id.biography_value)
    TextView biography_value;
    @BindView(R.id.gallery_recycler)
    RecyclerView gallery_recycler;
    PhotosAdapter photosAdapter;
    Result result;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        result = Objects.requireNonNull(getIntent().getExtras()).getParcelable(PERSON);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(result.getName());
        }

    }

    public void clickPosition(Profile profile) {

    }

    @Override
    public void setImages(List<Profile> profiles) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gallery_recycler.setLayoutManager(gridLayoutManager);
        photosAdapter = new PhotosAdapter(this, profiles);
        gallery_recycler.setAdapter(photosAdapter);
    }

    @Override
    public void setData(Person person) {
        String url=BASE_IMAGE_URL + person.getProfilePath();
       Uri uri= Uri.parse(url);
       person_image.setImageURI(uri);
        birth_value.setText(person.getBirthday());
        known_value.setText(person.getKnownForDepartment());
        biography_value.setText(person.getBiography());
    }
}
