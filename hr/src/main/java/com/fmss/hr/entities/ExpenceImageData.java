package com.fmss.hr.entities;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 import org.hibernate.annotations.OnDelete;
 import org.hibernate.annotations.OnDeleteAction;

 import javax.persistence.*;


@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenceImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @Lob
    @Column(name = "imagedata",length = 1000)
    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "expenseId", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Expense expense;


}
