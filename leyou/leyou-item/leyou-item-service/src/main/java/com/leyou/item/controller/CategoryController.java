package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
@GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryById(@RequestParam(value="pid" ,defaultValue = "0") Long id){

            if (id==null||id<0){
                return ResponseEntity.badRequest().build();

            }
            List<Category> cate=this.categoryService.queryCategoryById(id);
            if (CollectionUtils.isEmpty(cate)){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(cate);
        }

    }


