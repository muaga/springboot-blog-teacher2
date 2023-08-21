package shop.mtcoding.blogv2;

class MyController {
    // Controller를 호출하는 것은 DS
    // 그래서 Controller에서 오류책임전가를 넘기는 쪽은 DS이다.
    MyService myService = new MyService();

    void home(boolean check) {
        myService.홈가기(check);
    }
}

class MyService {
    MyRepository myRepository = new MyRepository();

    void 홈가기(boolean check) {
        myRepository.findHome(check);
    }
}

class MyRepository {
    void findHome(boolean check) {
        if (check) {
            System.out.println("조회 완료");
        } else {
            throw new RuntimeException("조회 오류");
            // throw : 예외 미루기

            // 메소드 속 throw : 강제로 오류를 터트린다.

            // 메소드 자체의 throws
            // 메소드 내부 전체를 try-catch 한 것

            // ------------ 특정한 코드에 대한 오류를 잡고 싶을 때 -> throw
            // ------------ 정확한 위치 없이 메소드 전체에 대해 오류를 잡고 싶을 때 -> throws

            // 빨간줄X : RuntimeException : JVM 실행 중, 터질 수 있고 없고의 모든 오류의 부모
            // 빨간줄O : ComplieException : JVM 실행 전, 터질 수 있는 오류
            // 빨간줄O : Exception : 무조건 터질 오류를 말하는 것이기 때문에 try-catch로 오류가
            // 터졌을 때(catch)를 안내해야 한다.

            // 빨간줄의 유무를 확인하는 방법
            // findHome의 메소드가 Exception이면, Service에서
            // 알고나면 try-catch로 잡아야 한다
        }
    }
}

public class HelloTest {
    public static void main(String[] args) {
        MyController myController = new MyController();
        myController.home(true);
    }
    // 예외 책임 전가
    // Add throws declaration
}