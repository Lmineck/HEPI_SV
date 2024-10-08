package org.hepi.hepi_sv.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hepi.hepi_sv.errorHandler.ErrorHandler;
import org.hepi.hepi_sv.errorHandler.ErrorResponse;
import org.hepi.hepi_sv.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/flt")
public class FltController {

    RequestService requestService;

    @RequestMapping("/register")
    public String register(@RequestBody HashMap<String, String> request, HttpServletRequest httpRequest) {
        requestService = new RegisterService(request, httpRequest);
        return requestService.execute();
    }

    @RequestMapping("/login")
    public String login(@RequestBody HashMap<String, String> request, HttpServletRequest httpRequest) {
        requestService = new LoginService(request, httpRequest);
        return requestService.execute();
    }

    @RequestMapping("/eventImage")
    public String eventImage(HttpServletRequest httpRequest) {
        requestService = new EventImageService(httpRequest);
        return requestService.execute();
    }

    @RequestMapping("/gymInfo")
    public String gymInfo(@RequestBody HashMap<String, String> request, HttpServletRequest httpRequest) {
        requestService = new GymInfoService(request, httpRequest);
        return requestService.execute();
    }

    @RequestMapping("/product/{type}")
    public String product(@PathVariable String type, @RequestBody HashMap<String, String> request, HttpServletRequest httpRequest) {
        requestService = new ProductService(type, request, httpRequest);;
        return requestService.execute();
    }

    @RequestMapping("/smsAuth")
    public String smsAuth(@RequestBody HashMap<String, String> request, HttpServletRequest httpRequest) {
        requestService = new SmsService(request, httpRequest);
        return requestService.execute();
    }

    @ExceptionHandler(ErrorHandler.class)
    public ResponseEntity<ErrorResponse> handleError(ErrorHandler e) {
        ErrorResponse errorResponse = new ErrorResponse("ERROR", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
