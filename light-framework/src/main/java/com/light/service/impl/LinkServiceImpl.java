package com.light.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.constants.SystemConstants;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Link;
import com.light.mapper.LinkMapper;
import com.light.service.LinkService;
import com.light.utils.BeanCopyUtils;
import com.light.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-01 17:48:24
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links=list(queryWrapper);
        //转换成VO
        List<LinkVo> linkVos=BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

