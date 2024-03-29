package ru.effective.mobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 3, max = 35, message = "This field must be for 3 to 35 characters")
    @NotBlank(message = "This field cannot contain spaces")
    @JsonProperty("title")
    @NotNull(message = "This field cannot be empty")
    private String title;

    @NotNull(message = "This field cannot be empty")
    @NotBlank(message = "This field cannot contain spaces")
    @JsonProperty("description")
    private String description;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @JsonProperty("priority")
    private Priority priority;

    @ManyToOne()
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne()
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private User executor;


    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    @JsonProperty("comments")
    private List<Comment> comments;

    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}


