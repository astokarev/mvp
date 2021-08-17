package myapplications.libraries.mvp.mvp.view;

import android.support.annotation.NonNull;

public interface RepositoryItemView {
    int getPos();
    void setTitle(@NonNull String title);
}
