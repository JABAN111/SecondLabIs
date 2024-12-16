package is.fistlab.database.entities;

import is.fistlab.database.enums.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Validated
@Builder
@AllArgsConstructor
public class Person implements CreatorAware {
    @Id
    @Column(nullable = false, unique = true)
    @Size(min = 10)
    private String passportID;
    @Column(nullable = false)
    @Size(min = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Enumerated(EnumType.STRING)
    private Color eyeColor; //Поле может быть null
    @Enumerated(EnumType.STRING)
    private Color hairColor; //Поле может быть null
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Location location; //Поле не может быть null
    @Min(1)
    private int height; //Значение поля должно быть больше 0
    @Min(1)
    private long weight; //Значение поля должно быть больше 0
    @Column
    @Enumerated(EnumType.STRING)
    private Country nationality; //Поле не может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User creator;
}
