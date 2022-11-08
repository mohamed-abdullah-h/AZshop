package com.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long>{

}
