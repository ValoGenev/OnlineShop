package com.app.project.services.impl;

import com.app.project.domain.dtos.AddressDtos.AddressSeedDto;
import com.app.project.domain.entities.Address;
import com.app.project.repositories.AddressRepository;
import com.app.project.services.AddressService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AddressServiceImpl implements AddressService {

    private final String ADDRESS_FILE_PATH = "src/main/resources/JsonImport/addressSeed.json";
    private AddressRepository addressRepository;
    private ValidatorUtil validatorUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;

    @Autowired
    public AddressServiceImpl(FileUtil fileUtil, Gson gson, AddressRepository addressRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.addressRepository = addressRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedAddresses() throws IOException {

        String information= this.fileUtil.fileContent(ADDRESS_FILE_PATH);

        AddressSeedDto[] addressSeedDtos = this.gson.fromJson(information,AddressSeedDto[].class);

        for(AddressSeedDto addressSeedDto: addressSeedDtos){

            if(!validatorUtil.isValid(addressSeedDto)){

                validatorUtil.vioations(addressSeedDto)
                        .forEach(violation-> System.out.println(violation.getMessage()));

                continue;
            }

            Address address = this.modelMapper.map(addressSeedDto,Address.class);
            this.addressRepository.saveAndFlush(address);

        }

    }
}
