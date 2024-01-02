package com.sunsetriders.msvclabemployees.repository;

import com.sunsetriders.msvclabemployees.model.entity.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
