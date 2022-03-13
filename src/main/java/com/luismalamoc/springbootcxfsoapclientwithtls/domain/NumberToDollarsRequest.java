package com.luismalamoc.springbootcxfsoapclientwithtls.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberToDollarsRequest {
    @NotNull
    private BigDecimal decimalNumber;
}
