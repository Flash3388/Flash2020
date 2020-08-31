package frc.team3388.subsystems;

import com.beans.BooleanProperty;
import com.beans.properties.SimpleBooleanProperty;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;

public interface Testable {
    default Action tests(BooleanProperty testResult) {
        return Actions.empty();
    }

    default Action tests() {
        return tests(new SimpleBooleanProperty());
    }
}
