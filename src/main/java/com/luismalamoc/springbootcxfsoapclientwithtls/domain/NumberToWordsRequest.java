package com.luismalamoc.springbootcxfsoapclientwithtls.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberToWordsRequest {
    @NotNull
    private BigInteger ubiNum;
}
