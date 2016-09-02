package za.ac.jasonhans.services;

import za.ac.jasonhans.domain.Animal;
import za.ac.jasonhans.domain.LivingArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2016/05/08.
 */
public interface LivingAreaService extends Service<LivingArea, Long>{
    LivingArea createLivingArea(LivingArea value);
    LivingArea findAvailability(int size);
    boolean houseAnimal(LivingArea area, Animal animal);
    ArrayList<LivingArea> getLivingAreas();
    LivingArea relocateAnimal(Animal animal);
}
