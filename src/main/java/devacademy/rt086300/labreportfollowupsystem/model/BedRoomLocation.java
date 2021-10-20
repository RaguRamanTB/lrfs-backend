package devacademy.rt086300.labreportfollowupsystem.model;

public class BedRoomLocation {
	private Bed bed;
	private Room room;
	private LocationUnit locationUnit;
	private PatientSummary patientSummary;

	public BedRoomLocation() {
	}

	public Bed getBed() {
		return bed;
	}

	public Room getRoom() {
		return room;
	}

	public PatientSummary getPatientSummary() {
		return patientSummary;
	}

	public void setPatientSummary(PatientSummary patientSummary) {
		this.patientSummary = patientSummary;
	}

	public LocationUnit getLocationUnit() {
		return locationUnit;
	}

	public void setBed(Bed bed) {
		this.bed = bed;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setLocationUnit(LocationUnit locationUnit) {
		this.locationUnit = locationUnit;
	}

}
