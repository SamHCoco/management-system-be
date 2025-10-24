package com.samhcoco.managementsystem.core.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static java.util.Objects.nonNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    private Integer number;
    private Integer size;
    private String sortDirection;

    public PageRequest toPageRequest() {
        if (nonNull(number) && nonNull(size) && nonNull(sortDirection)) {
            return PageRequest.of(number, size, Sort.Direction.fromString(sortDirection));
        }
        if (nonNull(number) && nonNull(size)) {
            return PageRequest.of(number, size);
        }
        return null;
    }
}
