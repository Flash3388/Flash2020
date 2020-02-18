package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.team3388.objects.Piston;

public class IntakeSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 0;
    private static final int RIGHT_PISTON_FORWARD_CHANNEL = 1;
    private static final int RIGHT_PISTON_REVERSE_CHANNEL = 0;
    private static final int LEFT_PISTON_FORWARD_CHANNEL = 3;
    private static final int LEFT_PISTON_REVERSE_CHANNEL = 2;
    private static final double SPEED = 0.4;

    private final Piston rightPiston;
    private final Piston leftPiston;

    public IntakeSystem(WPI_VictorSPX controller, Piston rightPiston, Piston leftPiston) {
        super(controller, SPEED);
        this.rightPiston = rightPiston;
        this.leftPiston = leftPiston;
    }

    public static IntakeSystem forRobot() {
        WPI_VictorSPX controller = new WPI_VictorSPX(CONTROLLER_PORT); controller.setInverted(true);
//        Piston rightPiston = new Piston(RIGHT_PISTON_FORWARD_CHANNEL, RIGHT_PISTON_REVERSE_CHANNEL);
//        Piston left_Piston = new Piston(LEFT_PISTON_FORWARD_CHANNEL, LEFT_PISTON_REVERSE_CHANNEL);

        return new IntakeSystem(controller, null, null);
    }

    public void fold() {
        leftPiston.close();
        rightPiston.close();
    }

    public void unfold() {
        leftPiston.open();
        rightPiston.open();
    }
}
