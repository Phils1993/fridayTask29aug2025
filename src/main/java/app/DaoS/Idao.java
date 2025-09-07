package app.DaoS;

import java.util.List;

public interface Idao <T, I> {

    T create(T t);

    T getById(I id);

    T update(T t);

    boolean delete(I id);

    List<T> getAll();
}
