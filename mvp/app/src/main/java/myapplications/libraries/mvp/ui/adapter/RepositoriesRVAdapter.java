package myapplications.libraries.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplications.libraries.mvp.R;
import myapplications.libraries.mvp.mvp.presenter.IRepositoryListPresenter;
import myapplications.libraries.mvp.mvp.view.RepositoryItemView;

public class RepositoriesRVAdapter extends RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder> {
    private IRepositoryListPresenter presenter;

    public RepositoriesRVAdapter(IRepositoryListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bind(holder);
        RxView.clicks(holder.itemView).map(o -> holder).subscribe(presenter.getClickSubject());
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RepositoryItemView {
        int pos = 0;

        @BindView(R.id.tv_title)
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setTitle(String title) {
            titleTextView.setText(title);
        }
    }
}