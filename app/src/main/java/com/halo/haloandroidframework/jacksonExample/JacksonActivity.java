package com.halo.haloandroidframework.jacksonExample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by orson on 16/9/22.
 */

public class JacksonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectMapper mapper = new ObjectMapper();
        //解析时,忽略不存在的字段,默认时严格匹配的
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //1.根据数据的来源,解析的时候可以采用下面几种方式
        try {
            //从数据文件中
            MyValue  value = mapper.readValue(new File("data.json"), MyValue.class);
            //从网络获取
            value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
            //直接解析json字符串
            value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //2.还可以反向序列化一个对象到json字符串,也有几种方式
        try {
            MyValue myResultObject = new MyValue();
            //直接输出到文件
            mapper.writeValue(new File("result.json"), myResultObject);
            //序列化成字节数组
            byte[] jsonBytes = mapper.writeValueAsBytes(myResultObject);
            //序列换成json字符串
            String jsonString = mapper.writeValueAsString(myResultObject);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对json数组进行解析
        //Map<String, Integer> scoreByName = mapper.readValue(jsonSource, Map.class);
        //List<String> names = mapper.readValue(jsonSource, List.class);

        //对数组序列化
        //mapper.writeValue(new File("names.json"), names);
    }
}
