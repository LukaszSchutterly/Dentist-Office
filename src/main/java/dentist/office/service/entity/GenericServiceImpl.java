package dentist.office.service.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericServiceImpl<E, K extends Serializable> implements GenericService<E, K> {

    private JpaRepository<E, K> jpaRepository;

    public GenericServiceImpl(JpaRepository<E, K> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void saveOrUpdate(E entity) {
        jpaRepository.save(entity);
    }

    @Override
    public void saveAndFlush(E entity) {
        jpaRepository.saveAndFlush(entity);
    }

    @Override
    public void removeById(K key) {
        jpaRepository.delete(key);
    }

    @Override
    public E getById(K key) {
        return jpaRepository.findOne(key);
    }

    @Override
    public List<E> getAll() {
        return jpaRepository.findAll();
    }

}
