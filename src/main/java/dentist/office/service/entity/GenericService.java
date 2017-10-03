package dentist.office.service.entity;

import java.util.List;

public interface GenericService<E, K> {

    void saveOrUpdate(E entity);

    void saveAndFlush(E entity);

    void removeById(K key);

    E getById(K key);

    List<E> getAll();

}
