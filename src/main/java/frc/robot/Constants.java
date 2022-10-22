// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import org.a05annex.frc.A05Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        /*
        public static final int
                // Non-Drive Motors
                MOTOR1 = port number,
                MOTOR2 = port number;
         */
    }


    //TODO: Set which cameras you have
    public static final boolean HAS_USB_CAMERA = false;
    public static final boolean HAS_LIMELIGHT = false;


    /*
        DRIVE_XBOX_PORT = 0 (Set in A05Constants)
        Port 0 is whichever controller was plugged in first, not a specific port.
    */
    //TODO: Comment in if you want 2nd controller, you also need to uncomment something in RobotContainer
    //public static final int ALT_XBOX_PORT = 1; // 2nd Controller for more controls

    public static final XboxController.Axis INCREASE_GAIN_AXIS = XboxController.Axis.kRightTrigger;
    public static final XboxController.Axis DECREASE_GAIN_AXIS = XboxController.Axis.kLeftTrigger;

    // for prototype, length and width from center of the wheels, in m (note chassis is 30" square,
    // the bolt pattern is 29" square, wheels are 2.75" in from the bolt pattern or centered on the
    // corners of a 23.5"(0.5969m) square.
    // For competition, length and width from center of the wheels, in m (note chassis is 28" square,
    // the bolt pattern is 27" square, wheels are 2.75" in from the bolt pattern or centered on the
    // corners of a 21.5"(0.5461m) square.

    //TODO: Verify dimensions of your robot
    public static final double DRIVE_LENGTH = 0.5969;
    public static final double DRIVE_WIDTH = 0.5969;

    // kP for keeping drive at the same orientation
    public static double DRIVE_ORIENTATION_kP = 0.3;

    //TODO: Calibrate and comment date of calibration
    public static final class CalibrationOffset {
        public static final double
                // RF = Right Front, LR = Left Rear, etc
                RF = 0.785,
                RR = 0.357,
                LR = 2.519,
                LF = 0.563;
    }

    public static final A05Constants.AutonomousPath[] AUTONOMOUS_PATHS = {
            new A05Constants.AutonomousPath("Sample Path", 0, "samplePath.json")
    };

    // Connect values to SmartDashboard, if you change the value in smart dashboard it changes the const (speed adjusting etc)
    // By having two methods, you can optionally add the bounds
    /**
     * Initialize value on SmartDashboard for user input, or if already present, return current value.
     *
     * @param key (String) The key to associate with the value.
     * @param initValue (double) The default value to assign if not already on SmartDashboard.
     *
     * @return The new value that appears on the dashboard.
     */
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
