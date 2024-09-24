package edware.rest_api.controller;

import edware.rest_api.domain.model.Activity;
import edware.rest_api.domain.model.Logs;
import edware.rest_api.domain.model.Runner;
import edware.rest_api.service.ActivityService;
import edware.rest_api.service.LogsService;
import edware.rest_api.service.RunnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/logs")
@Tag(name = "Logs Controller", description = "RESTful API for managing logs.")
public record LogsController(LogsService logsService, RunnerService runnerService, ActivityService activityService) {

    @GetMapping
    @Operation(summary = "Get all logs", description = "Get a list of all logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs retrieved successfully")
    })
    public ResponseEntity<List<Logs>> findAll() {
        return ResponseEntity.ok(logsService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a log by ID", description = "Retrieve a specific log based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Log not found")
    })
    public ResponseEntity<Logs> findById(Long id) {
        return ResponseEntity.ok(logsService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new log for a user", description = "Create a new log for a user and return the created log's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid log data provided")
    })
    public ResponseEntity<Logs> create(@RequestParam("runner") Long runnerId, @RequestParam("activity") Long activityId, @Valid @RequestBody Logs logs) {
        Runner dbRunner = runnerService.findById(runnerId);
        Activity dbActivity = activityService.findById(activityId);

        System.out.println(dbRunner);
        System.out.println(dbActivity);

        if (dbRunner == null || dbActivity == null) {
            return ResponseEntity.badRequest().build();
        }

        logs.setRunner(dbRunner);
        logs.setActivity(dbActivity);

        Logs savedLogs = logsService.create(logs);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLogs.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedLogs);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a log", description = "Update a log based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid log data provided"),
            @ApiResponse(responseCode = "404", description = "Log not found")
    })
    public ResponseEntity<Logs> update(Long id, @Valid @RequestBody Logs logs) {
        return ResponseEntity.ok(logsService.update(id, logs));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a log by ID", description = "Delete a log based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Log deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Log not found")
    })
    public ResponseEntity<Void> delete(Long id) {
        logsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
