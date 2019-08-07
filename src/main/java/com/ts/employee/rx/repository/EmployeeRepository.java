package com.ts.employee.rx.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.ts.employee.rx.model.Employee;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

  Flux<Employee> findByEmail(String email);

  // Mono<Void> deleteByEmpId(Long empId);

}
