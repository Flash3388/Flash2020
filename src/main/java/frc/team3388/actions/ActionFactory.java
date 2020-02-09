package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.ClimbSystem;

public class ActionFactory {
    public static Action timedHookRaiseAction(ClimbSystem climbSystem) {
        final Time RAISING_TIME = Time.seconds(1.5);
        return raiseHookAction(climbSystem).setTimeout(RAISING_TIME);
    }

    public static Action timedHookLowerAction(ClimbSystem climbSystem) {
        final Time LOWERING_TIME = Time.seconds(1.5);
        return lowerHookAction(climbSystem).setTimeout(LOWERING_TIME);
    }

    public static Action raiseHookAction(ClimbSystem climbSystem) {
        return Actions.sequential(
                Actions.runnableAction(climbSystem::raiseHook),
                Actions.instantAction(climbSystem::stopHook)
        ).requires(climbSystem);
    }

    public static Action lowerHookAction(ClimbSystem climbSystem) {
        return Actions.sequential(
                Actions.runnableAction(climbSystem::lowerHook),
                Actions.instantAction(climbSystem::stopHook)
        ).requires(climbSystem);
    }

    public static Action liftRobotAction(ClimbSystem climbSystem) {
        return Actions.sequential(
                Actions.runnableAction(climbSystem::liftRobot),
                Actions.instantAction(climbSystem::stopRobotLift)
        ).requires(climbSystem);
    }
}
