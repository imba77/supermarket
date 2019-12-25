package top.imba77.service;

import top.imba77.pojo.Provider;
import top.imba77.vo.ProviderVo;

import java.util.List;

public interface ProviderService {
    List<Provider> queryProviderList() throws Exception;

    List<ProviderVo> queryProviderVoList(String queryProCode, String queryProName) throws Exception;

    Boolean delProviderById(String proId) throws Exception;

    ProviderVo queryProviderById(String proId) throws Exception;

    Boolean updateProviderInfo(Provider provider, Long id) throws Exception;

    Boolean addProvider(Provider provider, Long id) throws Exception;
}
