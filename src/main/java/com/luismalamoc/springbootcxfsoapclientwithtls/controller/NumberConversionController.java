package com.luismalamoc.springbootcxfsoapclientwithtls.controller;

import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsRequest;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToDollarsResponse;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsRequest;
import com.luismalamoc.springbootcxfsoapclientwithtls.domain.NumberToWordsResponse;
import com.luismalamoc.springbootcxfsoapclientwithtls.service.NumberConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class NumberConversionController {

    private final NumberConversionService numberConversionService;

    @RequestMapping(value = "/numberToWords", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NumberToWordsResponse> numberToWords(@RequestBody @Valid NumberToWordsRequest request){
        return ResponseEntity.ok(numberConversionService.numberToWords(request));
    }

    @RequestMapping(value = "/numberToDollars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NumberToDollarsResponse> numberToDollars(@RequestBody @Valid NumberToDollarsRequest request){
        return ResponseEntity.ok(numberConversionService.numberToDollars(request));
    }
}
