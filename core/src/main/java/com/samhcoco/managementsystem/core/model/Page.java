package com.samhcoco.managementsystem.core.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.Objects.nonNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    private int page = 0;
    private int size = 20;
    private String sort = "id";
    private String sortDirection = PageSortDirection.ASC.toString();

    public PageRequest toPageRequest() {
        return PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
    }
}
