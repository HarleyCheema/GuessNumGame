/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package num.controllers;

/**
 *
 * @author mikeh
 */
import java.util.List;
import num.models.Guess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import num.data.GuessDao;
import num.models.UserInput;

@RestController
@RequestMapping("/api/guess")
public class GuessController {

    private final GuessDao dao;

    public GuessController(GuessDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Guess> all() {
        return dao.getAll();
    }
    // We need to be able to change this to where user puts in a number
    // and our DB DAO checks to see if the guess number is = to the game number
    // if it matches, changes the match completed to true
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody UserInput userguess) {
        return ResponseEntity.ok((dao.compareboth(userguess)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable int id) {
        Guess result = dao.findById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Guess guess) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != guess.getId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (dao.update(guess)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (dao.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
