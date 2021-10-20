package devacademy.rt086300.labreportfollowupsystem.service;

import java.util.HashMap;
import java.util.List;

import devacademy.rt086300.labreportfollowupsystem.model.LabTest;
import devacademy.rt086300.labreportfollowupsystem.model.PatientSummary;

/**
 * An interface PatientSummaryService, to abstract the methods. The
 * implementations are in PatientSummaryServiceImpl class.
 * 
 * @author RT086300
 *
 */
public interface PatientSummaryService {
	List<PatientSummary> listAllPatientSummaries();

	List<PatientSummary> listAllPatientSummariesByPatId(long id);

	List<Long> listByBedIdAndDischarged(List<Long> bedIdList, int isDischarged);

	HashMap<Long, List<LabTest>> listByBedIdAndNotifiedAndDischarged(List<Long> bedIdList, int isNotified,
			int isDischarged);

	List<LabTest> listTestsByPatId(long patId);

	LabTest findLabTestById(long id);

	List<LabTest> updateLabTest(long patId);

	List<LabTest> revertLabTest(long patId);
}
