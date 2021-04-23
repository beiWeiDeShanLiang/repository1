package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);

        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);

        }
        PageHelper.startPage(page,rows);
        if(StringUtils.isNotBlank(sortBy)){

            example.setOrderByClause(sortBy+" "+(desc?"desc":"asc"));

        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
         return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增brand表
        this.brandMapper.insertSelective(brand);

            cids.forEach(cid ->{
                    this.brandMapper.insertCategoryAndBrand(cid,brand.getId());

            });
        }

    public List<Brand> queryBrandsByCid(Long cid) {
    return this.brandMapper.selectBrandByCid(cid);
    }
}
