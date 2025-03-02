package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.a05annex.frc.subsystems.SparkNeo;
import org.a05annex.frc.subsystems.SparkNeo550;

public class SampleMotorSubsystem extends SubsystemBase {

    private final SparkNeo550 motor = SparkNeo550.factory(Constants.CAN_Devices.SAMPLE_MOTOR);

    // Declare PID constants for smart motion control
    @SuppressWarnings("FieldCanBeLocal")
	private final double smKp = 0.00005, smKi = 0.000, smKiZone = 0.0, smKff = 0.000156, smMaxRPM = 3000.0,
            smMaxDeltaRPMSec = 3000.0, smError = 0.1;

    // Declare PID constants for position control
    @SuppressWarnings("FieldCanBeLocal")
    private final double posKp = 0.22, posKi = 0.0, posKiZone = 0.0, posKff = 0.0;

    // Declare PID constants for speed (rpm) control
    @SuppressWarnings("FieldCanBeLocal")
    private final double rpmKp = 0.5, rpmKi = 0.0, rpmKiZone = 0.0, rpmKff = 0.0;

    // Declare min and max soft limits and where the motor thinks it starts
    @SuppressWarnings("FieldCanBeLocal")
    private final Double minPosition = null, maxPosition = 1000.0, startPosition = 500.0;

    private final static SampleMotorSubsystem INSTANCE = new SampleMotorSubsystem();
    public static SampleMotorSubsystem getInstance() {
        return INSTANCE;
    }

    private SampleMotorSubsystem() {
        motor.startConfig();
        motor.setCurrentLimit(SparkNeo.UseType.RPM_OCCASIONAL_STALL, SparkNeo.BreakerAmps.Amps40);
		//noinspection ConstantValue
		motor.setSoftLimits(minPosition, maxPosition);
        motor.setDirection(SparkNeo.Direction.REVERSE);
        //motor.setIdleMode(SparkBaseConfig.IdleMode.kBrake);
        motor.setPositionPID(posKp, posKi, posKiZone, posKff);
        motor.setMAXMotionPosition(smKp, smKi, smKiZone, smKff, smMaxRPM, smMaxDeltaRPMSec, smError);
        motor.setRpmPID(rpmKp, rpmKi, rpmKiZone, rpmKff);
        motor.endConfig();
        motor.setEncoderPosition(startPosition);
    }

    @SuppressWarnings("unused")
    public void goToSmartMotionPosition(double position) {
        motor.setTargetMAXMotionPosition(position);
    }

    @SuppressWarnings("unused")
	public void goToPosition(double position) {
        motor.setTargetPosition(position);
    }

    @SuppressWarnings("unused")
    public void setVelocity(double rpm) {
        motor.setTargetRPM(rpm);
    }

    @SuppressWarnings("unused")
    public void resetEncoder() {
        motor.setEncoderPosition(0.0);
    }

    @SuppressWarnings("unused")
    public void stop() {
        motor.stopMotor();
    }

    public double getPosition() {
        return motor.getEncoderPosition();
    }
}

