package com.pmds.catalog.controller;

import com.pmds.catalog.data.dto.request.CatalogReqDTO;
import com.pmds.catalog.data.dto.response.CatalogDTO;
import com.pmds.catalog.data.mapstruct.CatalogMapper;
import com.pmds.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogDTO>> getCatalogs() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.getAllCatalogs());
    }

    @PostMapping("/catalogs")
    public ResponseEntity<List<CatalogDTO>> createCatalogs(@ModelAttribute CatalogReqDTO catalogReqDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.createCatalogs(catalogReqDTO));
    }

}