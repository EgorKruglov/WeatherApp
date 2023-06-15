package com.example.demo.repos;

import com.example.demo.tables.tblAdminCodes;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface tblAdminCodesRepo extends CrudRepository<tblAdminCodes, Integer> {
    Optional<tblAdminCodes> findByCode(String code);
}