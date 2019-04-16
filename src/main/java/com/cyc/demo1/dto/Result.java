package com.cyc.demo1.dto;

/**
 * @author chenyuchuan
 */
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    Integer code;

    String message;

    T data;
}
