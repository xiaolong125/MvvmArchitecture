package com.turdroid.library.network.parser;


import com.turdroid.library.network.model.ApiResponse;

import org.jetbrains.annotations.NotNull;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import java.io.IOException;
import java.lang.reflect.Type;

@Parser(name = "Response")
public class ResponseParser<T> extends AbstractParser<T> {

    //注意，以下两个构造方法是必须的
    protected ResponseParser() { super(); }
    public ResponseParser(Type type) { super(type); }

    @Override
    public T onParse(@NotNull okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(ApiResponse.class, mType); //获取泛型类型
        ApiResponse<T> data = convert(response, type);
        T t = data.getData(); //获取data字段
        if (data.getCode() != 200) {//这里假设code不等于200，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
        }
        return t;
    }

    public static <T> ResponseParser<T> get(Type type){
        return new ResponseParser<>(type);
    }
}

