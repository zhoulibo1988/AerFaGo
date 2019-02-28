package org.ivan.api.file;

import java.util.List;

import org.ivan.api.weixin.BaseService;
import org.ivan.entity.file.FilmCollection;

/**
 * @author cyl
 * @version 
 */
public interface FilmCollectionService extends BaseService<FilmCollection>  {
	public List<FilmCollection> getList(FilmCollection t);
}
