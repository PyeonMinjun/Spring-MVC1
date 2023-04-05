package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //key = id , value = member
    private static long sequence = 0L; // sequence = 아이디가 1씩 증가 하는 것으로 사용

    private static final MemberRepository instance = new MemberRepository(); // 싱글톤으로 만든다.

    public static MemberRepository getInstance() { // 생성자를 막하주고 getInstance로만 접근을 해야함
        return instance;
    }

    private MemberRepository(){  // 싱글톤일때는 private로 생성자를 막아줌

    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 이렇게 작성하면 store의 모든값을 가져옴
    }

    public void clearStore() {
        store.clear();  // store을 모두 날려버림 -> test에서만 쓰임
    }




}
