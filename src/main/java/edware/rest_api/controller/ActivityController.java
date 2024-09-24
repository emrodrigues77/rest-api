package edware.rest_api.controller;


import edware.rest_api.domain.model.Activity;
import edware.rest_api.service.ActivityService;
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
@RequestMapping("/activities")
@Tag(name = "Activities Controller", description = "RESTful API for managing activities.")
public record  ActivityController(ActivityService activityService) {

    @GetMapping
    @Operation(summary = "Get all activities", description = "Get a list of all activities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully")
    })
    public ResponseEntity<List<Activity>> findAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @PostMapping
    @Operation(summary = "Create a new activity", description = "Create a new activity and return the created activity's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid activity data provided")
    })
    public ResponseEntity<Activity> create(@Valid @RequestBody Activity activity) {
        Activity savedActivity = activityService.create(activity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedActivity.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedActivity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a activity by ID", description = "Retrieve a specific activity based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    public ResponseEntity<Activity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing activity", description = "Update an existing activity and return the updated activity's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid activity data provided"),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    public ResponseEntity<Activity> update(@PathVariable Long id, @Valid @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.update(id, activity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an activity by ID", description = "Delete an activity based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
