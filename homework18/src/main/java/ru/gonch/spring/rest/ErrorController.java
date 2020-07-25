package ru.gonch.spring.rest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleNotFound() {
        return new ModelAndView("error-404");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error-500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied() {
        return new ModelAndView("error-403");
    }

    @RequestMapping("/403")
    public String accessDeniedPage() {
        return "error-403";
    }
}
