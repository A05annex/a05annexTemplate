// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveCommand;
import org.a05annex.frc.A05RobotContainer;
import org.a05annex.frc.commands.A05DriveCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer extends A05RobotContainer
{
    // The robot's subsystems and commands are defined here...
    // NavX, DriveSubsystem, DriveXbox have already been made in A05RobotContainer
    //TODO: Add any additional subsystems and commands here

    //TODO: Uncomment if you have alternate xbox controller, you need to uncomment a constant too
    //XboxController m_altXbox = new XboxController(Constants.ALT_XBOX_PORT);

    // controller button declarations
    JoystickButton m_xboxA = new JoystickButton(m_driveXbox, 1);
    JoystickButton m_xboxB = new JoystickButton(m_driveXbox, 2);
    JoystickButton m_xboxX = new JoystickButton(m_driveXbox, 3);
    JoystickButton m_xboxY = new JoystickButton(m_driveXbox, 4);
    JoystickButton m_xboxLeftBumper = new JoystickButton(m_driveXbox, 5);
    JoystickButton m_xboxRightBumper = new JoystickButton(m_driveXbox, 6);
    JoystickButton m_xboxBack = new JoystickButton(m_driveXbox, 7);
    JoystickButton m_xboxStart = new JoystickButton(m_driveXbox, 8);
    JoystickButton m_xboxLeftStickPress = new JoystickButton(m_driveXbox, 9);
    JoystickButton m_xboxRightStickPress = new JoystickButton(m_driveXbox, 10);

    // alt xbox controller buttons
    //JoystickButton m_altXboxA = new JoystickButton(m_altXbox, 1);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        super();
        // finish swerve drive initialization for this specific robt.
        m_driveSubsystem.setDriveGeometry(m_robotSettings.m_length, m_robotSettings.m_width,
                m_robotSettings.m_rf, m_robotSettings.m_rr,
                m_robotSettings.m_lf, m_robotSettings.m_lr);

        m_driveCommand = new DriveCommand(m_driveXbox, m_driver);

        //TODO: Use these if you want custom drive sensitivity
//        A05DriveCommand.DRIVE_SPEED_GAIN = 0.7D;
//        A05DriveCommand.DRIVE_SPEED_SENSITIVITY = 2.0D;
//        A05DriveCommand.ROTATE_GAIN = 0.5D;
//        A05DriveCommand.ROTATE_SENSITIVITY = 1.5D;

        m_driveSubsystem.setDefaultCommand(m_driveCommand);


        //TODO: add auto

        // Configure the button bindings
        configureButtonBindings();
    }
    
    
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings()
    {
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html

        m_xboxBack.onTrue(new InstantCommand(m_navx::initializeHeadingAndNav)); // Reset the NavX field relativity
    }
}
