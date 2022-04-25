/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package num.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import num.models.Guess;
import num.models.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
@Profile("database")
public class GuessDatabaseDao implements GuessDao {

    private final JdbcTemplate jdbcTemplate;
    private final Map<String, Guess> guesslist = new HashMap<>();

    @Autowired
    public GuessDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //this add() method needs to be changed completely into where 
    //it changes the guessid status to true if the guess matches the array
    @Override
    public Guess compare(Guess guess) {

        final String sql = "INSERT INTO guess(guess, num1, num2, num3, num4, status) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, guess.getId());
            statement.setInt(2, guess.getNum1());
            statement.setInt(3, guess.getNum2());
            statement.setInt(4, guess.getNum3());
            statement.setInt(5, guess.getNum4());

            return statement;

        }, keyHolder);
        guess.setId(keyHolder.getKey().intValue());

        return guess;
    }

    //real method
    @Override
    public String compareboth(UserInput user) {
        int gameid = user.getGameid();
        int usernumber = user.getNumber();

        // turning user number into an array
        String temp = Integer.toString(usernumber);
        int[] userArray = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            userArray[i] = temp.charAt(i) - '0';
        }

        //getting the gameid number
        final String sql1 = "SELECT id,  num1, num2, num3, num4, status "
                + "FROM Number WHERE id = ?;";
        Guess guessnum = jdbcTemplate.queryForObject(sql1, new GuessMapper(), gameid);
        //setting the number array
        int[] guessnumArray = {guessnum.getNum1(), guessnum.getNum2(), guessnum.getNum3(), guessnum.getNum4()};

        //gameloop boolean and numbers correct
        boolean check = false;
        int counter = 0;
        //compare both arrays
        int guessnumber[] = guessnum.getGuessNumber();
        for (int b = 0; b < userArray.length; b++) {
            System.out.println(b);
            if (guessnumArray[b] == userArray[b]) {
                counter++;
            }
        }

        if (counter == 4) {
            check = true;
        }
        if (check == true) {

            final String sql = "UPDATE Number SET "
                    + "status = true "
                    + "WHERE id = ?;";
            jdbcTemplate.update(sql, gameid);

        }

        String comparison = "";

        for (int i = 0; i < userArray.length; i++) {
            if (guessnumArray[i] == userArray[i]) {
                comparison += "MATCH ";
            } else if ((Arrays.toString(guessnumArray)).contains(Integer.toString(userArray[i]))) {
                comparison += "partial ";
            } else {
                comparison += "no_match ";
            }
        }

        return comparison;

    }

    @Override
    public List<Guess> getAll() {
        final String sql = "select id, if(status = false, '0', num1) num1, if(status = false, '0', num2) num2, if(status = false, '0', num3) num3,"
                + "if(status = false, '0', num4) num4, status FROM number;";

        return jdbcTemplate.query(sql, new GuessMapper());
    }

    @Override
    public Guess findById(int id) {

        final String sql = "select id, if(status = false, '0', num1) num1, if(status = false, '0', num2) num2, if(status = false, '0', num3) num3,"
                + "if(status = false, '0', num4) num4, status FROM number WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new GuessMapper(), id);
    }

    @Override
    public boolean update(Guess guess) {

        final String sql = "UPDATE Number SET "
                + "num1 = ?, "
                + "num2 = ?, "
                + "num3 = ? "
                + "num4 = ? "
                + "status = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                guess.getId(),
                guess.getNum1(),
                guess.getNum2(),
                guess.getNum3(),
                guess.getNum4(),
                guess.isStatus(),
                guess.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM Number WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class GuessMapper implements RowMapper<Guess> {

        @Override
        public Guess mapRow(ResultSet rs, int index) throws SQLException {
            Guess td = new Guess();
            td.setId(rs.getInt("id"));
            td.setNum1(rs.getInt("num1"));
            td.setNum2(rs.getInt("num2"));
            td.setNum3(rs.getInt("num3"));
            td.setNum4(rs.getInt("num4"));
            //td.setGuessNumber();
            td.setStatus(rs.getBoolean("status"));

            return td;
        }
    }
}
