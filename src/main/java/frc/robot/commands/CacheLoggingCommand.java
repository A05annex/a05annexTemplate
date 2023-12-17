package frc.robot.commands;

import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

import org.a05annex.frc.A05Constants;
import org.a05annex.frc.commands.A05DriveCommand;
import org.a05annex.frc.subsystems.PhotonCameraWrapper;
import org.a05annex.frc.subsystems.SpeedCachedSwerve;
import org.a05annex.util.AngleD;
import org.photonvision.targeting.PhotonTrackedTarget;


public class CacheLoggingCommand extends A05DriveCommand {

    private final PhotonCameraWrapper camera = Constants.CAMERA;
    private final SpeedCachedSwerve speedCachedSwerve = SpeedCachedSwerve.getInstance();

    private PhotonTrackedTarget baseTarget = null;
    private double baseTargetTime;

    private boolean hasValidFrame;

    private boolean isFinished = false;

    public static final String APRIL_TAG_LOG_NAME = "aprilTag";
    private final StringLogEntry aprilTagLog = new StringLogEntry(DataLogManager.getLog(),APRIL_TAG_LOG_NAME);

    public CacheLoggingCommand(XboxController driveXbox, A05Constants.DriverSettings driver) {
        super(SpeedCachedSwerve.getInstance(), driveXbox, driver);
        addRequirements(this.speedCachedSwerve.getDriveSubsystem());
    }

    @Override
    public void initialize() {
        camera.updateLatestFrameAndTarget();
        if(camera.doesLatestFrameAndTargetMatch()) {
            baseTarget = camera.getLatestTarget();
            baseTargetTime = camera.getLatestTargetTime();
        }

        hasValidFrame = camera.doesLatestFrameAndTargetMatch();
    }

    @Override
    public void execute() {
        conditionStick();

        AngleD fieldHeading = navX.getHeadingInfo().getClosestDownField();
        navX.setExpectedHeading(fieldHeading);
        conditionedRotate = new AngleD(navX.getHeadingInfo().expectedHeading).subtract(new AngleD(navX.getHeadingInfo().heading))
                .getRadians() * 0.9; // 0.9 is a kp value

        lastConditionedRotate = conditionedRotate;

        speedCachedSwerve.swerveDrive(conditionedDirection, conditionedSpeed, conditionedRotate);

        camera.updateLatestFrameAndTarget();

        // Log weather or not there was a valid frame
        hasValidFrame = camera.doesLatestFrameAndTargetMatch();
        if (!hasValidFrame) {
            // wait until there is a new target before we log again
            aprilTagLog.append("FALSE" );
        } else {
            // Is a base frame set yet?
            if (baseTarget == null) {
                // Set the base frame and go from there
                baseTarget = camera.getLatestTarget();
                baseTargetTime = camera.getLatestTargetTime();
            }
            SpeedCachedSwerve.RobotRelativePosition position =
                    speedCachedSwerve.getRobotRelativePositionSince(baseTargetTime);
            if (position.cacheOverrun) {
                isFinished = true;
                return;
            }

            aprilTagLog.append(String.format("TRUE,%.4f,%.5f,%.5f,%.5f,%.5f",camera.getLatestTargetTime(),
                    camera.getXFromLastTarget(), camera.getYFromLastTarget(),
                    getXFromBaseTarget() - position.forward, getYFromBaseTarget() - position.strafe));
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        aprilTagLog.append("");
    }

    private double getXFromBaseTarget() {
        return baseTarget.getBestCameraToTarget().getX();
    }

    private double getYFromBaseTarget() {
        return -baseTarget.getBestCameraToTarget().getY();
    }
}
