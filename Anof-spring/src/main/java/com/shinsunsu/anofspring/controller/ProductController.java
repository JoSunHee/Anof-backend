package com.shinsunsu.anofspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinsunsu.anofspring.dto.request.RegisterProductRequest;
import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.ProductService;
import com.shinsunsu.anofspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private UserService userService;


    //get으로 변경 예정
    @PostMapping("/detail/barcode") //바코드 인식을 통한 식품 상세 조회
    public ResponseEntity<Object> detailProductByBarcode(@RequestBody Map<String, String> barcode) throws JsonProcessingException {
      
        String barcodeNumber = barcode.get("barcode");
        if(!productService.checkBarcodeExist(barcodeNumber)) { //인식한 바코드 식품 db에 존재하지 않을 경우
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();
        String product = mapper.writeValueAsString(productService.detailProductByBarcode(barcodeNumber));
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    //get으로 변경 예정
    @PostMapping("/detail/name") //식품명 검색을 통한 식품 상세 조회
    public ResponseEntity<Object> detailProductByName(@RequestBody Map<String, String> name, Principal principal) throws JsonProcessingException {
        System.out.println(principal.getName());
        String productName = name.get("name");
        if(!productService.checkNameExist(productName)) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();
        String product = mapper.writeValueAsString(productService.detailProductByName(productName));
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    //get으로 변경
    @PostMapping("/search") //상품명 검색 -> 상품 리스트 제공
    public ResponseEntity<List<ProductResponse>> search(@RequestBody Map<String, String> keyword, Principal principal) {
        String productKeyword = keyword.get("keyword");
        return new ResponseEntity<>(productService.search(productKeyword), HttpStatus.OK);
    }

    @PostMapping("/new") //상품 등록
    public ResponseEntity registerProduct(@RequestBody RegisterProductRequest request, Principal principal) {
        return new ResponseEntity(productService.registerProduct(request, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/custom")
    public ResponseEntity<Object> customInfo(@RequestBody Map<String, String> name, Principal principal) {

        return new ResponseEntity<>(productService.customInfo(name.get("name"), principal.getName()), HttpStatus.OK);
    }

}
