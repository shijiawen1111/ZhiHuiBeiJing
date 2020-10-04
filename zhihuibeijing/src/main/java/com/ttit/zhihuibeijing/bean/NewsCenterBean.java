package com.ttit.zhihuibeijing.bean;

import java.util.List;

/**
 * Created by JW.S on 2020/9/27 6:56 PM.
 */
public class NewsCenterBean {

    /**
     * 新闻中心对应的模型对象
     * retcode : 200
     * data : [{"id":10000,"title":"新闻","type":1,"children":[{"id":10007,"title":"北京","type":1,"url":"/10007/list_1.json"},{"id":10006,"title":"中国","type":1,"url":"/10006/list_1.json"},{"id":10008,"title":"国际","type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"体育","type":1,"url":"/10010/list_1.json"},{"id":10091,"title":"生活","type":1,"url":"/10091/list_1.json"},{"id":10012,"title":"旅游","type":1,"url":"/10012/list_1.json"},{"id":10095,"title":"科技","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"军事","type":1,"url":"/10009/list_1.json"},{"id":10093,"title":"时尚","type":1,"url":"/10093/list_1.json"},{"id":10011,"title":"财经","type":1,"url":"/10011/list_1.json"},{"id":10094,"title":"育儿","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"汽车","type":1,"url":"/10105/list_1.json"}]},{"id":10002,"title":"专题","type":10,"url":"/10006/list_1.json","url1":"/10007/list1_1.json"},{"id":10003,"title":"组图","type":2,"url":"/10008/list_1.json"},{"id":10004,"title":"互动","type":3,"excurl":"","dayurl":"","weekurl":""}]
     * extend : [10007,10006,10008,10014,10012,10091,10009,10010,10095]
     */

    public int retcode;
    public List<NewsCenterMenuBean> data;
    public List<Integer> extend;

    public static class NewsCenterMenuBean {
        /**
         * 测滑菜单对应的分类数据
         * id : 10000
         * title : 新闻
         * type : 1
         * children : [{"id":10007,"title":"北京","type":1,"url":"/10007/list_1.json"},{"id":10006,"title":"中国","type":1,"url":"/10006/list_1.json"},{"id":10008,"title":"国际","type":1,"url":"/10008/list_1.json"},{"id":10010,"title":"体育","type":1,"url":"/10010/list_1.json"},{"id":10091,"title":"生活","type":1,"url":"/10091/list_1.json"},{"id":10012,"title":"旅游","type":1,"url":"/10012/list_1.json"},{"id":10095,"title":"科技","type":1,"url":"/10095/list_1.json"},{"id":10009,"title":"军事","type":1,"url":"/10009/list_1.json"},{"id":10093,"title":"时尚","type":1,"url":"/10093/list_1.json"},{"id":10011,"title":"财经","type":1,"url":"/10011/list_1.json"},{"id":10094,"title":"育儿","type":1,"url":"/10094/list_1.json"},{"id":10105,"title":"汽车","type":1,"url":"/10105/list_1.json"}]
         * url : /10006/list_1.json
         * url1 : /10007/list1_1.json
         * excurl :
         * dayurl :
         * weekurl :
         */

        public int id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String excurl;
        public String dayurl;
        public String weekurl;
        public List<NewsCenterNewsTabBean> children;

    }

    public static class NewsCenterNewsTabBean {
        /**
         * 新闻中心新闻tab的模型
         * id : 10007
         * title : 北京
         * type : 1
         * url : /10007/list_1.json
         */

        public int id;
        public String title;
        public int type;
        public String url;
    }
}
