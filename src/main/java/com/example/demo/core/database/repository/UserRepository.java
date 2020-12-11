package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;


public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    UserEntity save(UserEntity userEntity);

    Iterable<UserEntity> findAll();

    Page<UserEntity> findAll(Pageable pageable);

}
