package com.tothenew.objects;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PagingAndSortingDto {
    private int offset;
    private int max = 10;
    private String sortBy = "id";
    private Sort.Direction order = Sort.Direction.ASC;
    private String query;
}
