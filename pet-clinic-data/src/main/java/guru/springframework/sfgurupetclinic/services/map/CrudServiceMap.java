package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.BaseEntity;
import guru.springframework.sfgurupetclinic.services.CrudService;

import java.util.*;

public class CrudServiceMap<T extends BaseEntity, ID extends Long> implements CrudService<T, ID> {

    private Map<Long, T> map = new HashMap<>();

    private Long getNextId() {

        try {
            return Collections.max(map.keySet()) + 1;
        } catch (NoSuchElementException e) {
            return 1L;
        }
    }

    @Override
    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    @Override
    public T findById(ID id) {
        return map.get(id);
    }


    @Override
    public T save(T object) {

        if (object == null) throw new RuntimeException("object to save cannot be null!");

        if (object.getId() == null) object.setId(getNextId());

        map.put(object.getId(), object);

        return object;
    }


    @Override
    public void delete(T object) {

        if (object == null) throw new RuntimeException("object to delete cannot be null!");

        //map.entrySet().removeIf(entry -> entry.getValue().equals(object));
        map.remove(object.getId());
    }

    @Override
    public void deleteById(ID id) {
        map.remove(id);
    }
}
