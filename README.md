# 2024 Crescendo: Driver Profile Tuner

This is a project we use every year that is generated from the base template and lets us create custom driver profiles

* **version:** 2024.2.2 (runs with WPIlib 2024.2.1 and associated vendor deps; and a05annexRobot 2024.2.2)
* **status:** first used for FRC **2023 Charged Up**, updated for **2024 Crescendo**
* **comments:** We first started building custom driver profiles for the 2021 Infinite Recharge
    at Home competition (The COVID-19 competition year). The competition included 3 obstacle
    courses that were both manually driven and autonomously driven. We started to use driver profiles
    in driver training so drivers could start with a controllable robot that had limited speed, acceleration
    spin rate and spin acceleration - and then step those up in a personalized way as they became more 
    proficient driving the robot.

## Driver Profiles - What are They? How do They Work?

An FRC robot is a large and massive machine that can go quite fast and accelerate and decelerate
at very high rates - so it is an intrinsically uncontrollable machine that a driver needs to learn to control. Driver
profiles map the driver's movements of the Xbox control sticks to the response of the robot to those actions. More
specifically, they create a path from a beginning, occasional, or demonstration driver (the robot is highly limited in
both speed and acceleration) to the competition driver, who is encouraged to *'put the pedal to the metal'*.

A *driver profile* is a file of mapping parameters that map the Xbox control stick to the behavior of the robot. These
files are in the `src/main/deploy/drivers` directory of the project. Anything in the `src/main/deploy` directory is
loaded onto the Roborio when robot code is deployed, so they can be loaded when the robot initializes. We use
physical switches on the robot to set which driver profile
will be read at robot code start, and if the switches have not been installed we default to the *programmer* driver
profile, which, because programmers are notoriously bad drivers, limits speed and acceleration so most young
children could non-destructively control the robot.

How they work is that we dedicate (by default) 2 switches to driver selection, which gives use 4 driver profiles (you
could easily change this if you need more). We use profile 0 as the default benign profile, and the next 3 as driver
profiles (we have never had more than 2 competition drivers). And there are the actual mapping functions controlled
by the parameters in the profile file - which are discussed later.

### How Do I Build and Tune a Driver Profile?

The short answer is:
1. create an entry in the driver list;
2. create a corresponding profile file (just copy the programmer file as a start);
3. load this project on the robot;
4. set the switches to load the correct driver profile on robot start;
5. start, enable, and drive the robot; instruct the operator how you want you drive parameters
   adjusted while you are driving
6. save your profile for future use.

NOTE: once you have a profile, you can tune it in the future. During the 2021 year it was common that for a driving
session a driver would load their profile, dial back the max speed and acceleration for a few practice runs, and
then work on moving everything as far forward as possible while still completing the tasks.

OK, lets cover those steps in detail.

### How Does the Mapping from Stick Position to Swerve Commands Really Work?



