package com.ivan.dubbo.dao.film;

import java.util.List;
import java.util.Map;

import org.ivan.entity.file.FilmComment;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author cyl
 * @version 
 */
public interface FilmCommentMapper extends BaseMapper<FilmComment>{
	public List<FilmComment> getListMap(Map<String, Object> map);
}