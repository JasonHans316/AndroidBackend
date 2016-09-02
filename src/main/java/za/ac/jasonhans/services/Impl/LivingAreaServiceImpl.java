package za.ac.jasonhans.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.jasonhans.domain.Animal;
import za.ac.jasonhans.domain.LivingArea;
import za.ac.jasonhans.repositories.LivingAreaRespository;
import za.ac.jasonhans.services.LivingAreaService;

import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Admin on 2016/05/08.
 */
@Service
public class LivingAreaServiceImpl implements LivingAreaService{

    @Autowired
    private LivingAreaRespository repository;

    private static LivingAreaServiceImpl service = null;

    public static LivingAreaServiceImpl getInstance()
    {
        if(service == null)
            service = new LivingAreaServiceImpl();
        return service;
    }

    private LivingAreaServiceImpl()
    {
    }

    @Override
    public LivingArea createLivingArea(LivingArea value) {
        try{
            return repository.save(value);
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return null;
    }

    /*
    * Used to find which living area has space for the animal selected
    * */
    @Override
    public LivingArea findAvailability(int size) {
        try{
            ArrayList<LivingArea> myList = getLivingAreas();


            for(int i=0; i<myList.size(); i++)
                if(myList.get(i).getSpaceAvailable() > size)
                    return myList.get(i);
            return new LivingArea.Builder().build();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return null;
    }

    /**
     * Used for returning a list of living areas
     * */
    @Override
    public ArrayList<LivingArea> getLivingAreas() {
        try{
            ArrayList<LivingArea> result = new ArrayList<>();
            if(result.addAll((ArrayList<LivingArea>)repository.findAll()))
                return result;
            else
                return new ArrayList<LivingArea>();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return null;
    }
/**
 * Used to house animal in the selected living area updating the animal and living area records
 * */
    @Override
    public boolean houseAnimal(LivingArea area, Animal animal) {
        try{

            area = new LivingArea.Builder().copy(area)
                    .spaceAvailable(area.getSpaceAvailable()-animal.getSpaceRequired())
                    .animalId(animal.getAnimalId())
                    .build();
            boolean result = repository.save(area) == null;
            return !result;
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return false;
    }
/**
 * Used to move an animal from one location to another
 * */
    @Override
    public LivingArea relocateAnimal(Animal animal) {
        try{
            ArrayList<LivingArea> myList = getLivingAreas();

            LivingArea current = null;

            for(int i=0; i<myList.size(); i++)
                if(myList.get(i).getAnimal() == animal.getAnimalId()) {
                    current = myList.get(i);
                    i = myList.size()+2;
                }
            if(houseAnimal(current, animal))
                for(int i=0; i<myList.size(); i++)
                    if(myList.get(i).getAnimal() == 1/*animal.getAnimalId().intValue()*/
                            && current != myList.get(i)) {
                        current = myList.get(i);
                        break;
                    }

            if(current.getAnimal() != animal.getAnimalId())
                return current;
            else
                return new LivingArea.Builder().build();

        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return null;
    }

    @Override
    public LivingArea create(LivingArea entity) {
        try{
            return repository.save(entity);
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return null;
    }

    @Override
    public LivingArea readById(Long aLong) {
        return null;
    }

    @Override
    public Set<LivingArea> readAll() {
        return null;
    }

    @Override
    public LivingArea update(LivingArea entity) {
        return null;
    }

    @Override
    public void delete(LivingArea entity) {

    }
}
