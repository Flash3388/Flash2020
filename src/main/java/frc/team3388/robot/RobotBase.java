package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.DelegatingRobotBase;
import com.flash3388.flashlib.frc.robot.RobotConfiguration;
import edu.wpi.first.wpilibj.DriverStation;

public class RobotBase extends DelegatingRobotBase {

    public RobotBase() {
        super(IterativeRobot::new,
                DriverStation.getInstance().isFMSAttached() ?
                        RobotConfiguration.competitionConfiguration() :
                        RobotConfiguration.debugConfiguration());
    }
}
