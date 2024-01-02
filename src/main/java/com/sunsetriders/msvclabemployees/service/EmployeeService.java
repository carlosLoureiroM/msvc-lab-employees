package com.sunsetriders.msvclabemployees.service;

import com.sunsetriders.msvclabemployees.model.entity.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Flux<Employee> findAll();

    Mono<Employee> findById(String id);

    Mono<Employee> save(Employee employee);

    Mono<Void> delete(String id);

    Mono<Employee> update(Employee employee);
}
