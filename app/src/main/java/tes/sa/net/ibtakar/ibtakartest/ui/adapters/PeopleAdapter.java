package tes.sa.net.ibtakar.ibtakartest.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tes.sa.net.ibtakar.ibtakartest.R;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Result;
import tes.sa.net.ibtakar.ibtakartest.utils.CircleProgressDrawable;
import tes.sa.net.ibtakar.ibtakartest.utils.ItemClickListener;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.BASE_IMAGE_URL;

public class PeopleAdapter extends RecyclerView.Adapter {
    private List<Result> results;
    private ItemClickListener itemClickListener;
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM1 = 1;

    public PeopleAdapter(ItemClickListener itemClickListener, List<Result> results) {
        this.itemClickListener = itemClickListener;
        this.results = results;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.people_list_item, parent, false);
            vh = new peopleViewHolder(itemView);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_loading, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int viewType) {
        if (holder instanceof peopleViewHolder) {
            ((peopleViewHolder) holder).bindTo(results.get(viewType));
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    class peopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.people_image)
        SimpleDraweeView people_image;
        @BindView(R.id.people_name)
        TextView people_name;
        private Result result;

        public peopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(result);
            }
        }

        public void bindTo(Result result) {
            this.result = result;
            people_name.setText(result.getName());
            Uri imageData = Uri.parse(BASE_IMAGE_URL + result.getProfilePath());
            people_image.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(imageData)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(people_image.getController())
                    .setAutoPlayAnimations(true)
                    .setImageRequest(imageRequest)
                    .build();
            people_image.setController(controller);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (results.get(position) == null)
            return VIEW_PROG;
        else return VIEW_ITEM1;
    }
}
