package ${basepackage}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${basepackage}.service.BaseService;
import ${basepackage}.util.PageObject;
import ${basepackage}.util.ReMessage;

public class BaseController<T, E> {

    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    private BaseService<T, E> baseService;

    public void setBaseService(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    /**
     * ===== 接受的数据是 MAP use for json =====
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> query(@RequestParam Map<String, Object> map) {
        try {
            List<T> list = baseService.query(map);
            return ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, list);
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("Server request exception !", e);
            return ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> detail(@RequestParam Map<String, Object> map) {
        try {
            return ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, baseService.detail(map));
        } catch (Exception e) {
            return ReMessage.resultBack(ParameterEunm.ERROR_500_CODE, null);
        }
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> del(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        try {
            baseService.delete(map);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("Server request error!" ,e);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        }
        return resultMap;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> add(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        try {
            baseService.add(map);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("Server request error!" ,e);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        }
        return resultMap;
    }

    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> update(@RequestParam Map<String, Object> map) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        try {
            baseService.update(map);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("Server request error!" ,e);
            resultMap=ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
        }
        return resultMap;
    }

    @RequestMapping(value = "/pagequery", method = RequestMethod.POST)
    public @ResponseBody PageObject<T> PageQuery(@RequestParam Map<String, Object> map) {
        PageObject<T> pageObject = null;
        try {
            pageObject = baseService.Pagequery(map);
            LOGGER.debug(map);
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("Server request exception !", e);
        }
        return pageObject;
    }
	
}
