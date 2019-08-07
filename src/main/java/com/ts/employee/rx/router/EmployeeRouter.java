package com.ts.employee.rx.router;

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
  RouterFunction<ServerResponse> routerFunction(EmployeeHandler employeeHandler) {
    return route().POST("/api/employee/create", employeeHandler::createEmployee)
        .GET("/api/employee/{id}", employeeHandler::getEmployee).build();

  }

}
