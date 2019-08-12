package com.ts.employee.rx.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ts.employee.rx.handler.EmployeeHandler;


@EnableWebFlux
@Configuration
public class EmployeeRouter {
  @Bean
  RouterFunction<ServerResponse> employeeRouterFunction(EmployeeHandler employeeHandler) {
    return route().POST("/api/employee/create", employeeHandler::createEmployee)
        .GET("/api/employee/{id}", employeeHandler::getEmployee)
        .GET("/api/employees", employeeHandler::getAllEmployees)
        .DELETE("/api/employee/delete/{id}", employeeHandler::deleteEmployee)
        .PUT("/api/employee/{id}", employeeHandler::updateEmployee).build();
  }


  @Bean
  public RouterFunction<ServerResponse> errorRoute(EmployeeHandler employeeHandler) {
    return route(GET("/runtimeexception").and(accept(APPLICATION_JSON)),
        employeeHandler::employeeException);

  }

}
