package frc.team3388;

public class BallCounter {
    private final int size;
    private int counter;

    public BallCounter(int size) {
        this.size = size;
        counter = 0;
    }

    public int add() {
        return counter++;
    }

    public int remove() {
        return counter <= 1 ? 0: counter--;
    }

    public boolean isFull() {
        return counter >= size;
    }
}
