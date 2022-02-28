package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.functionalInterface.FunctionalInterfaceTest;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class helloController extends BaseController{

    @RequestMapping("/index")
    public String Hello(){
        return "index";
    }

    @RequestMapping("/intList")
    public void Demo(HttpServletRequest request, HttpServletResponse response){
        List<Integer> list = new ArrayList<>();
        list.add(99);
        list.add(52);
        list.add(88);
        list.add(1);
        int sum = list.stream().mapToInt(Employee->Employee).sum();
        int max = list.stream().mapToInt(Employee->Employee).max().getAsInt();
        int min = list.stream().mapToInt(Employee->Employee).min().getAsInt();
        System.out.println(sum);
        System.out.println(max);
        System.out.println(min);

        list.stream().forEach(System.out::print);

        //1.简化参数类型，可以不写参数类型，但是必须所有参数都不写
        FunctionalInterfaceTest.NoReturnMultiParam lamdba1 = (a, b) -> {
            System.out.println("简化参数类型");
        };
        lamdba1.method(1, 2);

        //2.简化参数小括号，如果只有一个参数则可以省略参数小括号
        FunctionalInterfaceTest.NoReturnOneParam lambda2 = a -> {
            System.out.println("简化参数小括号");
        };
        lambda2.method(1);

        //3.简化方法体大括号，如果方法条只有一条语句，则可以胜率方法体大括号
        FunctionalInterfaceTest.NoReturnNoParam lambda3 = () -> System.out.println("简化方法体大括号");
        lambda3.method();

        //4.如果方法体只有一条语句，并且是 return 语句，则可以省略方法体大括号
        FunctionalInterfaceTest.ReturnOneParam lambda4 = a -> a+3;
        System.out.println(lambda4.method(5));

        FunctionalInterfaceTest.ReturnMultiParam lambda5 = (a, b) -> a+b;
        System.out.println(lambda5.method(1, 1));


        Thread t = new Thread(() -> {

        });


        Function <String, Integer> fun = Integer::parseInt;
        fun.apply("");


        writerJson(response, list);
    }

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        List<Map> mapList = new ArrayList<Map>();

        Map<String, String> m1 = new HashMap<>();
        m1.put("a", "a1");
        m1.put("b", "b1");
        Map<String, String> m2 = new HashMap<>();
        m2.put("a", "a2");
        m2.put("b", "b2");
        mapList.add(m1);
        mapList.add(m2);

        mapList.forEach(item ->{
            json.put("a", item.get("a"));
            json.put("b", item.get("b"));
            if("a2".equals(item.get("a"))){
                System.out.println(item.get("a"));
            }else{
                item.remove("a", item.get("a"));
            }
        });
        System.out.println(mapList);

    }

}
