package frc.team3388;

public class BallCounter {
    private final int size;
    private int counter;

    public BallCounter(int size) {
        this.size = size;
        counter = 0;
    }

    public void add() {
        counter++;
    }

    public void remove() {
        counter = counter <= 1 ? 0: counter--;
    }

    public boolean isFull() {
        return counter >= size;
    }

    public boolean isEmpty() {
        return counter == 0;
    }
}
