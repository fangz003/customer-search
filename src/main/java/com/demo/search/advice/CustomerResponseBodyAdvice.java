package com.demo.search.advice;

import com.demo.search.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;


/**
 * This class converts List of Customer Dtos to ResponseEntity which contains data and http status code.
 */
@AllArgsConstructor
public class CustomerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        if (body instanceof List) {
            List<?> bodyList = (List<?>) body;
            try{
                if (!bodyList.isEmpty() && bodyList.get(0) instanceof Customer) {
                    return ResponseEntity.ok(bodyList);
                }
            }catch(Exception e){

            }
        }
        return body;
    }
}

