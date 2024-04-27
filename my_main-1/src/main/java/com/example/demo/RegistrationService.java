package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration addRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }

    public Registration updateRegistration(Long id, Registration updatedRegistration) {
        if (registrationRepository.existsById(id)) {
            updatedRegistration.setId(id);
            return registrationRepository.save(updatedRegistration);
        } else {
            return null; 
        }
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    public int getRegistrationsCountForWorkout(Long workoutId) {
        return registrationRepository.countByWorkoutId(workoutId);
    }
}