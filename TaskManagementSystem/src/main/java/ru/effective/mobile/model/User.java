package ru.effective.mobile.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
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
