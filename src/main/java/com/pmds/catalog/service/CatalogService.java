package com.pmds.catalog.service;

import com.pmds.catalog.data.dto.request.CatalogReqDTO;
import com.pmds.catalog.data.dto.response.CatalogDTO;

import java.util.List;

public interface CatalogService {
    List<CatalogDTO> getAllCatalogs();

    List<CatalogDTO> createCatalogs(CatalogReqDTO catalogReqDTO);
}
