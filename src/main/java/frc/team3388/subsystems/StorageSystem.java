package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class StorageSystem extends Subsystem {
    private static final int CONTROLLER_PORT = 1;
    private static final int ENTRY_SENSOR_PORT = 0;
    private static final double SPEED = -0.4;
    private final SpeedController controller;

    public StorageSystem(SpeedController controller) {
        this.controller = controller;
    }

    public static StorageSystem forRobot(BallCounter counter) {
        return new StorageSystem(new WPI_VictorSPX(CONTROLLER_PORT));
    }

    public void move() {
        controller.set(SPEED);
    }

    public void stop() {
        controller.stopMotor();
    }
}
