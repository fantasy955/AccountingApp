package com.experiment.lenovo.accountingsoftware.common;

import com.experiment.lenovo.accountingsoftware.R;

public class Constants {
    public static String EXPEND = "0";
    public static String STRING_EXPEND = "支出";
    public static String INCOME = "1";
    public static String STRING_INCOME = "收入";
    public static String LOCAL_TIME_ZONE = "gmt";
    public static String PARAM_BILLRECORDS = "param0";
    public static int Greater = 1;
    public static int Smaller = -1;
    public static int Equal = 0;
    public static int UPDATE_BUDGET = 0;
    public static String INTENT_PARAM_BILLRECORD = "billrecord";
    public static String CANYIN = "餐饮";
    public static String[] billClassNames = {
            "餐饮","购物","日用","交通",
            "蔬菜","水果","零食","运动",
            "娱乐","通讯","服饰","美容",
            "住房","居家","孩子","长辈",
            "社交","旅行","烟酒","数码",
            "汽车","医疗","书籍","学习",
            "宠物","礼金","礼物","办公",
            "维修","捐赠","彩票","亲友",
            "快递"};
    public static int[] billClassImages = {R.drawable.billclass_01,R.drawable.billclass_02,R.drawable.billclass_03,R.drawable.billclass_04,R.drawable.billclass_05,R.drawable.billclass_06,R.drawable.billclass_07,
            R.drawable.billclass_08,R.drawable.billclass_09,R.drawable.billclass_10,R.drawable.billclass_11,R.drawable.billclass_12,R.drawable.billclass_13,R.drawable.billclass_14,R.drawable.billclass_15,
            R.drawable.billclass_16,R.drawable.billclass_17,R.drawable.billclass_18,R.drawable.billclass_19,R.drawable.billclass_20,R.drawable.billclass_21,R.drawable.billclass_22,R.drawable.billclass_23,
            R.drawable.billclass_24,R.drawable.billclass_25,R.drawable.billclass_26,R.drawable.billclass_27,R.drawable.billclass_28,R.drawable.billclass_29,R.drawable.billclass_30,
            R.drawable.billclass_31,R.drawable.billclass_32,R.drawable.billclass_33};

    public static String[] incomeClassNames = {
            "工资","兼职","理财","礼金",
            "其他"
    };
    public static int[] incomeClassImages = {
            R.drawable.incomeclass_01,R.drawable.incomeclass_02,R.drawable.incomeclass_03,R.drawable.incomeclass_04,
            R.drawable.incomeclass_05
    };

}
