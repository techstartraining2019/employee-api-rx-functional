package com.ts.employee.rx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.employee.rx.model.Employee;
import com.ts.employee.rx.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class EmployeeServiceImpl implements EmployeeService {


  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public Flux<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Mono<Employee> getEmployeeById(String id) {
    return employeeRepository.findById(id);
  }

  @Override
  public Flux<Employee> findByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }

  @Override
  public Mono<Employee> createEmployee(Employee emp) {
    return employeeRepository.save(emp);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return employeeRepository.deleteById(id);
  }

  @Override
  public Mono<Employee> updateEmployee(Employee emp) {
    return employeeRepository.save(emp);
  }

  @Override
  public Mono<Void> deleteEmployee(Employee emp) {
    return employeeRepository.delete(emp);
  }


  /*
   * @Override public Mono<Void> deleteByEmpId(Long empId) { return
   * employeeRepository.deleteByEmpId(empId); }
   */


}
