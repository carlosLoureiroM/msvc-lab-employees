package com.sunsetriders.msvclabemployees.handler;

import com.sunsetriders.msvclabemployees.model.entity.Employee;
import com.sunsetriders.msvclabemployees.model.request.EmployeeRequest;
import com.sunsetriders.msvclabemployees.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeHandler {

    public static final String ID = "id";
    private final EmployeeService employeeService;

    private final Validator validator;

    public EmployeeHandler(EmployeeService employeeService, Validator validator) {
        this.employeeService = employeeService;
        this.validator = validator;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(employeeService.findAll(), Employee.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable(ID);

        return employeeService.findById(id)
                .flatMap(employee -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(employee)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<EmployeeRequest> employeeRequestMono = request.bodyToMono(EmployeeRequest.class);

        return employeeRequestMono
                .flatMap(employeeRequest -> {
                    Errors errors = new BeanPropertyBindingResult(employeeRequest, EmployeeRequest.class.getName());
                    validator.validate(employeeRequest, errors);

                    if (errors.hasErrors()) {
                        Map<String, Object> errorResponse = createErrorResponse(errors);
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(errorResponse));
                    } else {
                        return employeeService.save(buildEmployee(employeeRequest, null))
                                .flatMap(employee -> ServerResponse.created(URI.create("/employee/".concat(employee.getId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(employee)));
                    }

                })
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;

                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");
        Mono<EmployeeRequest> employeeRequestMono = request.bodyToMono(EmployeeRequest.class);

        return employeeService.findById(id)
                .flatMap(employeeRequest ->
                {

                    Errors errors = new BeanPropertyBindingResult(employeeRequestMono, EmployeeRequest.class.getName());
                    validator.validate(employeeRequestMono, errors);

                    if (errors.hasErrors()) {
                        Map<String, Object> errorResponse = createErrorResponse(errors);
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(errorResponse));
                    } else {
                        return employeeRequestMono
                                .flatMap(employee -> employeeService.update(buildEmployee(employee, id)))
                                .flatMap(employeeEntity -> ServerResponse
                                        .created(URI.create("/employee/".concat(employeeEntity.getId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(employeeEntity)));
                    }

                })
                .switchIfEmpty(ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .body(BodyInserters.fromValue("NOT FOUND")))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;

                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return employeeService.delete(id)
                .then(ServerResponse.noContent().build());

    }

    private Employee buildEmployee(EmployeeRequest employeeRequest, String id){
        return Employee.builder()
                .id(id)
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .gender(employeeRequest.getGender())
                .email(employeeRequest.getGender())
                .phoneNumber(employeeRequest.getPhoneNumber())
                .hireDate(employeeRequest.getHireDate())
                .department(employeeRequest.getDepartment())
                .jobTitle(employeeRequest.getJobTitle())
                .salary(employeeRequest.getSalary())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private Map<String, Object> createErrorResponse(Errors errors) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<String> errorList = errors.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        errorResponse.put("errors", errorList);
        errorResponse.put("timestamp", LocalDateTime.now());

        return errorResponse;
    }
}
