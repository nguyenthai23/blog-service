package vn.example.blog.pool;


public interface ClientProvider<T> {
    public T obtain();
    public void release( T t );
}