package com.msvc_news.msvc_news.models.dto.response;

import com.msvc_news.msvc_news.models.dto.response.enums.ErrorType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ErrorResponse {

    private String code;
    private ErrorType type;
    private String message;
    private List<String> details;
    private String timestamp;
}
