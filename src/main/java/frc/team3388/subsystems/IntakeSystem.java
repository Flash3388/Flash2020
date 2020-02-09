package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.team3388.Piston;

public class IntakeSystem extends Subsystem {
    private static final int CONTROLLER_PORT = 4;
    private static final int RIGHT_PISTON_FORWARD_CHANNEL = 1;
    private static final int RIGHT_PISTON_REVERSE_CHANNEL = 0;
    private static final int LEFT_PISTON_FORWARD_CHANNEL = 3;
    private static final int LEFT_PISTON_REVERSE_CHANNEL = 2;
    private static final double SPEED =0.4;

    private final PWMVictorSPX controller;
    private final Piston rightPiston;
    private final Piston leftPiston;

    public IntakeSystem(PWMVictorSPX controller, Piston rightPiston, Piston leftPiston) {
        this.controller = controller;
        this.rightPiston = rightPiston;
        this.leftPiston = leftPiston;
    }

    public static IntakeSystem forRobot() {
        PWMVictorSPX controller = new PWMVictorSPX(CONTROLLER_PORT);
        Piston rightPiston = new Piston(RIGHT_PISTON_FORWARD_CHANNEL, RIGHT_PISTON_REVERSE_CHANNEL);
        Piston left_Piston = new Piston(LEFT_PISTON_FORWARD_CHANNEL, LEFT_PISTON_REVERSE_CHANNEL);

        return new IntakeSystem(controller, rightPiston, left_Piston);
    }

    public void intake() {
        controller.set(SPEED);
    }

    public void stop() {
        controller.stopMotor();
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
