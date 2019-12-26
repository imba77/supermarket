package top.imba77.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imba77.dao.ProviderMapper;
import top.imba77.pojo.Provider;
import top.imba77.pojo.ProviderExample;
import top.imba77.vo.ProviderVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> queryProviderList() throws Exception {
        ProviderExample example = new ProviderExample();
        return providerMapper.selectByExample(example);
    }

    @Override
    public List<ProviderVo> queryProviderVoList(String queryProCode, String queryProName) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("queryProCode", queryProCode);
        param.put("queryProName", queryProName);
        return providerMapper.selectProviderList(param);
    }

    @Override
    public Boolean delProviderById(String proId) throws Exception {
        int i = providerMapper.deleteByPrimaryKey(Long.parseLong(proId));
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public ProviderVo queryProviderById(String proId) throws Exception {
        ProviderVo providerVo = new ProviderVo();
        Provider provider = providerMapper.selectByPrimaryKey(Long.parseLong(proId));
        BeanUtils.copyProperties(provider, providerVo);
        return providerVo;
    }

    @Override
    public void updateProviderInfo(Provider provider, Long id) throws Exception {
        Provider tempProvider = providerMapper.selectByPrimaryKey(provider.getId());
//        provider.setCreatedBy(tempProvider.getCreatedBy());
//        provider.setCreationDate(tempProvider.getCreationDate());
        provider.setModifyBy(id);
        provider.setModifyDate(new Date());
        providerMapper.updateByPrimaryKey(provider);
    }

    @Override
    public void addProvider(Provider provider, Long id) throws Exception {
        provider.setCreatedBy(id);
        provider.setCreationDate(new Date());
        providerMapper.insert(provider);
    }
}
