package myapplications.libraries.mvp.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import myapplications.libraries.mvp.App;
import myapplications.libraries.mvp.R;
import myapplications.libraries.mvp.mvp.model.image.IImageLoader;
import myapplications.libraries.mvp.mvp.presenter.MainPresenter;
import myapplications.libraries.mvp.mvp.view.MainView;
import myapplications.libraries.mvp.ui.adapter.RepositoriesRVAdapter;
import myapplications.libraries.mvp.ui.image.GlideImageLoader;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @BindView(R.id.rl_loading)
    RelativeLayout loadingRelativeLayout;

    RepositoriesRVAdapter adapter;

    IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter providePresenter() {
        MainPresenter presenter = new MainPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepositoriesRVAdapter(presenter.getCountriesListPresenter());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loadingRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void setUsername(String login) {
        usernameTextView.setText(login);
    }

    @Override
    public void loadImage(String avatarUrl) {
        imageLoader.loadInto(avatarUrl, avatarImageView);
    }
}
