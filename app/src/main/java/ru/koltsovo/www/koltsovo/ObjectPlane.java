package ru.koltsovo.www.koltsovo;

public class ObjectPlane {

    public String planeFlight;
    public String planeDirection;
    public String planeType;
    public String planeTimePlan;
    public String planeTimeFact;
    public String planeStatus;
    public String planeBaggageStatus;
    public String planeGate;
    public String planeCheckIn;
    public String planeCombination;
    public boolean planeTracking;

    public ObjectPlane(String planeFlight, String planeDirection, String planeType, String planeTimePlan, String planeTimeFact, String planeStatus, boolean planeTracking, String planeBaggageStatus, String planeGate, String planeCheckIn, String planeCombination) {
        this.planeFlight = planeFlight;
        this.planeDirection = planeDirection;
        this.planeType = planeType;
        this.planeTimePlan = planeTimePlan;
        this.planeTimeFact = planeTimeFact;
        this.planeStatus = planeStatus;
        this.planeTracking = planeTracking;
        this.planeBaggageStatus = planeBaggageStatus;
        this.planeGate = planeGate;
        this.planeCheckIn = planeCheckIn;
        this.planeCombination = planeCombination;
    }

    public String getPlaneFlight() {
        return planeFlight;
    }

    public String getPlaneDirection() {
        return planeDirection;
    }

    public String getPlaneType() {
        return planeType;
    }

    public String getPlaneTimePlan() {
        return planeTimePlan;
    }

    public String getPlaneTimeFact() {
        return planeTimeFact;
    }

    public String getPlaneStatus() {
        return planeStatus;
    }

    public String getPlaneBaggageStatus() {
        return planeBaggageStatus;
    }

    public String getPlaneGate() {
        return planeGate;
    }

    public String getPlaneCheckIn() {
        return planeCheckIn;
    }

    public String getPlaneCombination() {
        return planeCombination;
    }

    public boolean isPlaneTracking() {
        return planeTracking;
    }

    public void setPlaneTracking(boolean planeTracking) {
        this.planeTracking = planeTracking;
    }

    public String getShotPlaneFlight() {
        String planeFlight;
        String shotPlaneFlight;

        planeFlight = getPlaneFlight();
        shotPlaneFlight = planeFlight.substring(0,2);

        return shotPlaneFlight;
    }
}
