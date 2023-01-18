package com.example.jkeduhomepage.module.common.utility;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Basetime {

    @CreatedDate
    private LocalDate create_date;

    @LastModifiedDate
    private LocalDate update_date;

    public LocalDate getCreateDate() {
        return create_date;
    }


    public LocalDate getUpdateDate() {
        return update_date;
    }
}
