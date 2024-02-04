package com.zigaai;

import com.zigaai.mapper.UserMapper;
import com.zigaai.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = SystemApplication.class)
class ApiTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test11() {
        // IntStream.range(1, 1000)
        //         .forEach(i -> update());
        StopWatch sw = new StopWatch("mybatis update");
        for (int i = 0; i < 10; i++) {
            sw.start(String.valueOf(i));
            test();
            sw.stop();
        }

        System.out.println(sw.prettyPrint(TimeUnit.SECONDS));
        System.out.println(sw.shortSummary());
    }

    @Test
    public void test() {


        String uid = UUID.randomUUID().toString();
        String shortUid = uid.substring(0, 10);
        List<User> list = IntStream.range(0, 1000)
                .mapToObj(i -> {
                    User user = new User();
                    user.setId(i + 1L);
                    user.setUsername(shortUid);
                    user.setPassword(uid);
                    user.setSalt(uid);
                    user.setNickName(shortUid);
                    user.setRealName(uid);
                    user.setPhone(shortUid);
                    user.setAvatar(uid);
                    return user;
                })
                .collect(Collectors.toList());

        userMapper.updateBatchById(list);


        // IntStream.range(1, 100)
        //         .forEach(i -> {
        //             sw.start();
        //             update();
        //             sw.stop();
        //         });


    }

    @Test
    void update() {
        User user = new User();
        user.setId(1L);
        user.setSalt(String.valueOf(System.currentTimeMillis()));
        userMapper.updateById(user);
    }

    @Test
    void userMapperTest() {

        StopWatch sw = new StopWatch("jooq insert");
        // IntStream.range(0, 1000)
        //         .forEach(v -> insert());
        // IntStream.range(0, 100)
        //         .forEach(v -> {
        //             sw.start(String.valueOf(v));
        //             insert();
        //             sw.stop();
        //         });
        // System.out.println(sw.prettyPrint(TimeUnit.SECONDS));
        // System.out.println(sw.shortSummary());

        int size = 10000;
        List<User> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setUsername("user_" + i);
            user.setPassword("pass_" + i);
            user.setSalt("salt_" + i);
            user.setNickName("nick_" + i);
            user.setRealName("real_" + i);
            user.setPhone("phone_" + i);
            user.setAvatar("");
            user.setCreateTime(new Date());
            user.setIsDeleted(false);
            list.add(user);
        }
        for (int i = 0; i < 20; i++) {
            for (User user : list) {
                user.setId(null);
            }
            sw.start(String.valueOf(i));
            userMapper.insertBatch(list);
            sw.stop();
        }
        System.out.println(sw.prettyPrint(TimeUnit.SECONDS));
        System.out.println(sw.shortSummary());
    }

    void insert() {
        long i = System.currentTimeMillis();
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("pass_" + i);
        user.setSalt("salt_" + i);
        user.setNickName("nick_" + i);
        user.setRealName("real_" + i);
        user.setPhone("phone_" + i);
        userMapper.insert(user);
    }
}