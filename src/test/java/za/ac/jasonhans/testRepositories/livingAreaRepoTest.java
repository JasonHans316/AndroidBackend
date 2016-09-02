package za.ac.jasonhans.testRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import za.ac.jasonhans.App;
import za.ac.jasonhans.domain.LivingArea;
import za.ac.jasonhans.repositories.LivingAreaRespository;
import za.ac.jasonhans.services.Impl.LivingAreaServiceImpl;
import za.ac.jasonhans.services.LivingAreaService;

/**
 * Created by Admin on 2016/09/02.
 */
@SpringApplicationConfiguration(classes= App.class)
@WebAppConfiguration
public class livingAreaRepoTest extends AbstractTestNGSpringContextTests {
    LivingAreaService _service;
    long livingAreaId;

    @Autowired
    LivingAreaRespository repo;

    @Test
    public void setUp() throws Exception{
        _service = LivingAreaServiceImpl.getInstance();
    }

    @Test(dependsOnMethods = "setUp")
    public void testAdd() {
        try {
            LivingArea newArea = new LivingArea.Builder()
                    .id(livingAreaId)
                    .code("Test")
                    .name("New Name")
                    .animalId(new Long("5"))
                    .active(true)
                    .spaceAvailable(45)
                    .build();

            LivingArea area = _service.createLivingArea(newArea);
            livingAreaId = area.getLivingAreaId();
            Assert.assertNotNull(livingAreaId);
        }catch(Exception x)
        {
            System.err.println(x.getMessage());
        }
    }

    @Test(dependsOnMethods = "testAdd")
    public void testGetAllRecords()
    {
        try {
            Assert.assertTrue(repo.findAll().iterator().hasNext());
        }catch(Exception x)
        {
            System.out.println(x.getMessage());
        }
    }

    @Test(dependsOnMethods = "testGetAllRecords")
    public void testUpdateRecord(){
        try{
            String updateCode = "Inserted New Code";
            LivingArea myArea = _service.readById(livingAreaId);
            LivingArea newArea = _service.update(new LivingArea.Builder()
                    .copy(myArea)
                    .code(updateCode)
                    .build());
            Assert.assertEquals(newArea.getCode(), updateCode);
        }
        catch(Exception x){
            System.err.println(x.getMessage());
        }
    }

    @Test(dependsOnMethods = "testUpdateRecord")
    public void testDeleteRecord()
    {
        try{
            LivingArea valueToDelete = _service.readById(livingAreaId);
            _service.delete(valueToDelete);
            Assert.assertNotEquals(valueToDelete, _service.readById(livingAreaId));
        }
        catch(Exception x){
            System.err.println(x.getMessage());
        }
    }
}
