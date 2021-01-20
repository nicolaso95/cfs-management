package com.example.dispatch.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CFSDataDTO {
    @NotNull
    private String eventNumber;
    @NotNull
    private String eventTypeCode;
    @NotNull
    private String responder;
}
