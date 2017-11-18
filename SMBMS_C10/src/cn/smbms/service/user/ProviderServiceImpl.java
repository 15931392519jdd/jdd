package cn.smbms.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.mapper.ProviderDao;
import cn.smbms.pojo.Provider;

@Service(value="providerService")
public class ProviderServiceImpl implements ProviderService {

	@Resource
	private ProviderDao providerDao;
	
	@Override
	public List<Provider> queryList(String proCode, String proName) {
		// TODO Auto-generated method stub
		return providerDao.queryList(proCode,proName);
	}

	@Override
	public Provider queryById(Integer id) {
		// TODO Auto-generated method stub
		return providerDao.queryById(id);
	}

	@Override
	public int updateByProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerDao.updateByProvider(provider);
	}

	@Override
	public int deleteByPriid(Integer id) {
		// TODO Auto-generated method stub
		return providerDao.deleteByPriid(id);
	}

	@Override
	public int addProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerDao.addProvider(provider);
	}

}
