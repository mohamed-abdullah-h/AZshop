package com.main.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

	List<Product> findByCategory(String category);
	@Query("select p from Product p where p.name like %:name%")
	List<Product> findByName(@Param("name") String name);
	@Query("select p.name from Product p where p.name like %:name%")
	List<String> findProductNames(@Param("name") String name);
	
}
