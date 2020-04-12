package org.levelup.web.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup.model.VisibilityType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class CategoriesForm {
    @NotNull
    @NotEmpty
    private String name;
    private VisibilityType visible;

}
