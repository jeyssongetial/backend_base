package com.lego.backend.infrastruture.error.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ErrorResponse {
    private List<ErrorDetail> errors;
}
