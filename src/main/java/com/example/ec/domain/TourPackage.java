package com.example.ec.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A Classification of Tours.
 */
@Entity
public class TourPackage {
    @Id
    private String code;

    @Column
    private String name;

    public TourPackage(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected TourPackage() {
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "TourPackageRepository {" +
                "code='" + code + "'," +
                "name='" + name + "'," +
                "}";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
