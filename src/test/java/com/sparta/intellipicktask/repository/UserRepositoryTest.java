package com.sparta.intellipicktask.repository;

import com.sparta.intellipicktask.entity.User;
import com.sparta.intellipicktask.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRepositoryTest {
    //빈 주입
    @Autowired
    private UserRepository userRepository;

    @Test
    void addUser() throws Exception {
        //given
        User user = new User(
                "test11",
                "test1",
                "test12",
                "test@email.com",
                UserRoleEnum.USER);
        //when
        User savedUser = userRepository.save(user);
        //then 세이브시킨유저와 기존유저의 이름을 비교테스트
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }
}
