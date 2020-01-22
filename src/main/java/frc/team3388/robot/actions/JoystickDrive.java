package frc.team3388.robot.actions;

import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystem.DriveSystem;

public class JoystickDrive extends Action {
    private final Joystick right;
    private final Joystick left;
    private final DriveSystem driveSystem;
    public JoystickDrive(DriveSystem driveSystem,Joystick right, Joystick left) {
        this.right = right;
        this.driveSystem = driveSystem;
        this.left = left;
        requires(driveSystem);
    }

    @Override
    protected void execute() {
        double rightAxis=-right.getAxis(JoystickAxis.Y).getAsDouble();
        double leftAxis=-left.getAxis(JoystickAxis.Y).getAsDouble();
        if (Math.abs(rightAxis)<0.2)
            rightAxis=0;
        if (Math.abs(leftAxis)<0.2)
            leftAxis=0;
        driveSystem.tankDrive(rightAxis,leftAxis);
    }

    @Override
    protected void end() {
        driveSystem.stop();
    }
}
