package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper mapper;

    public List<Category> queryCategoryById(Long id) {
        Category record = new Category();
        record.setParentId(id);
        List<Category> select = mapper.select(record);
        return select;
    }

    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.mapper.selectByIdList(ids);
        List<String> collect = categories.stream().map(
                category ->
                        category.getName()
        ).collect(Collectors.toList());
        return collect;
    }
}
