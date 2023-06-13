package com.example.demo.repos;

import com.example.demo.tables.tblUsers;
import org.springframework.data.repository.CrudRepository;


public interface tblUsersRepo extends CrudRepository<tblUsers, Integer> {

}