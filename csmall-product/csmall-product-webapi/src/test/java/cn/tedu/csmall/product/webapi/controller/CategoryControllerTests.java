package cn.tedu.csmall.product.webapi.controller;

import cn.tedu.csmall.common.web.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("classpath:truncate.sql")
    public void testAddNewSuccessfully() throws Exception {
        // 准备测试数据，不需要封装，应该全部声明为String类型
        String name = "水果";
        String parentId = "0"; // 即使目标类型是Long，参数值也不要加L
        String keywords = "水果的关键字是啥";
        String sort = "66";
        String icon = "图标待定";
        String isDisplay = "1";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/add-new";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.post(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("name", name) // 请求参数，有多个时，多次调用param()方法
                                .param("parentId", parentId)
                                .param("keywords", keywords)
                                .param("icon", icon)
                                .param("sort", sort)
                                .param("isDisplay", isDisplay)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(20000)) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testAddNewFailBecauseNameDuplicate() throws Exception {
        // 准备测试数据，不需要封装，应该全部声明为String类型
        String name = "类别001";
        String parentId = "0"; // 即使目标类型是Long，参数值也不要加L
        String keywords = "水果的关键字是啥";
        String sort = "66";
        String icon = "图标待定";
        String isDisplay = "1";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/add-new";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.post(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("name", name) // 请求参数，有多个时，多次调用param()方法
                                .param("parentId", parentId)
                                .param("keywords", keywords)
                                .param("icon", icon)
                                .param("sort", sort)
                                .param("isDisplay", isDisplay)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NAME_DUPLICATE.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    public void testAddNewFailBecauseParentNotFound() throws Exception {
        // 准备测试数据，不需要封装，应该全部声明为String类型
        String name = "类别001";
        String parentId = "-1"; // 即使目标类型是Long，参数值也不要加L
        String keywords = "水果的关键字是啥";
        String sort = "66";
        String icon = "图标待定";
        String isDisplay = "1";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/add-new";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.post(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("name", name) // 请求参数，有多个时，多次调用param()方法
                                .param("parentId", parentId)
                                .param("keywords", keywords)
                                .param("icon", icon)
                                .param("sort", sort)
                                .param("isDisplay", isDisplay)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    public void testAddNewFailBecauseBadRequest() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String parentId = "0"; // 即使目标类型是Long，参数值也不要加L
        String keywords = "水果的关键字是啥";
        String sort = "66";
        String icon = "图标待定";
        String isDisplay = "1";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/add-new";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.post(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                // .param("name", name) // 注意：此处不提交必要的name属性
                                .param("parentId", parentId)
                                .param("keywords", keywords)
                                .param("icon", icon)
                                .param("sort", sort)
                                .param("isDisplay", isDisplay)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_BAD_REQUEST.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testListByParentId() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String parentId = "0";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/list-by-parent";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("parentId", parentId)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetDetailsByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/" + id;
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetDetailsByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "9999999999";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/" + id;
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    /**
     * 6.26作业
     **/
    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/disable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/disable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdFailBecauseIsDisable() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "16"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/disable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_IS_DISABLE.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "16"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/enable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/enable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdFailBecauseIsEnable() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/enable";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_IS_ENABLE.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/notDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/notDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdFailBecauseIsNotDisplay() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "16"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/notDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_IS_NOT_DISPLAY.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "16"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/isDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/isDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdFailBecauseIsIsDisplay() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/isDisplay";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_IS_IS_DISPLAY.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "6"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/delete";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/delete";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdFailBecauseIsParent() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1"; // 即使目标类型是Long，参数值也不要加L
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/delete";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_DELETE.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateByIdSuccessfully() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1";
        String name = "YJ";
        String icon = "干杯";
        String sort = "233";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/update";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .param("name", name)
                                .param("icon", icon)
                                .param("sort", sort)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.OK.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateByIdFailBecauseNotFound() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "-1";
        String name = "YJ";
        String icon = "干杯";
        String sort = "233";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/update";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .param("name", name)
                                .param("icon", icon)
                                .param("sort", sort)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateByIdFailBecauseNameDuplicate() throws Exception {
        // 准备测试数据，注意：此次没有提交必要的name属性值
        String id = "1";
        String name = "类别002";
        String icon = "干杯";
        String sort = "233";
        // 请求路径，不需要写协议、服务器主机和端口号
        String url = "/categories/update";
        // 执行测试
        // 以下代码相对比较固定
        mockMvc.perform( // 执行发出请求
                        MockMvcRequestBuilders.get(url) // 根据请求方式决定调用的方法
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 请求数据的文档类型，例如：application/json; charset=utf-8
                                .param("id", id)
                                .param("name", name)
                                .param("icon", icon)
                                .param("sort", sort)
                                .accept(MediaType.APPLICATION_JSON)) // 接收的响应结果的文档类型，注意：perform()方法到此结束
                .andExpect( // 预判结果，类似断言
                        MockMvcResultMatchers
                                .jsonPath("state") // 预判响应的JSON结果中将有名为state的属性
                                .value(State.ERR_CATEGORY_NAME_DUPLICATE.getValue())) // 预判响应的JSON结果中名为state的属性的值，注意：andExpect()方法到此结束
                .andDo( // 需要执行某任务
                        MockMvcResultHandlers.print()); // 打印日志
    }

}
