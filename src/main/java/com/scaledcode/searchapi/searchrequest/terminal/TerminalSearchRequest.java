package com.scaledcode.searchapi.searchrequest.terminal;

import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public abstract class TerminalSearchRequest extends AbstractSearchRequest {
    @Getter(value = AccessLevel.PROTECTED)
    private String field;
    @Getter(value = AccessLevel.PROTECTED)
    private String value;
}
