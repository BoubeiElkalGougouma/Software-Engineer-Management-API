package com.boubeidev.exceptions;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiError {
  private String message;
  private Integer code;
  private LocalDate timestamp;

}
