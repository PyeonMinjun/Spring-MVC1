package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "FrontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }
    // 각패턴을 저장해 놓았음

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //  front-controller/v3/members 그대로 호출
        String requestURI = request.getRequestURI();// url이 그대로 들어온다.

        // MemberListControllerV3() 이 꺼내짐
        // 다형성으로 인하여 인터페이스로 받을 수 있음
        ControllerV3 controller = controllerMap.get(requestURI); // 각  URI에 맞게 객체 인스턴스가 반환이됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 만약 없으면 404 에러 페이지가 없거나 못찾을때 사용
            return;
        }

        //paramMap 을 꺼내줘야함
        Map<String, String> paramMap = creatgeParamMap(request);

        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();// 논리이름 new-form
//        /WEB-INF/views/new-form.jsp로 변환
        MyView view = viewResolver(viewName);


        view.render(mv.getModel(),request,response);
    }
    private static Map<String, String> creatgeParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}
