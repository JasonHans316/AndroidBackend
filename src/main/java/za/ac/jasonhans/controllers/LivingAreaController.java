package za.ac.jasonhans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import za.ac.jasonhans.domain.LivingArea;
import za.ac.jasonhans.services.LivingAreaService;

import java.util.Set;

/**
 * Created by Admin on 2016/08/21.
 */
@RestController
public class LivingAreaController {
    @Autowired
    private LivingAreaService _service;

    @RequestMapping(value = "/livingarea/", method = RequestMethod.POST)
    public ResponseEntity<Void> createLivingArea(@RequestBody LivingArea area, UriComponentsBuilder ucBuilder) {
        _service.create(area);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/livingarea/{id}").buildAndExpand(area.getLivingAreaId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/livingarea{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LivingArea> getLivingArea(@PathVariable ("id") long id) {
        LivingArea area = _service.readById(id);
        return area == null ? new ResponseEntity<LivingArea>(HttpStatus.NOT_FOUND) : new ResponseEntity<LivingArea>(area, HttpStatus.OK);
    }

    @RequestMapping(value = "/livingarea/", method = RequestMethod.GET)
    public ResponseEntity<Set<LivingArea>> getAreas() {
        Set<LivingArea> areas = _service.readAll();
        if(areas.isEmpty()){
            return new ResponseEntity<Set<LivingArea>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Set<LivingArea>>(areas, HttpStatus.OK);
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.PUT)
    public ResponseEntity<LivingArea> updateArea(@PathVariable("id") long id, @RequestBody LivingArea newArea) {
        LivingArea existingArea = _service.readById(id);
        if (existingArea==null)
            return new ResponseEntity<LivingArea>(HttpStatus.NOT_FOUND);

        LivingArea updatedArea = new LivingArea.Builder().copy(existingArea)
                .code(newArea.getCode())
                .name(newArea.getName())
                .animalId(newArea.getAnimal())
                .spaceAvailable(newArea.getSpaceAvailable())
                .build();
        _service.update(updatedArea);
        return new ResponseEntity<LivingArea>(updatedArea, HttpStatus.OK);
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<LivingArea> deleteArea(@PathVariable("id") long id) {
        LivingArea story = _service.readById(id);
        if (story == null)
            return new ResponseEntity<LivingArea>(HttpStatus.NOT_FOUND);
        _service.delete(story);
        return new ResponseEntity<LivingArea>(HttpStatus.NO_CONTENT);
    }
}
