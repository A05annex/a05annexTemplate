package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import org.a05annex.frc.A05Constants;


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

        if(altY.getAsBoolean()) {

            if(altXbox.getPOV() == 0) {
                driver.setDriveSpeedGain(driver.getDriveSpeedGain() + 0.05);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setDriveSpeedGain(driver.getDriveSpeedGain() - 0.05);
                lockOut = 0;
            }
        }

        if(altB.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                driver.setRotateGain(driver.getRotateGain() + 0.05);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setRotateGain(driver.getRotateGain() - 0.05);
                lockOut = 0;
            }
        }

        if(altA.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                driver.setDriveSpeedSensitivity(driver.getDriveSpeedSensitivity() + 0.05);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setDriveSpeedSensitivity(driver.getDriveSpeedSensitivity() - 0.05);
                lockOut = 0;
            }
        }

        if(altX.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                driver.setRotateSensitivity(driver.getRotateSensitivity() + 0.05);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setRotateSensitivity(driver.getRotateSensitivity() - 0.05);
                lockOut = 0;
            }
        }

        if(altLeftBumper.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                driver.setRotateMaxInc(driver.getRotateMaxInc() + 0.01);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setRotateMaxInc(driver.getRotateMaxInc() - 0.01);
                lockOut = 0;
            }
        }

        if(altRightBumper.getAsBoolean()) {
            if(altXbox.getPOV() == 0) {
                driver.setDriveSpeedMaxInc(driver.getDriveSpeedMaxInc() + 0.01);
                lockOut = 0;
            } else if(altXbox.getPOV() == 180) {
                driver.setDriveSpeedMaxInc(driver.getDriveSpeedMaxInc() - 0.01);
                lockOut = 0;
            }
        }
    }
}
