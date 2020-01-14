package com.app.project.repositories;

import com.app.project.domain.entities.Category;
import com.app.project.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Set<Category> findAllByIdIn(int... id);
}
