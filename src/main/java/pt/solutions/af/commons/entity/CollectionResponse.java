package pt.solutions.af.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@AllArgsConstructor
@Data
public class CollectionResponse<T> {

    private boolean hasNext;
    private Collection<T> items;

}
