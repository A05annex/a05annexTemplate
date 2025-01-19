// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.a05annex.frc.A05Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.a05annex.frc.subsystems.PhotonCameraWrapper;
import org.a05annex.util.AngleD;
import org.a05annex.util.AngleUnit;
import org.photonvision.PhotonCamera;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants extends A05Constants
{
    public static final class CAN_Devices {
        public static final int
            // Non-Drive Motors
            SAMPLE_MOTOR = 14;
    }

    //TODO: Set which cameras you have
    @SuppressWarnings("unused")
    public static final boolean HAS_USB_CAMERA = false;
    @SuppressWarnings("unused")
	public static final boolean HAS_LIMELIGHT = false;


    //TODO: declare camera name
    public static final PhotonCameraWrapper CAMERA = new PhotonCameraWrapper(new PhotonCamera("IMX219"), 1.0, new AngleD(AngleUnit.DEGREES,0.0));

    //TODO: write a function to adjust the reported X from photonvision to the real x value
    public static double xCorrectionFunction(double reportedX) {
        return reportedX;
    }

    //TODO: write a function to adjust the reported Y from photonvision to the real Y value
    public static double yCorrectionFunction(double reportedY) {
        return reportedY;
    }


    // kP for keeping drive at the same orientation
    public static final double DRIVE_ORIENTATION_kP = 1.2;

    // for practice, length and width from center of the wheels, in m (note chassis is 30" square,
    // the bolt pattern is 29" square, wheels are 2.75" in from the bolt pattern or centered on the
    // corners of a 23.5"(0.5969m) square.
    // For competition, length and width from center of the wheels, in m (note chassis is 28" square,
    // the bolt pattern is 27" square, wheels are 2.75" in from the bolt pattern or centered on the
    // corners of a 21.5"(0.5461m) square.
    //TODO: set calibration constants here for your relevant robot. rf = right front, lr = left rear, etc
    //TODO: Verify dimensions of your robot
    /**
     * The geometry and calibration specific to a swerve drive robot base. We currently have 2 bases, the first being
     * a <i>prototyping/practice</i> base that should always be in working condition for drive tuning/testing,
     * calibration, as well as software prototyping. The second is the <i>competition</i> robot that is built for
     * the yearly competition, and is generally not drivable between the introduction of the competition and a
     * week or two before the first competition because all the competition-specific appendages are being built
     * and assembled to it.
     * The <i>competition</i> robot is the index 0 entry in the list, so it does not require any changes to the
     * Roborio. The <i>practice</i> robot requires a jumper on the digital input port 5 that connects the signal
     * pin to ground. This convention was chosen to minimize the things that could go wrong on the competition robot.
     * These settings are loaded into {@link #ROBOT_SETTINGS_LIST} during {@link Robot#robotInit()}
     */
    public static final RobotSettings[] ROBOT_SETTINGS = {
            new A05Constants.RobotSettings(0, "Competition", 0.5461, 0.5461, 5.0974, 1.2225,
                    0.9817, 4.9839, 1.0, 0.9650),
            new RobotSettings(1, "Practice", 0.5969, 0.5969, 5.240, 5.654,
                    0.969, 5.039, 1.0, 0.9164)
    };

    public static final AutonomousPath[] AUTONOMOUS_PATHS = {
            new AutonomousPath("Sample Path", 0, "samplePath.json")
    };

    public static final DriverSettings[] DRIVER_SETTINGS = {
            new DriverSettings("programmer", 0)
    };

    public static void setAprilTagPositionParametersDictionary() {
        aprilTagSetDictionary.put("left coral station", new AprilTagSet(new int[]{1}, new int[]{13}, new AngleD(AngleUnit.DEGREES, -126)));
        aprilTagSetDictionary.put("right coral station", new AprilTagSet(new int[]{2}, new int[]{12}, new AngleD(AngleUnit.DEGREES, 126)));
        aprilTagSetDictionary.put("close center reef", new AprilTagSet(new int[]{7}, new int[]{18}, new AngleD(AngleUnit.DEGREES, 0)));
        aprilTagSetDictionary.put("far center reef", new AprilTagSet(new int[]{10}, new int[]{21}, new AngleD(AngleUnit.DEGREES, 180)));
        aprilTagSetDictionary.put("close left reef", new AprilTagSet(new int[]{6}, new int[]{19}, new AngleD(AngleUnit.DEGREES, -60)));
        aprilTagSetDictionary.put("far left reef", new AprilTagSet(new int[]{11}, new int[]{20}, new AngleD(AngleUnit.DEGREES, 120)));
        aprilTagSetDictionary.put("close right reef", new AprilTagSet(new int[]{8}, new int[]{17}, new AngleD(AngleUnit.DEGREES, 60)));
        aprilTagSetDictionary.put("far right reef", new AprilTagSet(new int[]{9}, new int[]{22}, new AngleD(AngleUnit.DEGREES, -120)));
        aprilTagSetDictionary.put("left cage", new AprilTagSet(new int[]{5}, new int[]{14}, new AngleD(AngleUnit.DEGREES, 0)));
        aprilTagSetDictionary.put("right cage", new AprilTagSet(new int[]{4}, new int[]{15}, new AngleD(AngleUnit.DEGREES, 0)));
        aprilTagSetDictionary.put("processor", new AprilTagSet(new int[]{3}, new int[]{16}, new AngleD(AngleUnit.DEGREES, 90)));
    }


    // Connect values to SmartDashboard, if you change the value in smart dashboard it changes the const
    // (speed adjusting etc.) By having two methods, you can optionally add the bounds
    /**
     * Initialize value on SmartDashboard for user input, or if already present, return current value.
     *
     * @param key (String) The key to associate with the value.
     * @param initValue (double) The default value to assign if not already on SmartDashboard.
     *
     * @return The new value that appears on the dashboard.
     */
    @SuppressWarnings("unused")
    public static double updateConstant(String key, double initValue) {
        // if key already exists, value will be the current value or whatever we just typed in to the dashboard
        // if key doesn't exist yet, value will be set to initValue and added to SmartDashboard
        double value = SmartDashboard.getNumber(key, initValue);

        // add number if it doesn't exist, or just set it to its current value
        SmartDashboard.putNumber(key, value);
        return value;
    }

    /**
     * Initialize value on SmartDashboard for user input, or if already present, return current value.
     * If value is outside (lowerBound, upperBound), it will be set to the previous value.
     *
     * @param key (String) The key to associate with the value.
     * @param initValue (double) The default value to assign if not already on SmartDashboard.
     * @param lowerBound (double) Lower bound on the value.
     * @param upperBound (double) Upper bound on the value.
     *
     * @return The new value that appears on the dashboard.
     */
    @SuppressWarnings("unused")
    public static double updateConstant(String key, double initValue, double lowerBound, double upperBound) {
        // if key already exists, value will be the current value or whatever we just typed in to the dashboard
        // if key doesn't exist yet, value will be set to initValue and added to SmartDashboard
        double value = SmartDashboard.getNumber(key, initValue);

        // bounds check
        if (value < lowerBound || value > upperBound) {
            value = initValue;
        }

        // add number if it doesn't exist, or just set it to its current value
        SmartDashboard.putNumber(key, value);
        return value;
    }
}
