package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService service;

    /**
     * 根据查询条件分页查询品牌信息并排序
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(@RequestParam(value = "key", required = false) String key,
                                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                               @RequestParam(value = "sortBy", required = false)  String sortBy,
                                                               @RequestParam(value = "desc", required = false)  Boolean desc
                                                               ){
        PageResult<Brand> result=this.service.queryBrandsByPage(key,page,rows,sortBy,desc);
        if ( CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> addBrands (Brand brand, @RequestParam("cids")List<Long> cids){
        this.service.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @RequestMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid){
        List<Brand> brands=this.service.queryBrandsByCid(cid);
        if ( CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(brands);
    }

}
