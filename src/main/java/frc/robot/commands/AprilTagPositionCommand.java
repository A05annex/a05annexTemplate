package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import org.a05annex.frc.A05Constants;
import org.a05annex.frc.commands.A05AprilTagPositionCommand;
import org.a05annex.frc.subsystems.PhotonCameraWrapper;


public class AprilTagPositionCommand extends A05AprilTagPositionCommand {

    public AprilTagPositionCommand(XboxController xbox, A05Constants.DriverSettings driver, PhotonCameraWrapper camera,
                                   double xPosition, double yPosition, String positionParametersKey) {
        // NOTE: the super adds the drive subsystem requirement
        super(camera, xPosition, yPosition, positionParametersKey);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        // Similar to the drive command, you can either call the super.execute which runs checkIfCanPerformTargeting()
        // and executeTargeting(), or write your own code.

        // super.execute();

        // NOTE: there is a variable called 'canPerformTargeting' that needs to get set to true in order to use
        // executeTargeting(). checkIfCanPerformTargeting will set this to true if the conditions in it are met

        checkIfCanPerformTargeting();

        executeTargeting();
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
