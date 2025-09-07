package app.DaoS;

import java.util.List;

public interface IDTODAO<D,I>{
    D getDtoById(I id);
    List<D> getAllDtos();
}
