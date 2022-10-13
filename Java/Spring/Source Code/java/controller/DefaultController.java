package com.mkyong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mkyong.model.Exercise;
import com.mkyong.model.User;
import com.mkyong.service.Facade;

@Controller
public class DefaultController {

	@Autowired
	Facade facade;
	
    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @RequestMapping(value="/getexercises", method = RequestMethod.GET)
    public String getExercises(Model model){
    	List<Exercise> exercises = facade.findAllExercises();
    	model.addAttribute("exercises", exercises);
    	return "/getexercises";
    }
    
    @RequestMapping(value="/getusers", method = RequestMethod.GET)
    public String getUsers(Model model){
    	List<User> users = facade.findAllUsers();
    	model.addAttribute("users", users);
    	return "/getusers";
    }
    
    @RequestMapping(value="/exercisecreate", method=RequestMethod.GET)
    public String createExerciseShow(Model model){
    	Exercise ex = new Exercise();
    	model.addAttribute("exercise", ex);
    	return "/exercisecreate";
    }
    
    @RequestMapping(value="/exercisecreate", method=RequestMethod.POST)
    public String createExercise(@ModelAttribute(value="exercise") Exercise ex, Model model){
   
    	facade.createExercise(ex);
    	return "redirect:/exercisecreate";
    }
    
    @RequestMapping(value="/exercisedelete", method=RequestMethod.GET)
    public String removeExerciseShow(Model model){
    	Exercise exercise = new Exercise();
    	model.addAttribute("exercise", exercise);
    	return "/exercisedelete";
    }
    
    @RequestMapping(value="/exercisedelete", method=RequestMethod.POST)
    public String removeExercise(@ModelAttribute(value="exercise") Exercise exercise, Model model){
    	facade.deleteExercise(exercise.exerciseid);
    	return "redirect:/exercisedelete";
    }
    
    @RequestMapping(value="/exerciseupdate", method=RequestMethod.GET)
    public String updateexerciseShow(Model model){
    	Exercise exercise = new Exercise();
    	model.addAttribute("exercise", exercise);
    	return "/exerciseupdate";
    }
    
    @RequestMapping(value="/exerciseupdate", method=RequestMethod.POST)
    public String updateExercise(@ModelAttribute(value="exercise") Exercise exercise, Model model){
    	facade.updateExercise(exercise);
    	return "redirect:/exerciseupdate";
    }
    
    
    @RequestMapping(value="/usercreate", method=RequestMethod.GET)
    public String addUserShow(Model model){
    	User u = new User();
    	model.addAttribute("user", u);
    	return "/usercreate";
    }
    
    @RequestMapping(value="/usercreate", method=RequestMethod.POST)
    public String addUser(@ModelAttribute(value="user") User u, Model model){
   
    	facade.createUser(u);
    	return "redirect:/usercreate";
    }
    
    @RequestMapping(value="/userdelete", method=RequestMethod.GET)
    public String removeUserShow(Model model){
    	User u = new User();
    	model.addAttribute("user", u);
    	return "/userdelete";
    }
    
    @RequestMapping(value="/userdelete", method=RequestMethod.POST)
    public String removeUser(@ModelAttribute(value="user") User u, Model model){
    	facade.deleteUser(u.userid);
    	return "redirect:/userdelete";
    }
    
    @RequestMapping(value="/userupdate", method=RequestMethod.GET)
    public String updateUserShow(Model model){
    	User u = new User();
    	model.addAttribute("user", u);
    	return "/userupdate";
    }
    
    @RequestMapping(value="/userupdate", method=RequestMethod.POST)
    public String updateUser(@ModelAttribute(value="user") User u, Model model){
    	facade.updateUser(u);
    	return "redirect:/userupdate";
    }
}
