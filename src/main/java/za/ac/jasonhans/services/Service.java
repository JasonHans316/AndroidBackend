package za.ac.jasonhans.services;

import java.util.Set;

/**
 * Created by Admin on 2016/08/21.
 */
public interface Service<E, ID> {

    E create(E entity);

    E readById(ID id);

    Set<E> readAll();

    E update(E entity);

    void delete(E entity);


}
