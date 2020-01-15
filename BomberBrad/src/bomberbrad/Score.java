/**
 * DKP Studios
 * 2020-01-14
 * Class that represents a high score
 */
package bomberbrad;

public class Score {

    private int amount;
    private String name;

    /**
     * score constructor
     *
     * @param amount amount of points
     * @param name username
     */
    public Score(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    /**
     * Accessor method for amount
     *
     * @return the amount of points
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Mutator method for amount
     *
     * @param amount new amount of points
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Accessor method for name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method for name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

}
