package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.team3388.objects.Piston;

public class IntakeSystem extends ConstantSpeedRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 0;
    private static final int PISTONS_FORWARD_CHANNEL = 4;
    private static final int PISTONS_REVERSE_CHANNEL = 5;
    private static final double SPEED = 0.4;

    private final Piston bothPistons;

    public IntakeSystem(WPI_VictorSPX controller, Piston bothPistons) {
        super(controller, SPEED);
        this.bothPistons = bothPistons;
    }

    public static IntakeSystem forRobot() {
        WPI_VictorSPX controller = new WPI_VictorSPX(CONTROLLER_PORT); controller.setInverted(true);
        Piston bothPistons = new Piston(PISTONS_FORWARD_CHANNEL, PISTONS_REVERSE_CHANNEL);

        return new IntakeSystem(controller, bothPistons);
    }

    public void fold() {
        bothPistons.close();
    }

    public void unfold() {
        bothPistons.open();
    }
}
