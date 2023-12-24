package ru.effective.mobile.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.Priority;
import ru.effective.mobile.model.Status;
import ru.effective.mobile.model.User;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDTO {

    @Size(min = 3, max = 35, message = "This field must be for 3 to 35 characters")
    @NotBlank(message = "This field cannot contain spaces")
    @JsonProperty("title")
    @NotNull(message = "This field cannot be empty")
    private String title;

    @NotNull(message = "This field cannot be empty")
    @NotBlank(message = "This field cannot contain spaces")
    @JsonProperty("description")
    private String description;
    //
//    @JsonProperty("status")
//    private Status status;
//
//
//    @JsonProperty("priority")
//    private Priority priority;
//
//    private User owner;
//
//
//    private User executor;
    @JsonProperty("comments")
    private List<Comment> comments;
}
