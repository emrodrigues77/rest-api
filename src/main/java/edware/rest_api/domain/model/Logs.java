package edware.rest_api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Activity activity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Runner runner;

    private String location;

    private LocalDate activityDate;

    private LocalTime activityTime;

    private float distance;

    private String remark;
}
