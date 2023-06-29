// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.SampleMotorSubsystem;
import org.a05annex.frc.A05RobotContainer;

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


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        super();
        // finish swerve drive initialization for this specific robt.
        driveSubsystem.setDriveGeometry(robotSettings.length, robotSettings.width,
                robotSettings.rf, robotSettings.rr,
                robotSettings.lf, robotSettings.lr,
                robotSettings.maxSpeedCalibration);

        driveCommand = new DriveCommand(driveSubsystem, driveXbox, driver);


        driveSubsystem.setDefaultCommand(driveCommand);


        //TODO: add auto

        // Configure the button bindings
        configureButtonBindings();
    }
    
    
    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * Joystick} or {@link XboxController}), and then passing it to a {@link
     * JoystickButton}.
     */
    private void configureButtonBindings()
    {
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html

        driveBack.onTrue(new InstantCommand(navx::initializeHeadingAndNav)); // Reset the NavX field relativity

        driveA.whileTrue(new InstantCommand(SampleMotorSubsystem.getInstance()::setForward));
        driveB.whileTrue(new InstantCommand(SampleMotorSubsystem.getInstance()::setBackward));
        driveY.whileTrue(new InstantCommand(SampleMotorSubsystem.getInstance()::resetEncoder));
        driveX.whileTrue(new InstantCommand(SampleMotorSubsystem.getInstance()::stop));
    }
}
