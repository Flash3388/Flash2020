package frc.team3388.objects;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Piston {
    private final DoubleSolenoid solenoid;

    public Piston(int forwardPort, int reversePort) {
        this.solenoid = new DoubleSolenoid(forwardPort, reversePort);
    }

    public Piston(DoubleSolenoid solenoid) {
        this.solenoid = solenoid;
    }

    public void open() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        if(solenoid.get() == DoubleSolenoid.Value.kForward)
            close();
        else
            open();
    }
}
