package vn.example.blog.pool;


public interface ClientProvider<T> {
    T obtain();

    void release(T t);
}