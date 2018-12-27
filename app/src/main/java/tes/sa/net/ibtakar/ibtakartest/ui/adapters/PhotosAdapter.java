package tes.sa.net.ibtakar.ibtakartest.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tes.sa.net.ibtakar.ibtakartest.R;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Profile;
import tes.sa.net.ibtakar.ibtakartest.ui.details.DetailsActivity;
import tes.sa.net.ibtakar.ibtakartest.utils.CircleProgressDrawable;
import tes.sa.net.ibtakar.ibtakartest.utils.PhotoClickListener;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.BASE_IMAGE_URL;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ImageViewHolder> {
    private List<Profile> images;
    private Context context;

    public PhotosAdapter(Context c, List<Profile> images) {
        this.images = images;
        this.context = c;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.setItemClickListener((view, position1) -> ((DetailsActivity) context).clickPosition(images.get(position)));
        String url = BASE_IMAGE_URL + images.get(position).getFilePath();
        Uri uri = Uri.parse(url);
        holder.downloadData(uri);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private final SimpleDraweeView image;
        PhotoClickListener itemClickListener;

        ImageViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(this);
        }


        void setItemClickListener(PhotoClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }

        void downloadData(Uri imageData) {
            image.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(imageData)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(image.getController())
                    .setAutoPlayAnimations(true)
                    .setImageRequest(imageRequest)
                    .build();
            image.setController(controller);

        }
    }
}
