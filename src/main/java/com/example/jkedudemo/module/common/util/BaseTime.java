package com.example.jkedudemo.module.common.util;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

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
