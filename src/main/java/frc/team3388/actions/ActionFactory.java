package frc.team3388.actions;

import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import frc.team3388.subsystem.DriveSystem;

public class ActionFactory {
    public static Action manualDrive(DriveSystem driveSystem, Joystick right, Joystick left) {
        return new TankDriveAction(driveSystem, right.getAxis(JoystickAxis.Y), left.getAxis(JoystickAxis.Y));
    }
}
