package org.ivan.api.file;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.BaseService;
import org.ivan.entity.file.FilmComment;

/**
 * @author cyl
 * @version 
 */
public interface FilmCommentService extends BaseService<FilmComment>  {
	
	public List<FilmComment> getListMap(Map<String, Object> map);
}
