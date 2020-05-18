package com.example.demo.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entities.MainEntity;

public interface MainRepository extends JpaRepository<MainEntity, Long>, JpaSpecificationExecutor<MainEntity> {

    List<MainEntity> findByIdIn(Set<Long> ids);

}
