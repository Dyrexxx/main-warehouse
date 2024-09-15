package ru.pizza.main_warehouse.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView handleNoContentException(RuntimeException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());

        return new ModelAndView("error", model.asMap());
    }
}
