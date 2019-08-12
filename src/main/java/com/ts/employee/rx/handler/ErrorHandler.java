package com.ts.employee.rx.handler;


import java.util.function.BiFunction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ts.employee.rx.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ErrorHandler {
  private static BiFunction<HttpStatus, String, Mono<ServerResponse>> response =
      (status, value) -> ServerResponse.status(status).body(Mono.just(new ErrorResponse(value)),
          ErrorResponse.class);

  Mono<ServerResponse> notFound(ServerRequest request) {
    return response.apply(HttpStatus.NOT_FOUND, "not found");
  }

  Mono<ServerResponse> badRequest(Throwable error) {
    log.error("error raised", error);
    return response.apply(HttpStatus.BAD_REQUEST, error.getMessage());
  }


}
