package com.ts.employee.rx.handler;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ts.employee.rx.model.Employee;
import com.ts.employee.rx.service.EmployeeService;
import reactor.core.publisher.Mono;

@Component
public class EmployeeHandler {

  @Autowired
  private EmployeeService employeeService;

  public EmployeeHandler(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  public Mono<ServerResponse> createEmployee(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(Employee.class)
        .flatMap(c -> ServerResponse.status(HttpStatus.OK).contentType(APPLICATION_JSON)
            .body(employeeService.createEmployee(c), Employee.class));
  }


  public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<Employee> empMono = employeeService.getEmployeeById(id);

    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    return empMono
        .flatMap(emp -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(emp)))
        .switchIfEmpty(notFound);
  }



}
