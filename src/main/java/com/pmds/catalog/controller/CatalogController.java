package com.pmds.catalog.controller;

import com.pmds.catalog.data.dto.request.CatalogReqDTO;
import com.pmds.catalog.data.dto.response.CatalogDTO;
import com.pmds.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    /*
        health check
     */

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    /*
        [조회] Catalog 목록 조회
     */
    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogDTO>> getCatalogs() {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.getAllCatalogs());
    }

    /*
        [저장] Catalog 등록
     */
    @PostMapping("/catalogs")
    public ResponseEntity<List<CatalogDTO>> createCatalogs(@RequestBody CatalogReqDTO catalogReqDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.createCatalogs(catalogReqDTO));
    }

    @GetMapping("/feign/list")
    public ResponseEntity<List<Map<String, String>>> getCatalogListForFeign(){
        List<Map<String, String>> returnValue = new ArrayList<>();

        List<CatalogDTO> result = catalogService.getAllCatalogs();
        result.forEach(e->{

            Map<String, String> temp = new HashMap<>();
            temp.put("productId", e.getProductId());
            temp.put("productName", e.getProductName());
            temp.put("stock", String.valueOf(e.getStock()));
            temp.put("unitPrice", String.valueOf(e.getStock()));

            returnValue.add(temp);
        });

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping("/feign/list")
    public ResponseEntity<List<Map<String, String>>> saveCatalogForFeign(@RequestBody Map<String, String> input){
        CatalogReqDTO inputData = new CatalogReqDTO();
        inputData.setProductId(input.get("productId"));
        inputData.setProductName(input.get("productName"));
        inputData.setStock(Integer.parseInt(input.get("stock")));
        inputData.setUnitPrice(Integer.parseInt(input.get("unitPrice")));

        createCatalogs(inputData);

        return getCatalogListForFeign();
    }

}