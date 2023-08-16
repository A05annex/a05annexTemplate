package frc.robot.commands;

import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Timer;
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

//    private final DataLog log = DataLogManager.getLog();

//    private final BooleanLogEntry isRunningLoggingCommand = new BooleanLogEntry(log, "isRunningLoggingCommand");

    private boolean hasValidFrame;

    private boolean isFinished = false;

    public static final String APRIL_TAG_LOG_NAME = "aprilTag";
    private StringLogEntry aprilTagLog = new StringLogEntry(DataLogManager.getLog(),APRIL_TAG_LOG_NAME);

//    private final BooleanLogEntry hasValidFrameLog = new BooleanLogEntry(log, "hasValidFrame");
//    private final DoubleLogEntry actualPositionTimeLog = new DoubleLogEntry(log, "actualPositionTimeLog");
//
//    private final DoubleLogEntry actualXPositionLog = new DoubleLogEntry(log, "actualXPosition");
//    private final DoubleLogEntry actualYPositionLog = new DoubleLogEntry(log, "actualYPosition");
//
//    private final DoubleLogEntry cacheXPositionLog = new DoubleLogEntry(log, "cacheXPosition");
//    private final DoubleLogEntry cacheYPositionLog = new DoubleLogEntry(log, "cacheYPosition");

//    private final DoubleLogEntry swerveTime = new DoubleLogEntry(log, "swerveTime");
//    private final DoubleLogEntry direction = new DoubleLogEntry(log, "direction");
//    private final DoubleLogEntry speed = new DoubleLogEntry(log, "speed");
//    private final DoubleLogEntry rotate = new DoubleLogEntry(log, "rotate");

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
//        hasValidFrameLog.append(hasValidFrame);
//        isRunningLoggingCommand.append(true);
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

        // The cache is doing it's own logging so the logged information is what is actually in the cache.
//        direction.append(conditionedDirection.getRadians());
//        speed.append(conditionedSpeed);
//        rotate.append(conditionedRotate);
//        swerveTime.append(Timer.getFPGATimestamp());

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

            aprilTagLog.append(String.format("TRUE,%.4,%.5f,%.5f,%.5f%.5f",camera.getLatestTargetTime(),
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
