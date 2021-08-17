package myapplications.libraries.mvp.ui.image;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import myapplications.libraries.mvp.mvp.model.cache.ImageCache;
import myapplications.libraries.mvp.mvp.model.image.IImageLoader;
import myapplications.libraries.mvp.ui.NetworkStatus;
import timber.log.Timber;

public class GlideImageLoader implements IImageLoader<ImageView> {

    private NetworkStatus networkStatus = new NetworkStatus();

    @Override
    public void loadInto(String url, ImageView container) {
        if (networkStatus.isOnline()) {
            Glide.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Timber.e(e, "Image load failed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    ImageCache.saveImage(url, resource);
                    return false;
                }
            }).into(container);
        } else {
            if (ImageCache.contains(url)) {
                Glide.with(container.getContext())
                        .load(ImageCache.getFile(url))
                        .into(container);
            }
        }
    }
}
