package frc.robot.commands;

import org.a05annex.frc.commands.A05TagTargetCommand;

@SuppressWarnings("unused")
public class TagTargetCommand extends A05TagTargetCommand {

    public TagTargetCommand(double xPosition, double yPosition, String positionParametersKey) {
        // NOTE: the super adds the drive subsystem requirement
        super(xPosition, yPosition, positionParametersKey);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        // Similar to the drive command, you can either call the super.execute which runs checkIfCanPerformTargeting()
        // and executeTargeting(), or write your own code.

        //super.execute();

        // NOTE: there is a boolean variable called 'canTarget' that needs to get set to true in order to use
        // executeTargeting(). verifyOkToTarget() will set this to true if conditions in it are met.

        updateIRP();

        verifyOkToTarget();

        calculateThenDrive();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
