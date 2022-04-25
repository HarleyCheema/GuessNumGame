/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package num.models;

/**
 *
 * @author mikeh
 */
public class Guess {

    private int id;
    private String guess;
    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int[] guessnumber = new int[4];
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public int getNum4() {
        return num4;
    }

    public void setNum4(int num4) {
        this.num4 = num4;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    //3 methods below
    public void setGuessNumber(){
        this.guessnumber[1] = num1;
        this.guessnumber[2] = num2;
        this.guessnumber[3] = num3;
        this.guessnumber[4] = num4;
    }
    public int[] getGuessNumber(){
        return this.guessnumber;
    }
    public String toString(){
        return "Game " + this.id + "\nAnswer = " + this.num1 + "" + this.num2 +
                "" + this.num3 + "" + this.num4 +"\nGame Status Complete? = " +
                this.status;
    }


}
