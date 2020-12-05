package com.example.demo.core.database.repository;

import com.example.demo.core.database.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    @Override
    Iterable<UserEntity> findAll();

    Page<UserEntity> findAll(Pageable pageable);

//    @Override
//    Optional<UserEntity> save(UserEntity userEntity);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);


}
