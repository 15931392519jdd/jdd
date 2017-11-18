package cn.smbms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;

public interface ProviderDao {

	public List<Provider> queryList(@Param("proCode") String proCode, @Param("proName") String proName);

	public Provider queryById(Integer id);

	public int updateByProvider(Provider provider);

	public int deleteByPriid(Integer id);

	public int addProvider(Provider provider);
}
