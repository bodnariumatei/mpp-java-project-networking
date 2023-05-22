package sm.services.rest;

import sm.model.Competition;
import sm.persistance.RepositoryException;
import sm.persistance.repository.CompetitionDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/sm/competitions")
public class CompetitionController {
    private static final String template = "Hello, %s!";

    @Autowired
    private CompetitionDbRepository compeRepo;

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }


    @RequestMapping( method=RequestMethod.GET)
    public Iterable<Competition> getAll(){
        System.out.println("Get all competitions ...");
        return compeRepo.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id){
        System.out.println("Get by id "+id);
        Competition competition=compeRepo.getOne(id);
        if (competition==null)
            return new ResponseEntity<>("competition not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(competition, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Competition create(@RequestBody Competition competition){
        System.out.println(competition.toString());
        compeRepo.store(competition);
        return competition;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Competition update(@RequestBody Competition competition) {
        System.out.println("Updating competition ...");
        compeRepo.update(competition);
        return competition;

    }
    // @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable int id){
        System.out.println("Deleting competition ... " + id);
        try {
            compeRepo.delete(id);
            return new ResponseEntity<Competition>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete competition exception");
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String competitionError(RepositoryException e) {
        return e.getMessage();
    }
}
