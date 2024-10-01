package sq.project.md5.perfumer.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


    @Entity
    @Table(name = "reviews")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @Column( columnDefinition = "TEXT")
        private String content;

        @Column
        private Integer rate;

        @JsonFormat(pattern = "dd/MM/yyyy")
        @Column( nullable = false)
        private Date created;
    }
