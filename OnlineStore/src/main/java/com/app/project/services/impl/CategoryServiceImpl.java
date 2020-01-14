package com.app.project.services.impl;

import com.app.project.domain.dtos.CategoryDtos.CategorySeedDto;
import com.app.project.domain.entities.Category;
import com.app.project.repositories.CategoryRepository;
import com.app.project.services.CategoryService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final String CATEGORY_FILE_PATH ="src/main/resources/JsonImport/categorySeed.json";
    private CategoryRepository categoryRepository;
    private ValidatorUtil validatorUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;

    @Autowired
    public CategoryServiceImpl(Gson gson, FileUtil fileUtil, ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {

        String information= this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(information,CategorySeedDto[].class);

        for(CategorySeedDto categorySeedDto: categorySeedDtos){

            if(!validatorUtil.isValid(categorySeedDto)){

                validatorUtil.vioations(categorySeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            Category category = this.modelMapper.map(categorySeedDto,Category.class);

            this.categoryRepository.saveAndFlush(category);
        }
    }
}
