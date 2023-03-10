package pt.solutions.af.workingplan.application;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.solutions.af.utils.TimePeriod;
import pt.solutions.af.workingplan.model.WorkingPlan;
import pt.solutions.af.workingplan.repository.WorkingPlanRepository;

@Service
@AllArgsConstructor
@Log4j2
public class WorkingPlanApplicationService {

    private WorkingPlanRepository repository;

    public void updateWorkingPlan(WorkingPlan updateData) {
        WorkingPlan workingPlan = repository.getReferenceById(updateData.getId());
        workingPlan.getMonday().setWorkingHours(updateData.getMonday().getWorkingHours());
        workingPlan.getTuesday().setWorkingHours(updateData.getTuesday().getWorkingHours());
        workingPlan.getWednesday().setWorkingHours(updateData.getWednesday().getWorkingHours());
        workingPlan.getThursday().setWorkingHours(updateData.getThursday().getWorkingHours());
        workingPlan.getFriday().setWorkingHours(updateData.getFriday().getWorkingHours());
        workingPlan.getSaturday().setWorkingHours(updateData.getSaturday().getWorkingHours());
        workingPlan.getSunday().setWorkingHours(updateData.getSunday().getWorkingHours());
        repository.save(workingPlan);
    }

    public void addBreakToWorkingPlan(TimePeriod breakToAdd, String planId, String dayOfWeek) {
//        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = repository.getReferenceById(planId);
//        if (workingPlan.getProvider().getId().equals(currentUser.getId())) {
//            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
//        }
        workingPlan.getDay(dayOfWeek).getBreaks().add(breakToAdd);
        repository.save(workingPlan);
    }

    public void deleteBreakFromWorkingPlan(TimePeriod breakToDelete, String planId, String dayOfWeek) {
//        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        WorkingPlan workingPlan = repository.getReferenceById(planId);
//        if (workingPlan.getProviderId().equals(currentUser.getId())) {
//            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
//        }
        workingPlan.getDay(dayOfWeek).getBreaks().remove(breakToDelete);
        repository.save(workingPlan);
    }

}
