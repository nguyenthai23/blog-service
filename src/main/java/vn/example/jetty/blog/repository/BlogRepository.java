package vn.example.jetty.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.example.jetty.blog.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
}
