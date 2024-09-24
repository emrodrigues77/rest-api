package edware.rest_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "runner_id")
    private Runner runner;

    @NotBlank(message = "Location must not be blank")
    @Size(min=2, message="Location should have at least 2 characters")
    private String location;

    @NotNull(message = "Activity date must not be blank")
    @Past(message = "Activity date must be in the past")
    private LocalDate activityDate;

    @NotNull(message = "Activity time must not be blank")
    private LocalTime activityTime;

    @NotNull(message = "Distance must not be blank")
    @Positive(message = "Distance must be positive")
    private float distance;

    private String remark;
}
