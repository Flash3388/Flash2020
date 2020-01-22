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
        driveSystem.tankDrive(-right.getAxis(JoystickAxis.Y).getAsDouble(),-left.getAxis(JoystickAxis.Y).getAsDouble());
    }

    @Override
    protected void end() {
        driveSystem.stop();
    }
}
