package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.TaskRequest;
import com.elevate.ElevateBackend.entity.Task;
import com.elevate.ElevateBackend.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins="http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    
    @PostMapping
    public Task createTask(@RequestBody TaskRequest request,
                           Principal principal) {

        return taskService.createTask(request, principal.getName());
    }

    @GetMapping
    public List<Task> getAllTasks(Principal principal) {

        return taskService.getAllTasks(principal.getName());
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id){

        return taskService.getTask(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id,
                             Principal principal) {

        taskService.deleteTask(id, principal.getName());

        return "Task Deleted Successfully";
    }
    
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody TaskRequest request,
                           Principal principal){

        return taskService.updateTask(
                id,
                request,
                principal.getName());
    }
    
    @PutMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id,
                             Principal principal){

        return taskService.markCompleted(
                id,
                principal.getName());
    }
    
    @PutMapping("/{id}/progress/{progress}")
    public Task updateProgress(@PathVariable Long id,
                               @PathVariable Integer progress,
                               Principal principal){

        return taskService.updateProgress(
                id,
                progress,
                principal.getName());
    }
    
    @GetMapping("/completed")
    public List<Task> completedTasks(Principal principal){

        return taskService.getCompletedTasks(
                principal.getName());
    }
    
    @GetMapping("/pending")
    public List<Task> pendingTasks(Principal principal){

        return taskService.getPendingTasks(
                principal.getName());
    }
    
    @GetMapping("/search")
    public List<Task> search(
            @RequestParam String keyword,
            Principal principal){

        return taskService.searchTasks(
                principal.getName(),
                keyword);
    }
    
    @GetMapping("/today")
    public List<Task> dueToday(
            Principal principal){

        return taskService.dueToday(
                principal.getName());
    }
    
    @GetMapping("/sort")
    public List<Task> sortTasks(
            Principal principal){

        return taskService.sortByDueDate(
                principal.getName());
    }
    
    @GetMapping("/latest")
    public List<Task> latestTasks(
            Principal principal){

        return taskService.latestTasks(
                principal.getName());
    }
    
    @GetMapping("/page")
    public Page<Task> page(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal){

        return taskService.getTasksPage(
                principal.getName(),
                page,
                size);
    }

}