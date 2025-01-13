# Template Project for 2025 REEFSCAPE

***Please revise the title, and replace the contents below with your competition or
test code documentation when you start a new project by copying this Template. Below is
the documentation of this project, not the robot code you create using this template.***

# Template Project for 2025 REEFSCAPE

* **version:** 2025.1.1 (runs with WPILib 2025.1.1 and associated vendor deps; and a05annexRobot 2025.0.1)
* **status:** first used for FRC **2023 Charged Up**, updated for **2025 REEFSCAPE**
* **comments:** This is the starting point for a swerve drive robot built on the
  [a05annexRobot](https://github.com/A05annex/a05annexRobot) Swerve Drive with NavX and PhotonVision
  robot code base.

This is a project template for a swerve drive robot built on [a05annexRobot](https://github.com/A05annex/a05annexRobot)
library, which provides:
* The Swerve Drive subsystem with a driver-tunable drive command;
* A wrapper for the NavX which is integrated with the Drive subsystem for things like maintaining
  robot heading while translating, simultaneous rotation to face down-field/up-field while driving,
  and calibration for rotational drift of the NavX;
* Autonomous path selection and use of paths generated using the
  [SwervePathPlanner](https://github.com/A05annex/SwervePathPlanning);
* and PhotonVision support - though performance is currently suboptimal.
