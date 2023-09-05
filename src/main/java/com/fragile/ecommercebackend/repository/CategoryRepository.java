package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoryRepository extends JpaRepository<Category, Long> {


//    Category findByName(String topLevelCategory);

    Category findByNameIgnoreCase(String name);

    @Query("select c from Category c where c.name=:name and c.parentCategory.name=:parentCategoryName")
    Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
