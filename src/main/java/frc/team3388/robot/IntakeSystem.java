package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class IntakeSystem extends Subsystem {
    private static final double SPEED =0.324324324;
    private final WPI_TalonSRX controller;
    private  final DoubleSolenoid piston;


    public IntakeSystem(WPI_TalonSRX controller, DoubleSolenoid piston) {
        this.controller = controller;
        this.piston = piston;
    }
    public void in() {
        controller.set(SPEED);
    }
    public void stop(){
        controller.stopMotor();
    }
    public void up(){
        piston.set(DoubleSolenoid.Value.kForward);
    }
    public void down(){
        piston.set(DoubleSolenoid.Value.kReverse);
    }
}
