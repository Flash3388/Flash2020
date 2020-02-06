package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class ShooterFeederSystem extends Subsystem {
    private static final int CONTROLLER_PORT = 2;
    private static final double SPEED = 0.5;
    private final SpeedController controller;

    public ShooterFeederSystem(SpeedController controller) {
        this.controller = controller;
    }

    public static ShooterFeederSystem forRobot() {
        return new ShooterFeederSystem(new WPI_VictorSPX(CONTROLLER_PORT));
    }

    public void feed() {
        controller.set(SPEED);
    }

    public void stop() {
        controller.stopMotor();
    }
}
