package com.ts.employee.rx.handler;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ts.employee.rx.model.Employee;
import com.ts.employee.rx.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeHandler {


  private final Validator validator;

  private ErrorHandler error;



  private final EmployeeService employeeService;

  @Autowired
  public EmployeeHandler(Validator validator, EmployeeService employeeService, ErrorHandler error) {
    this.validator = validator;
    this.employeeService = employeeService;
    this.error = error;
  }


  public Mono<ServerResponse> createEmployee(ServerRequest serverRequest) {
    Mono<Employee> empMono = serverRequest.bodyToMono(Employee.class);
    return empMono.flatMap(emp -> ServerResponse.status(HttpStatus.OK).contentType(APPLICATION_JSON)
        .body(employeeService.createEmployee(emp), Employee.class));
  }


  public Mono<ServerResponse> getAllEmployees(ServerRequest serverRequest) {
    Flux<Employee> emps = employeeService.findAll();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(emps, Employee.class);
  }

  public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<Employee> empMono = employeeService.getEmployeeById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    return empMono
        .flatMap(emp -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(emp)))
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> deleteEmployee(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<Employee> empMono = employeeService.getEmployeeById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return empMono.flatMap(emp -> ServerResponse.ok().contentType(APPLICATION_JSON)
        .build(employeeService.deleteEmployee(emp))).switchIfEmpty(notFound);
  }


  public Mono<ServerResponse> updateEmployee(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<Employee> updatedEmpMono = serverRequest.bodyToMono(Employee.class);
    Mono<Employee> existingEmpMono = employeeService.getEmployeeById(id);
    return updatedEmpMono
        .flatMap(emp -> validator.validate(emp).isEmpty() ? existingEmpMono.flatMap(existingEmp -> {
          existingEmp.setEmpId(emp.getEmpId());
          existingEmp.setEmail(emp.getEmail());
          existingEmp.setDepid(emp.getDepid());
          existingEmp.setName(emp.getName());
          existingEmp.setSalary(emp.getSalary());
          return employeeService.updateEmployee(existingEmp);
        }).flatMap(e -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(e)))
            .switchIfEmpty(notFound().build()) : ServerResponse.unprocessableEntity().build());
  }

  public Mono<ServerResponse> employeeException(ServerRequest serverRequest) {   
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(Flux.just("A", "B", "C", "D")
        .concatWith(Flux.error(new RuntimeException("Exception Occured")))
        .concatWith(Flux.just("E")).onErrorReturn("default value"), String.class);



  }

}
