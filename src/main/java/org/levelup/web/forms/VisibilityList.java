package org.levelup.web.forms;

import lombok.Data;
import org.levelup.model.VisibilityType;

import java.util.Arrays;
import java.util.List;

@Data
public class VisibilityList {
    private List<VisibilityType> visibility = Arrays.asList(VisibilityType.values());
}
