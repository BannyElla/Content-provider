package org.levelup.web.rest.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryStatistic {
    long countAllCategories;
    int countPrivateCategories;
    int countPublicCategories;
    int countArticlesInAllCategories;
    int countArticlesInPrivateCategories;
    int countArticlesInPublicCategories;
}
