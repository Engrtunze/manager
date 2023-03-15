package com.sales.manager.dto;

import lombok.Data;

@Data
public class ErrorMessageDto {
    private String message;
   private int statusCode;
   private String code;
}
