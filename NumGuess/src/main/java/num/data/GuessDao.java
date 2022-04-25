/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package num.data;

/**
 *
 * @author mikeh
 */
import java.util.List;
import num.models.Guess;
import num.models.UserInput;

public interface GuessDao {

    Guess compare(Guess guess);

    List<Guess> getAll();

    Guess findById(int id);
    
    public String compareboth(UserInput user);

    // true if item exists and is updated
    boolean update(Guess guess);

    // true if item exists and is deleted
    boolean deleteById(int id);
}