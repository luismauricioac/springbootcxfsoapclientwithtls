package com.luismalamoc.springbootcxfsoapclientwithtls.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberToDollarsResponse {
    private String result;
}
