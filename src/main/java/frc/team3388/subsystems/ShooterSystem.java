package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.SrxEncoder;

public class ShooterSystem extends PreciseRotatableSubsystem {
    private static final int FIRST_CONTROLLER_PORT = 0;
    private static final int SECOND_CONTROLLER_PORT = 0;

    public ShooterSystem(SpeedController firstSpeedController, SpeedController secondSpeedController, SrxEncoder encoderSrx, PidController pidController) {
        super(new SpeedControllerGroup(new FrcSpeedController(firstSpeedController), new FrcSpeedController(secondSpeedController)), encoderSrx, pidController);
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.1;
        final double kI = 0;
        final double kD = 0;

        PidController pidController = new PidController(kP, kI, kD, 0);
        WPI_TalonSRX firstSpeedController = new WPI_TalonSRX(FIRST_CONTROLLER_PORT);
        WPI_TalonSRX secondSpeedController = new WPI_TalonSRX(SECOND_CONTROLLER_PORT);
        SrxEncoder encoderSrx = new SrxEncoder(firstSpeedController, 1);
        secondSpeedController.follow(firstSpeedController);

        return new ShooterSystem(firstSpeedController, secondSpeedController, encoderSrx, pidController);
    }
}
