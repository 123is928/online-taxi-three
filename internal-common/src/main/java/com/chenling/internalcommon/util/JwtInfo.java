package com.chenling.internalcommon.util;

import lombok.Data;

@Data
public class JwtInfo{
    String subject;
    Long issueDate;
}