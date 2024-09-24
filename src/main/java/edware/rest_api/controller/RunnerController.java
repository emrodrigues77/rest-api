package edware.rest_api.controller;

import edware.rest_api.domain.model.Runner;
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
@RequestMapping("/runners")
@Tag(name = "Runners Controller", description = "RESTful API for managing runners.")
public record RunnerController(RunnerService runnerService) {

    @PostMapping
    @Operation(summary = "Create a new runner", description = "Create a new runner and return the created runner's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Runner created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid runner data provided")
    })
    public ResponseEntity<Runner> create(@Valid @RequestBody Runner runner) {

        Runner savedRunner = runnerService.create(runner);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRunner.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedRunner);
    }

    @GetMapping
    @Operation(summary = "Get all runners", description = "Get a list of all runners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Runners retrieved successfully")
    })
    public ResponseEntity<List<Runner>> findAll() {
        return ResponseEntity.ok(runnerService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a runner by ID", description = "Retrieve a specific runner based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Runner> findById(@PathVariable Long id) {
        return ResponseEntity.ok(runnerService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a runner", description = "Update a runner based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Runner updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid runner data provided"),
            @ApiResponse(responseCode = "404", description = "Runner not found")
    })
    public ResponseEntity<Runner> update(@PathVariable Long id, @Valid @RequestBody Runner runner) {
        return ResponseEntity.ok(runnerService.update(id, runner));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a runner", description = "Delete a runner based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Runner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Runner not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        runnerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
