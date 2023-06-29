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

    private final DataLog log = DataLogManager.getLog();

    private final BooleanLogEntry isRunningLoggingCommand = new BooleanLogEntry(log, "isRunningLoggingCommand");

    private boolean hasValidFrame;
    private final BooleanLogEntry hasValidFrameLog = new BooleanLogEntry(log, "hasValidFrame");

    private final DoubleLogEntry actualXPositionLog = new DoubleLogEntry(log, "actualXPosition");
    private final DoubleLogEntry actualYPositionLog = new DoubleLogEntry(log, "actualYPosition");

    private final DoubleLogEntry cacheXPositionLog = new DoubleLogEntry(log, "cacheXPosition");
    private final DoubleLogEntry cacheYPositionLog = new DoubleLogEntry(log, "cacheYPosition");

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
        hasValidFrameLog.append(hasValidFrame);
        isRunningLoggingCommand.append(true);
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
        // We use an if statement and hasValidFrame to avoid unnecessary/extra logging
        if(camera.doesLatestFrameAndTargetMatch() != hasValidFrame) {
            hasValidFrame = camera.doesLatestFrameAndTargetMatch();
            hasValidFrameLog.append(hasValidFrame);
        }

        if(!camera.doesLatestFrameAndTargetMatch()) {
            // wait until there is
            return;
        }

        // Is a base frame set yet?
        if(baseTarget == null) {
            // Set the base frame and go from there
            baseTarget = camera.getLatestTarget();
            baseTargetTime = camera.getLatestTargetTime();
        }

        actualXPositionLog.append(camera.getXFromLastTarget());
        actualYPositionLog.append(camera.getYFromLastTarget());
        cacheXPositionLog.append(getXFromBaseTarget() - speedCachedSwerve.getRobotRelativePositionSince(baseTargetTime).forward);
        cacheYPositionLog.append(getYFromBaseTarget() - speedCachedSwerve.getRobotRelativePositionSince(baseTargetTime).strafe);
    }

    @Override
    public boolean isFinished() {
        return speedCachedSwerve.getRobotRelativePositionSince(baseTargetTime).cacheOverrun;
    }

    @Override
    public void end(boolean interrupted) {
        isRunningLoggingCommand.append(false);
    }

    private double getXFromBaseTarget() {
        return baseTarget.getBestCameraToTarget().getX();
    }

    private double getYFromBaseTarget() {
        return -baseTarget.getBestCameraToTarget().getY();
    }
}
