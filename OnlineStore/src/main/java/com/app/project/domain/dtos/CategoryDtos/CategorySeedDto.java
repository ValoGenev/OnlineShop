package com.app.project.domain.dtos.CategoryDtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class CategorySeedDto {

    @Expose
    private String name;

    public CategorySeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
