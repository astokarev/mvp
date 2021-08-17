package myapplications.libraries.mvp.mvp.model.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import myapplications.libraries.mvp.App;
import myapplications.libraries.mvp.mvp.model.entity.realm.RealmImage;
import timber.log.Timber;

public class ImageCache {
    private static final String IMAGE_FOLDER_NAME = "image";

    public static File getFile(String url) {
        RealmImage cachedImage = Realm.getDefaultInstance().where(RealmImage.class).equalTo("url", url).findFirst();
        if (cachedImage != null) {
            return new File(cachedImage.getPath());
        }
        return null;
    }

    public static boolean contains(String url) {
        return Realm.getDefaultInstance().where(RealmImage.class).equalTo("url", url).count() > 0;
    }

    public static void clear() {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.delete(RealmImage.class));
        deleteFileOrDirRecursive(getImageDir());
    }

    public static File saveImage(final String url, Bitmap bitmap) {
        if (!getImageDir().exists() && !getImageDir().mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }

        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        final File imageFile = new File(getImageDir(), SHA1(url) + fileFormat);
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(fileFormat.equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Timber.d("Failed to save image");
            return null;
        }

        Realm.getDefaultInstance().executeTransactionAsync(realm -> {
            RealmImage cachedImage = new RealmImage();
            cachedImage.setUrl(url);
            cachedImage.setPath(imageFile.toString());
            realm.copyToRealm(cachedImage);
        });

        return imageFile;
    }

    public static File getImageDir() {
        return new File(App.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }

    public static String SHA1(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public static float getSizeKb() {
        return getFileOrDirSize(getImageDir()) / 1024f;
    }

    public static void deleteFileOrDirRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteFileOrDirRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

    public static long getFileOrDirSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFileOrDirSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }
}
