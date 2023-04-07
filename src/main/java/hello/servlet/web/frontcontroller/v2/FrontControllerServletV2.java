package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "FrontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }
    // 각패턴을 저장해 놓았음

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //  front-controller/v2/members 그대로 호출
        String requestURI = request.getRequestURI();// url이 그대로 들어온다.

        // MemberListControllerV2() 이 꺼내짐
        // 다형성으로 인하여 인터페이스로 받을 수 있음
        ControllerV2 controller = controllerMap.get(requestURI); // 각  URI에 맞게 객체 인스턴스가 반환이됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 만약 없으면 404 에러 페이지가 없거나 못찾을때 사용
            return;
        }
        //WEB-INF/views/new-form.jsp" 일때
        MyView view = controller.process(request, response);
        view.render(request,response);
    }
}
