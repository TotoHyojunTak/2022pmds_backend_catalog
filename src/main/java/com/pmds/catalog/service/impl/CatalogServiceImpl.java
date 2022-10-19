package com.pmds.catalog.service.impl;

import com.pmds.catalog.data.dto.request.CatalogReqDTO;
import com.pmds.catalog.data.dto.response.CatalogDTO;
import com.pmds.catalog.data.entity.CatalogEntity;
import com.pmds.catalog.data.mapstruct.CatalogMapper;
import com.pmds.catalog.data.repository.CatalogRepository;
import com.pmds.catalog.service.CatalogService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogDTO> getAllCatalogs() {
        return CatalogMapper.INSTANCE.toDtoList(catalogRepository.findAll());
    }

    @Override
    public List<CatalogDTO> createCatalogs(CatalogReqDTO catalogReqDTO) {
        catalogRepository.save(CatalogMapper.INSTANCE.toEntity(catalogReqDTO));

        return getAllCatalogs();
    }
}