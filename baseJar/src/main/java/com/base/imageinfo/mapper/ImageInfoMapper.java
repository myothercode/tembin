package com.base.imageinfo.mapper;

import com.base.utils.scheduleother.domain.ImageCheckVO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/17.
 * 有关图片查询的sql
 */
public interface ImageInfoMapper {
    public List<ImageCheckVO> queryCheckImage(Map map);
}
