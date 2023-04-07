package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "FrontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }
    // 각패턴을 저장해 놓았음

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");


        //  front-controller/v1/members 그대로 호출
        String requestURI = request.getRequestURI();// url이 그대로 들어온다.

        // MemberListControllerV1() 이 꺼내짐
        // 다형성으로 인하여 인터페이스로 받을 수 있음
        ControllerV1 controller = controllerMap.get(requestURI); // 각  URI에 맞게 객체 인스턴스가 반환이됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 만약 없으면 404 에러 페이지가 없거나 못찾을때 사용
            return;
        }

        controller.process(request,response);
    }
}
