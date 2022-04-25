/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package num.models;

/**
 *
 * @author Dejan Savic
 */
public class UserInput {
    private int gameid;
    private int number;
    
    public void setGameid(int id){
        this.gameid = id;
    }
    public void setNumber(int num){
        this.number = num;
    }
    
    public int getGameid(){
        return this.gameid;
    }
    public int getNumber(){
        return this.number;
    }
}
