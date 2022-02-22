package com.scaledcode.searchapi.searchrequest.composite;

import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public abstract class CompositeSearchRequest extends AbstractSearchRequest {
    @Getter(value = AccessLevel.PROTECTED)
    private List<AbstractSearchRequest> requests;
}
