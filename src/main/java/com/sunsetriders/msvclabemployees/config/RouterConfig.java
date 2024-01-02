package com.sunsetriders.msvclabemployees.config;

import com.sunsetriders.msvclabemployees.handler.EmployeeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Value("${employee.api.findAll}")
    private String findAll;

    @Value("${employee.api.findById}")
    private String findById;

    @Value("${employee.api.save}")
    private String save;

    @Value("${employee.api.update}")
    private String update;

    @Value("${employee.api.delete}")
    private String delete;

    @Bean
    public RouterFunction<ServerResponse> routesEmployee(EmployeeHandler employeeHandler) {
        return RouterFunctions.route(RequestPredicates.GET(findAll), employeeHandler::findAll)
                .andRoute(RequestPredicates.GET(findById), employeeHandler::findById)
                .andRoute(RequestPredicates.POST(save), employeeHandler::save)
                .andRoute(RequestPredicates.PUT(update), employeeHandler::update)
                .andRoute(RequestPredicates.DELETE(delete), employeeHandler::delete);
    }
}
