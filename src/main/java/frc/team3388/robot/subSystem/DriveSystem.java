package frc.team3388.robot.subSystem;

import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;

public class DriveSystem extends TankDriveSystem {
    public DriveSystem(SpeedController rightController, SpeedController leftController) {
        super(rightController, leftController);
    }
}
