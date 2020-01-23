package frc.team3388.robot.actions.magazine;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import frc.team3388.robot.subsystems.MagazineSystem;

public class OpenPistonAction extends InstantAction {
    private MagazineSystem mMagazineSystem;

    public OpenPistonAction(MagazineSystem magazineSystem)
    {
        mMagazineSystem = magazineSystem;
        requires(mMagazineSystem);
    }

    @Override
    protected void execute() {
        mMagazineSystem.openPiston();
    }
}
