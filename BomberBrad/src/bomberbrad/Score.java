/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberbrad;

/**
 *
 * @author ridec8459
 */
public class Score {
    private int amount;
    private String name;
    public Score(int amount, String name) {
        this.amount = amount;
        this.name = name;
    } 

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
