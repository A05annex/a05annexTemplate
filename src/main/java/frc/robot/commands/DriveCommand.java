package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.a05annex.frc.A05Constants;
import org.a05annex.frc.commands.A05DriveCommand;
import org.a05annex.util.AngleD;
import org.a05annex.util.AngleUnit;

/**
 * Drive command is here because you will likely need to override the serve (targeting, competition specific reason)
 */
public class DriveCommand extends A05DriveCommand {

    /**
     * Default command for DriveSubsystem. Left stick moves the robot field-relatively, and right stick X rotates.
     * Contains driver constants for sensitivity, gain, and deadband.
     * @param xbox (XboxController) The drive xbox controller.
     */
    public DriveCommand(XboxController xbox, A05Constants.DriverSettings driver) {
        super(xbox, driver);
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //TODO: If you want to do special control like targeting, comment out super.execute() and add your own control code
        //TODO: Refer to the documentation. Much of the code you want to run is already packaged in callable methods
        //This runs the default swerve calculations for xbox control
        //super.execute();

        conditionStick();
        m_driveSubsystem.swerveDrive(m_conditionedDirection, m_conditionedSpeed, m_conditionedRotate);

        SmartDashboard.putNumber("Drive Forward", m_conditionedSpeed*(new AngleD(m_conditionedDirection).subtract(m_navx.getHeading()).cos()));
        SmartDashboard.putNumber("Drive Strafe: ", m_conditionedSpeed*(new AngleD(m_conditionedDirection).subtract(m_navx.getHeading()).sin()));
        SmartDashboard.putNumber("Rotation:", m_conditionedRotate);
        SmartDashboard.putNumber("NavX", m_navx.getHeading().getDegrees());
    }
}