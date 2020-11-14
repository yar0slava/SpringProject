package com.example.demo.database.repository;

import com.example.demo.database.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

//    UserEntity save(UserEntity userEntity);
//
//    Optional<UserEntity> findById(Long id);


    @Override
    Iterable<UserEntity> findAll();
}
