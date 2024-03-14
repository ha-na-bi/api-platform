package org.fn.platform.web.model.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageQuery {
    int page;
    int size;
    String sort;
    public PageQuery(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public PageQuery(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
