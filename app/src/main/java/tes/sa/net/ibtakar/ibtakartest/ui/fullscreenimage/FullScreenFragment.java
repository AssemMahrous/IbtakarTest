package tes.sa.net.ibtakar.ibtakartest.ui.fullscreenimage;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tes.sa.net.ibtakar.ibtakartest.R;

import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.IMAGE_URL;
import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.STORAGE_PERMISSION;
import static tes.sa.net.ibtakar.ibtakartest.utils.Constants.lexicon;


public class FullScreenFragment extends Fragment {
    String imageUrl;
    Unbinder binder;
    final java.util.Random rand = new java.util.Random();
    final Set<String> identifiers = new HashSet<>();
    @BindView(R.id.person_image)
    SimpleDraweeView person_image;

    public FullScreenFragment() {
        // Required empty public constructor
    }

    public static FullScreenFragment newInstance(String imageUrl) {
        FullScreenFragment fragment = new FullScreenFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen, container, false);
        binder = ButterKnife.bind(this, view);

        ControllerListener listener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                updateViewSize(imageInfo);
            }

            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                updateViewSize(imageInfo);
            }
        };
        Uri uri = Uri.parse(imageUrl);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(listener)
                .build();
        person_image.setController(controller);
        return view;
    }

    @OnClick(R.id.download_image)
    public void downloadImageClick(View view) {
        if (!isPermissionGranted())
            showRequestPermissionsInfoAlertDialog();
        else downloadImage();
    }

    public boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
    }

    public void showRequestPermissionsInfoAlertDialog() {
        showRequestPermissionsInfoAlertDialog(true);
    }

    public void showRequestPermissionsInfoAlertDialog(final boolean makeSystemRequest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.ibtakar);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            dialog.dismiss();
            if (makeSystemRequest) {
                requestPermission();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void downloadImage() {
        try {
            URL url = new URL(imageUrl);
            new DownloadTask().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION &&grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadImage();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }


    private void updateViewSize(@Nullable ImageInfo imageInfo) {
        if (imageInfo != null) {
            person_image.getLayoutParams().width = imageInfo.getWidth();
            person_image.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            person_image.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }


    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {


        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                saveImageToInternalStorage(result);
                Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error Has Occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveImageToInternalStorage(Bitmap bitmap) {
        MediaStore.Images.Media.insertImage(
                getContext().getContentResolver(),
                bitmap,
                "Bird",
                "Image of bird"
        );
//        ContextWrapper wrapper = new ContextWrapper(getContext());
//
//
//        File file = wrapper.getDir("Images", MODE_PRIVATE);
//
//        file = new File(file, randomIdentifier() + ".jpg");
//        try {
//            OutputStream stream;
//            stream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            stream.flush();
//            stream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
//        Log.v("savedImage", savedImageURI.toString());
    }
}
