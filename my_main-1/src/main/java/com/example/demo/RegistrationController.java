package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private WorkoutService workoutService;

    @GetMapping
    public String showAllRegistrations(Model model) {
        List<Registration> registrations = registrationService.getAllRegistrations();
        model.addAttribute("registrations", registrations);
        return "registrations";
    }

    @GetMapping("/add")
    public String showAddRegistrationForm(Model model) {
        List<Client> clients = clientService.getAllClients();
        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("clients", clients);
        model.addAttribute("workouts", workouts);
        model.addAttribute("registration", new Registration());
        return "add-registration";
    }

    @PostMapping("/add")
    public String addRegistration(@ModelAttribute Registration registration,
        @RequestParam("clientId") Long clientId,
        @RequestParam("workoutId") Long workoutId) {
        Workout workout = workoutService.getWorkoutById(workoutId);
        if (workout.getPlaces() <= registrationService.getRegistrationsCountForWorkout(workoutId)) {
            return "redirect:/registrations";
        }
        Client client = clientService.getClientById(clientId);
        registration.setClient(client);
        registration.setWorkout(workout);
        registrationService.addRegistration(registration);
        return "redirect:/registrations";
    }

    @GetMapping("/update/{id}")
    public String showUpdateRegistrationForm(@PathVariable Long id, Model model) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        registration.ifPresent(r -> model.addAttribute("registration", r));
        return "update-registration";
    }

    @PostMapping("/update/{id}")
    public String updateRegistration(@PathVariable Long id, @ModelAttribute Registration updatedRegistration) {
        registrationService.updateRegistration(id, updatedRegistration);
        return "redirect:/registrations";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return "redirect:/registrations";
    }
}