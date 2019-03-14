package maxz.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name="task")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@ToString
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public int state; //enum
    public String data;
    @Version
    public int version;

}
