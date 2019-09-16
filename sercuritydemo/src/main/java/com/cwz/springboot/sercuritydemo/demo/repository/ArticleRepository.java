package com.cwz.springboot.sercuritydemo.demo.repository;

import com.cwz.springboot.sercuritydemo.demo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
