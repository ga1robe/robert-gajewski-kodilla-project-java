package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    private TaskDto taskDto;
    private final List<TaskDto> taskDtoList = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() { return taskDtoList; }

    @GetMapping(value = "getTask")
    public TaskDto getTask(Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping(value = "deleteTask/{taskId}")
    public ResponseEntity<Long> deleteTask(@PathVariable Long taskId) {
        var isRemoved = taskDtoList.remove(taskId);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }


    @GetMapping(value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @PostMapping(value = "createTask")
    public void createTask(TaskDto taskDto) {
        this.taskDto = new TaskDto(1L, "Created test title", "Test content");
    }
}
