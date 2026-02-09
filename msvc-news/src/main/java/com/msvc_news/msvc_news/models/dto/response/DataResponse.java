package com.msvc_news.msvc_news.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DataResponse<T> {
    private String message;
    private Boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL) // Include data only if it's not null
    private T data;
}
