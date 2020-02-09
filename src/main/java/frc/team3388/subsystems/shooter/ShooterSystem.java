package frc.team3388.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.SrxEncoder;

public class ShooterSystem extends Subsystem implements Rotatable {
    private static final int CONTROLLER_PORT = 0;

    private final SpeedController speedController;
    private final SrxEncoder encoderSrx;
    private final PidController pidController;

    public ShooterSystem(SpeedController speedController, SrxEncoder encoderSrx, PidController pidController) {
        this.speedController = speedController;
        this.encoderSrx = encoderSrx;
        this.pidController = pidController;
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.1;
        final double kI = 0;
        final double kD = 0;

        PidController pidController = new PidController(kP, kI, kD, 0);
        WPI_TalonSRX speedController = new WPI_TalonSRX(CONTROLLER_PORT);
        SrxEncoder encoderSrx = new SrxEncoder(CONTROLLER_PORT, 1);

        return new ShooterSystem(speedController, encoderSrx, pidController);
    }

    public double rpm() {
        return encoderSrx.getAsDouble();
    }

    public void rotateAt(double rpm) {
        rotate(pidController.calculate(rpm(), rpm));
    }

    public void resetPidController() {
        pidController.reset();
    }

    @Override
    public void rotate(double speed) {
        speedController.set(speed);
    }

    public void stop() {
        speedController.set(0);
    }
}
