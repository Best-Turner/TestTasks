package ru.effective.mobile.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Task> tasks;
    @OneToMany(mappedBy = "executor")
    private List<Task> tasksToComplete;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasksToComplete() {
        return tasksToComplete;
    }

    public void setTasksToComplete(List<Task> tasksToComplete) {
        this.tasksToComplete = tasksToComplete;
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public void addTaskToComplete(Task task) {
        if (tasksToComplete == null) {
            tasksToComplete = new ArrayList<>();
        }
        tasksToComplete.add(task);
    }
}
