package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import org.a05annex.frc.A05Constants;
import org.a05annex.util.Utl;


public class DriveTuneCommand extends Command {

    private XboxController driveXbox = Constants.DRIVE_XBOX;

    private XboxController altXbox = Constants.ALT_XBOX;

    private final JoystickButton driveA = new JoystickButton(driveXbox, 1),
            driveB = new JoystickButton(driveXbox, 2),
            driveX = new JoystickButton(driveXbox, 3),
            driveY = new JoystickButton(driveXbox, 4),
            driveLeftBumper = new JoystickButton(driveXbox, 5),
            driveRightBumper = new JoystickButton(driveXbox, 6),
            driveBack = new JoystickButton(driveXbox, 7),
            driveStart = new JoystickButton(driveXbox, 8),
            driveLeftStickPress = new JoystickButton(driveXbox, 9),
            driveRightStickPress = new JoystickButton(driveXbox, 10);

    // alternate controller button declarations
    private final JoystickButton altA = new JoystickButton(altXbox, 1),
            altB = new JoystickButton(altXbox, 2),
            altX = new JoystickButton(altXbox, 3),
            altY = new JoystickButton(altXbox, 4),
            altLeftBumper = new JoystickButton(altXbox, 5),
            altRightBumper = new JoystickButton(altXbox, 6),
            altBack = new JoystickButton(altXbox, 7),
            altStart = new JoystickButton(altXbox, 8),
            altLeftStickPress = new JoystickButton(altXbox, 9),
            altRightStickPress = new JoystickButton(altXbox, 10);

    private final Constants.SettableDriveSettings driver = Constants.DRIVER_SETTINGS[0];

    private int lockOut = 0;


    double speedGain = driver.getDriveSpeedGain();
    double rotateGain = driver.getRotateGain();
    double speedSensitivity = driver.getDriveSpeedSensitivity();
    double rotateSensitivity = driver.getRotateSensitivity();
    double speedInc = driver.getDriveSpeedMaxInc();
    double rotateInc = driver.getRotateMaxInc();

    public DriveTuneCommand() {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        lockOut = 0;
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        if(!(lockOut > 5)) {
            lockOut++;
            return;
        }

        // Y = speed gain
        // B = rotate gain
        // A = speed sensitivity
        // X = rotate sensitivity
        // Right bumper = speed accel/deccel
        // Left bumper = rotate accel/deccel

        if(altY.getAsBoolean()) {

            if(altXbox.getPOV() == 0) {
                speedGain += 0.05;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                speedGain -= 0.05;
                lockOut = 0;
            }

            speedGain = Utl.clip(speedGain, 0.05, 1.0);
            driver.setDriveSpeedGain(speedGain);
        }

        if(altB.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                rotateGain += 0.05;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                rotateGain -= 0.05;
                lockOut = 0;
            }

            rotateGain = Utl.clip(rotateGain, 0.05, 1.0);
            driver.setRotateGain(rotateGain);
        }

        if(altA.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                speedSensitivity += 0.05;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                speedSensitivity -= 0.05;
                lockOut = 0;
            }

            speedSensitivity = Utl.clip(speedSensitivity, 0.05, 4.0);
            driver.setDriveSpeedSensitivity(speedSensitivity);
        }

        if(altX.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                rotateSensitivity += 0.05;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                rotateSensitivity -= 0.05;
                lockOut = 0;
            }

            rotateSensitivity = Utl.clip(rotateSensitivity, 0.05, 4.0);
            driver.setRotateSensitivity(rotateSensitivity);
        }

        if(altRightBumper.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                speedInc += 0.005;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                speedInc -= 0.005;
                lockOut = 0;
            }

            speedInc = Utl.clip(speedInc, 0.005, 1.0);
            driver.setDriveSpeedMaxInc(speedInc);
        }

        if(altLeftBumper.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                rotateInc += 0.005;
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                rotateInc -= 0.005;
                lockOut = 0;
            }

            rotateInc = Utl.clip(rotateInc, 0.005, 1.0);
            driver.setRotateMaxInc(rotateInc);
        }

        SmartDashboard.putNumber("speed gain", driver.getDriveSpeedGain());
        SmartDashboard.putNumber("rotate gain", driver.getRotateGain());
        SmartDashboard.putNumber("speed sensitivity", driver.getDriveSpeedSensitivity());
        SmartDashboard.putNumber("rotate sensitivity", driver.getRotateSensitivity());
        SmartDashboard.putNumber("speed inc", driver.getDriveSpeedMaxInc());
        SmartDashboard.putNumber("rotate inc", driver.getRotateMaxInc());
    }
}
