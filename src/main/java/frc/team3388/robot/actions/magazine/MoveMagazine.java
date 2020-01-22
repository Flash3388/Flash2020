package frc.team3388.robot.actions.magazine;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.MagazineSystem;

public class MoveMagazine extends Action {
    private MagazineSystem mMagazineSystem;

    public MoveMagazine(MagazineSystem magazineSystem)
    {
        mMagazineSystem = magazineSystem;
        requires(mMagazineSystem);
    }

    @Override
    protected void execute() {
        mMagazineSystem.run();
    }

    @Override
    protected void end() {
        mMagazineSystem.stop();
    }
}
