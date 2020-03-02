package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.DelegatingRobotBase;
import com.flash3388.flashlib.frc.robot.RobotConfiguration;
import com.flash3388.flashlib.frc.robot.logging.LogConfiguration;
import edu.wpi.first.wpilibj.DriverStation;

public class RobotBase extends DelegatingRobotBase {
    public RobotBase() {
        super(IterativeRobot::new, new RobotConfiguration(new LogConfiguration(false, false)));
    }
}
