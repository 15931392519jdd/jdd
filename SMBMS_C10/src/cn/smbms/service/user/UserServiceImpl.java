package cn.smbms.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.mapper.RoleDao;
import cn.smbms.mapper.UserDao;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Resource
	private RoleDao roleDao;

	@Override
	public User login(String userCode, String userPassword) {
		// TODO Auto-generated method stub
		return userDao.login(userCode, userPassword);
	}

	@Override
	public int getUserCount(String userName, int userRole) {

		return userDao.getUserCount(userName, userRole);
	}

	@Override
	public List<Role> queryRoleList() {
		// TODO Auto-generated method stub
		return roleDao.queryRoleList();
	}

	@Override
	public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
		// TODO Auto-generated method stub
		return userDao.getUserList(userName, userRole, (currentPageNo - 1) * pageSize, pageSize);
	}

	@Override
	public boolean add(User user) {
		try {
			userDao.add(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUserById(int id) {

		return userDao.getUserById(id);
	}

	@Override
	public boolean modify(User user) {
		try {
			userDao.modify(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public int delete(Integer id) {
		int i = 0;
		try {
			i = userDao.deleteUserById(id);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public User selectUserByUserCode(String usercode) {
		// TODO Auto-generated method stub
		return userDao.selectUserByUserCode(usercode);
	}

	@Override
	public int updateWith(String pwd,Integer id) {
		// TODO Auto-generated method stub
		return userDao.updateWith(pwd,id);
	}

}
