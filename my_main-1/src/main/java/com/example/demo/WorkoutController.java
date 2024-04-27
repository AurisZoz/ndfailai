package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping
    public String showAllWorkouts(Model model) {
        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("workouts", workouts);
        return "workouts";
    }

    @GetMapping("/add")
    public String showAddWorkoutForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "add-workout";
    }

    @PostMapping("/add")
    public String addWorkout(@ModelAttribute Workout workout) {
        workoutService.addWorkout(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/update/{id}")
    public String showUpdateWorkoutForm(@PathVariable Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);
        model.addAttribute("workout", workout);
        return "update-workout";
    }

    @PostMapping("/update/{id}")
    public String updateWorkout(@PathVariable Long id, @ModelAttribute Workout workout) {
        workout.setId(id);
        workoutService.updateWorkout(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/delete/{id}")
    public String deleteWorkoutById(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts";
    }
}