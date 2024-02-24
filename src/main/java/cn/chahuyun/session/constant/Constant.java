package cn.chahuyun.session.constant;

import cn.chahuyun.session.data.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量
 *
 * @author Moyuyanli
 * @date 2024/1/3 13:36
 */
public class Constant {

    /**
     * 作用域类型的单参数集合
     */
    public static final List<Scope.Type> SCOPE_TYPE_SINGLE_PARAMETER = new ArrayList<>() {{
        add(Scope.Type.GLOBAL_USER);
        add(Scope.Type.GROUP);
        add(Scope.Type.LIST);
        add(Scope.Type.USERS);
    }};

    /**
     * 作用域类型的单参数集合<br>
     * 单参类型为 long
     */
    public static final List<Scope.Type> SCOPE_TYPE_SINGLE_PARAMETER_LONG = new ArrayList<>() {{
        add(Scope.Type.GLOBAL_USER);
        add(Scope.Type.GROUP);
    }};
    /**
     * 作用域类型的单参数集合<br>
     * 单参类型为 String
     */
    public static final List<Scope.Type> SCOPE_TYPE_SINGLE_PARAMETER_STRING = new ArrayList<>() {{
        add(Scope.Type.LIST);
        add(Scope.Type.USERS);
    }};




}
