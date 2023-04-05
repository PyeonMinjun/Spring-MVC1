package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {
    //    싱글톤이라서 작성이 안됨 -> spring이라면 자동으로 싱글톤을 제공하기때문에 작성 x
//    MemberRepository memberRepository = new MemberRepository();
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {     // 테스트가 끝나면 초기화
        memberRepository.clearStore();
    }


    @Test
    void save(){
        //given 이런게 주어졌을때
        Member member = new Member("hello",20);

        //when 이런걸 실행했을때
        Member saveMember = memberRepository.save(member);

        //then 결과가 이거여야 해!
        Member findMember = memberRepository.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);

    }
    @Test
    void findAll() {
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2); // 총 2개인가?
        assertThat(result).contains(member1, member2); // result안에 member1과 member2 가 있는가 테코


    }
}
