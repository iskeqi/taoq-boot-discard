package tech.taoq.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.taoq.common.exception.client.ClientErrorException;
import tech.taoq.common.exception.client.ParamIllegalException;
import tech.taoq.mp.pojo.PageDto;
import tech.taoq.mp.pojo.PageParam;
import tech.taoq.system.domain.db.DictTypeDO;
import tech.taoq.system.mapper.DictItemMapper;
import tech.taoq.system.mapper.DictTypeMapper;
import tech.taoq.system.service.DictTypeService;

import java.util.Objects;

@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;
    @Autowired
    private DictItemMapper dictItemMapper;

    public void insert(DictTypeDO param) {
        DictTypeDO t = dictTypeMapper.selectOne(Wrappers.query(new DictTypeDO().setType(param.getType())));
        if (!Objects.isNull(t)) {
            throw new ParamIllegalException("type:" + param.getType() + " 已经存在");
        }

        dictTypeMapper.insert(param);
    }

    public void deleteById(String id) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectById(id);
        if (dictTypeDO == null) {
            throw new ClientErrorException("对应记录不存在");
        }

        DictTypeDO t1 = new DictTypeDO();
        t1.setId(dictTypeDO.getId());
        t1.setDeleted(true);
        dictTypeMapper.updateById(t1);
    }

    public void updateById(DictTypeDO param) {
        param.setType(null);
        dictTypeMapper.updateById(param);
    }

    public DictTypeDO getById(String id) {
        return dictTypeMapper.selectById(id);
    }

    public PageDto<DictTypeDO> page(PageParam<DictTypeDO> param) {
        Page<DictTypeDO> page = dictTypeMapper.selectPage(param.toPage(), Wrappers.lambdaQuery(DictTypeDO.class)
                .eq(DictTypeDO::getDeleted, false));
        return new PageDto<>(page.getTotal(), page.getRecords());
    }
}
