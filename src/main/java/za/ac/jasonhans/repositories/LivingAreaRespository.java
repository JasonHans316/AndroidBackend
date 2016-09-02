package za.ac.jasonhans.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.jasonhans.domain.LivingArea;
/**
 * Created by Admin on 2016/08/21.
 */
@Repository
public interface LivingAreaRespository extends CrudRepository<LivingArea, Long> {
}
