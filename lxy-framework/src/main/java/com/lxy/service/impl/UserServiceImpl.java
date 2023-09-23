package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.UserDto;
import com.lxy.domain.dto.UserUpdateDto;
import com.lxy.domain.entity.Role;
import com.lxy.domain.entity.User;
import com.lxy.domain.entity.UserRole;
import com.lxy.domain.vo.PageVo;
import com.lxy.domain.vo.UserInfoVo;
import com.lxy.domain.vo.UserRoleVo;
import com.lxy.domain.vo.UserVo;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.mapper.UserMapper;
import com.lxy.service.RoleService;
import com.lxy.service.UserRoleService;
import com.lxy.service.UserService;
import com.lxy.utils.BeanCopyUtils;
import com.lxy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-21 21:05:21
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    //后台查询所有用户

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, UserDto userDto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userDto.getUserName()),User::getUserName,userDto.getUserName())
                .eq(StringUtils.hasText(userDto.getPhonenumber()),User::getPhonenumber,userDto.getPhonenumber())
                .eq(StringUtils.hasText(userDto.getStatus()), User::getStatus, userDto.getStatus());
        Page<User> page = new Page(pageNum,pageSize);
        page(page,wrapper);
        List<User> records = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(records, UserVo.class);
        PageVo pageVo = new PageVo(userVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    //后台添加用户

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult addUser(UserDto userDto) {

        User user = BeanCopyUtils.copyBean(userDto, User.class);

        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);

        List<UserRole> userRoles = userDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(),roleId))
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeUser(List<Long> id) {
        for(Long userId : id){
            removeById(userId);
        }
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getUser(Long id) {
        //获取用户所关联的角色id列表
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoles = userRoleService.list(userRoleLambdaQueryWrapper);
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        //获取所有角色的列表
        List<Role> roles = roleService.list();
        //获取用户信息
        UserVo userVo = BeanCopyUtils.copyBean(getById(id), UserVo.class);
        UserRoleVo userRoleVo = new UserRoleVo(roleIds, roles, userVo);
        return ResponseResult.okResult(userRoleVo);
    }

    @Override
    public ResponseResult updateUser(UserUpdateDto userUpdateDto) {
        User user = BeanCopyUtils.copyBean(userUpdateDto, User.class);

        //对数据进行非空判断
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(wrapper);
        List<UserRole> userRoles = userUpdateDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }


    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }


    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
}
