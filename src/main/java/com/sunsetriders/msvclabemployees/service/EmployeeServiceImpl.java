package com.sunsetriders.msvclabemployees.service;

import com.sunsetriders.msvclabemployees.model.entity.Employee;
import com.sunsetriders.msvclabemployees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Flux<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Mono<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Mono<Void> delete(String id) {
        return employeeRepository.deleteById(id);
    }

    @Override
    public Mono<Employee> update(Employee employee) {
        return employeeRepository.save(employee);
    }
}
