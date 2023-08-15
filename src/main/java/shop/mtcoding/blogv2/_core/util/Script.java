package shop.mtcoding.blogv2._core.util;

// 오버로딩 - 매개변수 위치를 잘 고려하기
// JS
public class Script {

    // 경고창 + 뒤로가기
    public static String back(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        // 변수넣기
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }

    // 경고창 + 이동(redirect)
    public static String href(String url, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.herf='" + url + "';");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }

    // 이동(redirect)
    public static String href(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }
}
