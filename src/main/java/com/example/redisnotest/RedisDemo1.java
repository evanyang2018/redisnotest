package com.example.redisnotest;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@RestController
public class RedisDemo1 {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void before(){
        //修改key序列化工具，防止redis中的key带有"\xac\xed\x00\x05t\x00\x04"前缀
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //修改value序列化工具 防止increment()的时候出现错误   hash value is not an integer
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        System.out.println("=================================");
    }

    @RequestMapping("/setValue")
    public String setValue(){
        redisTemplate.opsForHash().put("Num1","num2",123L);
        return "success";
    }

    @RequestMapping("/getValue")
    public String getValue(){
        System.out.println(redisTemplate.opsForHash().get("Num1","num2"));
        return "success";
    }

    @RequestMapping("/getIncr")
    public String getIncr(){
        System.out.println(redisTemplate.opsForHash().get("Num1","num2"));
        System.out.println(redisTemplate.opsForHash().increment("Num1","num2",1L));
        return "success";
    }


    @RequestMapping("/getClients")
    /**获得客户端列表 */
    public String getClients(){
        if (redisTemplate.hasKey("num")){
            return "success";
        }else{
            return "false";
        }

    }


    /**
     * @Author  Evan Yang
     *
     *  ……………………唯一编号规则定义接口………………………………
     *  ************ 规则无法修改，只能停用 启用********
     * @param numRule 编码生成规则  包含：
     *  {PARAM(xxx)} 固定内容xxx  可以为字母、符号（“-”，“/”等）、数字
     *  {DATE(YYYYMMDD)} 日期  YYYY 年份  MM 月份  DD 日期
     *  {NO(x)} 递增数字 x为位数  如果包含多个数字规则 会采取将多个数字规则定义为一个 然后切分
     *   eg: {PARAM(BF)}{DATE(YYYYMMDD)}{NO(4)} ==> BF201806220001
     *       {PARAM(BF)}{DATE(YYYYMMDD)}{PARAM(AD)}{NO(4)} ==> BF20180622AD0001
     *
     *  @param zeroSet 是否每天都进行重置
     *  @param ruleCode 规则编码
     *  @param ruleName 规则名称
     *  @param ruleRemark  规则备注
     *   创建人  创建时间  启用状态
     */
    @RequestMapping("setNumRule")
    public String setNumRule(@RequestParam("numRule")String numRule,@RequestParam("zeroSet")String zeroSet, @RequestParam("ruleCode")String ruleCode,
                             @RequestParam("ruleName")String ruleName,@RequestParam("ruleRemark")String ruleRemark){

        String[] numRuleParams = numRule.split("}");


        return "";
    }



}
