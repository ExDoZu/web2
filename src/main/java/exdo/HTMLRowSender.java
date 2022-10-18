package exdo;

import exdo.beans.RowBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLRowSender {
    public static void sendRow(HttpServletResponse response, RowBean row) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("<div class=\"table_row\" value=\"" + row.isHitResult() + "\">\n" +
                "<div class=\"table_cell\">" + (row.isHitResult() ? "Попал(а)" : "Не попал(а)") + "</div>\n" +
                "<div class=\"table_cell\">" + row.getrValue() + "</div>\n" +
                "<div class=\"table_cell\">" + row.getxValue() + "</div>\n" +
                "<div class=\"table_cell\">" + row.getyValue() + "</div>\n" +
                "<div class=\"table_cell\">" + row.getCurrentTime() + "</div>\n" +
                "<div class=\"table_cell\">" + row.getExecutionTime() + "</div>\n" +
                "</div>");
        printWriter.flush();
        printWriter.close();
    }

}
