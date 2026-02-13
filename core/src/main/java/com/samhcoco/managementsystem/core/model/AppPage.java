package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.enums.PageSortDirection;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppPage {
    private int page = 0;
    private int size = 20;
    private String sort = "id";
    private String sortDirection = PageSortDirection.ASC.toString();

    public PageRequest toPageRequest() {
        return PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sort);
    }
}
