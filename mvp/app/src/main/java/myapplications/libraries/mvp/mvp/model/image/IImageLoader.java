package myapplications.libraries.mvp.mvp.model.image;

public interface IImageLoader<T> {
    void loadInto(String url, T container);
}
