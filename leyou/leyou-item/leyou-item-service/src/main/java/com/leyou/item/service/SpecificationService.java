package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup recoder = new SpecGroup();
        recoder.setCid(cid);
        List<SpecGroup> select = this.specGroupMapper.select(recoder);
        return  select;

    }


    public List<SpecParam> queryParams(Long gid,Long cid,Boolean searching,Boolean generic) {
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(gid);
            specParam.setCid(cid);
            specParam.setSearching(searching);
            specParam.setGeneric(generic);
        List<SpecParam> select = this.specParamMapper.select(specParam);
        return select;
    }
}
