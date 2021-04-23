package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service


public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private Stock stockMapper;

    /**
     *
     * 根据分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        if(saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        PageHelper.startPage(page,rows);

        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        List<SpuBo> spuBos = spus.stream().map(
                spu -> {
                    SpuBo spuBo = new SpuBo();
                    BeanUtils.copyProperties(spu, spuBo);
                    //查询品牌名称
                    Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
                    spuBo.setBname(brand.getName());


                    List<String> strings = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
                    String join = StringUtils.join(strings, "--");
                    spuBo.setCname(join);
                    return spuBo;
                }
        ).collect(Collectors.toList());
        return  new PageResult<>(spuPageInfo.getTotal(),spuBos);
    }

    @Transactional
    public void saveGoods(SpuBo spuBo) {
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuBo.getSpuDetail());

        //新增sku
        saveSkuAndStock(spuBo);

    }

    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku ->
                {
                    sku.setId(null);
                    sku.setSpuId(spuBo.getId());
                    sku.setCreateTime(new Date());
                    sku.setLastUpdateTime(sku.getCreateTime());
                    this.skuMapper.insertSelective(sku);
                    com.leyou.item.pojo.Stock stock = new com.leyou.item.pojo.Stock();
                    stock.setSkuId(sku.getId());
                    stock.setStock(sku.getStock());
                    this.stockMapper.insertSelective(stock);
                }
        );
    }

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = this.spuDetailMapper.selectByPrimaryKey(spuId);
        return  spuDetail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku recoder = new Sku();
        recoder.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(recoder);
        skus.forEach(sku -> {

            com.leyou.item.pojo.Stock stock = this.stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());

        });
        return skus;

    }

    @Transactional
    public void updateGoods(SpuBo spuBo) {

        Sku recoder = new Sku();
        recoder.setSpuId(spuBo.getId());
        List<Sku> skus = this.skuMapper.select(recoder);
        //删除stock
        skus.forEach(
                sku -> {
                    this.stockMapper.deleteByPrimaryKey(sku.getId());
                }
        );

        //删除sku
        Sku sku = new Sku();
        sku.setSpuId(spuBo.getId());
        this.skuMapper.delete(sku);

        //创建sku

        this.saveSkuAndStock(spuBo);
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(new Date());
        spuBo.setSaleable(null);
        spuBo.setValid(null);

        this.spuMapper.updateByPrimaryKeySelective(spuBo);
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }
}
