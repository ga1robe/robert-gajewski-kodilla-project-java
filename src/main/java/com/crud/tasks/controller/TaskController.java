package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final DbService dbService;
    private final TaskMapper taskMapper;
    private TaskDto taskDto;
    private final List<TaskDto> taskDtoList = new ArrayList<>();

    @GetMapping(value = "getTask")
    public TaskDto getTask(Long taskId) {

        return new TaskDto(1L, "test title", "test_content");
    }

    @GetMapping(value = "getTasks", params = "id")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "getTasks?id={taskId}")
    public List<TaskDto> getTaskById(@PathVariable Long taskId) throws TaskNotFoundException  {
        if (!dbService.getTaskById(taskId).isEmpty())
            return taskMapper.mapToTaskDtoList(dbService.getTaskById(taskId));
        else
            return (List<TaskDto>) new TaskNotFoundException();
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
