package com.sword.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sword.app.domain.OrderDomain;
import com.sword.app.domain.UserDomain;
import com.sword.app.service.OrderService;
import com.sword.app.service.UserService;
import com.sword.core.dto.JsonEntity;
import com.sword.core.utils.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Resource
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "redis")
    public void test(HttpServletRequest request) {

    }

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<UserDomain> doGetUser() throws Exception {
        JsonEntity<UserDomain> result = new JsonEntity<>();
        List<OrderDomain> all = orderService.getAll(0L);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "getIndexImages")
    public JSONArray getIndexImages() {
        JSONArray array = new JSONArray();
        // array.add("http://wx3.sinaimg.cn/large/006nLajtly1fkegnmnwuxj30dw0dw408.jpg");
        // array.add("http://pic3.16pic.com/00/16/45/16pic_1645919_b.jpg");
        // array.add("http://www.pptbz.com/pptpic/UploadFiles_6909/201211/2012111719294197.jpg");
        // array.add("http://img3.redocn.com/20100203/Redocn_2010020208535944.jpg");

        JSONObject object1 = new JSONObject();
        object1.put("url", "https://img.alicdn.com/tps/i4/TB17YNaArrpK1RjSZTESuwWAVXa.jpg_1080x1800Q90s50.jpg");
        array.add(object1);

        JSONObject object2 = new JSONObject();
        object2.put("url", "https://aecpm.alicdn.com/simba/img/TB1xuyJBjTpK1RjSZKPSuu3UpXa.jpg");
        array.add(object2);

        JSONObject object3 = new JSONObject();
        object3.put("url", "https://img.alicdn.com/tps/i4/TB1jJ3SAVzqK1RjSZFvSuwB7VXa.jpg_1080x1800Q90s50.jpg");
        array.add(object3);

        JSONObject object4 = new JSONObject();
        object4.put("url", "https://img.alicdn.com/tfs/TB1gVLZAAvoK1RjSZPfXXXPKFXa-990-360.jpg_1080x1800Q90s50.jpg");
        array.add(object4);

        return array;
    }

    @ResponseBody
    @RequestMapping(value = "testUploadFile", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public JsonEntity<String> testUploadFile(HttpServletRequest request) throws Exception {
        JsonEntity<String> result = new JsonEntity<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("=================");

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        System.out.println("=================");
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            String fileName = mf.getOriginalFilename();
            FileCopyUtils.copy(mf.getBytes(), new File("/Users/lijun/works/temp/"+Dates.format(new Date(), Dates.DATETIME_NOT_SEPARATOR)+".jpg"));
            System.out.println(fileName);
        }

        return result;
    }

}
