package com.example.tools.FallDeteation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Detection
{

    public List<Double> Getdata(List<Acceleration> dataList)
    {
        // dataList是前端传过来的
        Acceleration data = null;
        Iterator<Acceleration> it = dataList.iterator();
        List<Double> accList = new ArrayList<Double>();

        while(it.hasNext())
        {
            data = (Acceleration) it.next();
            double acc = Math.sqrt(Math.pow(data.getAccx(), 2) + Math.pow(data.getAccy(), 2)
                    + Math.pow(data.getAccz(), 2)); // 得到加速度幅值
            accList.add(acc);
        }
        return accList;
    }

    @SuppressWarnings("unused")
    public boolean Detect(List<Acceleration> dataList)
    {
        boolean fall = false;
        /*
         * int num = 0;// while循环里 start = start + num * 100;// while循环里
         */
        List<Double> acclist = Getdata(dataList);
        // System.out.println("acclist大小" + acclist.size());
        // 阈值检测
        int i = 0;
        I: while(i < acclist.size())
        {
            if(acclist.get(i) < 5) // 失重
            {
                System.out.println("失重" + i);
                i++;
                J: while(i < acclist.size())
                {
                    if(fall == false)
                    {
                        if(acclist.get(i) > 30) // 碰撞
                        {
                            System.out.println("碰撞" + i);
                            // 判断2s后的数据
                            System.out.println("检测2s后的数据");
                            int j = 200;
                            while(j < acclist.size())
                            {
                                if((acclist.get(j) >= 8.5) && (acclist.get(j) <= 11.5)) // 身体水平
                                {
                                    // System.out.println("水平" + j);
                                    if(j == (acclist.size() - 1))
                                    {
                                        fall = true;
                                    }
                                    j++;
                                }
                                else
                                {
                                    System.out.println("没有跌倒");
                                    break I;
                                }
                            }
                            System.out.println("2s后的数据判断完成" + j);
                        }
                        else
                        {
                            // System.out.println("没有超过高阈值" + i);
                            i++;
                        }

                    }
                    else
                    { // 跌倒了，且全部都在阈值范围内
                        System.out.println("身体水平");
                        System.out.println("跌倒");
                        break I;
                    }
                }
                if(i == acclist.size()) // 峰值没有超过高阈值
                {
                    System.out.println("没有碰撞，没有跌倒");
                }
            }
            i++;

        }
        if(i == (acclist.size() - 1)) // 峰值没有超过低阈值
        {
            System.out.println("没有失重，没有跌倒");
        }
        return fall;
    }
}

