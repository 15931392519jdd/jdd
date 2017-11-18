package cn.smbms.service.user;

import java.util.List;

import cn.smbms.pojo.Provider;

public interface ProviderService {

	public List<Provider> queryList(String proCode, String proName);

	public Provider queryById(Integer id);

	public int updateByProvider(Provider provider);

	public int deleteByPriid(Integer id);
	
	public int addProvider(Provider provider);
	
}
