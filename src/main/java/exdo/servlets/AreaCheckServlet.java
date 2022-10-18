package exdo.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import exdo.HTMLRowSender;
import exdo.beans.RowBean;
import exdo.beans.RowsBean;

@WebServlet(name = "AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.nanoTime();

        String xString = request.getParameter("x");
        String yString = request.getParameter("y").replace(',', '.');
        String rString = request.getParameter("r");
        boolean isValuesValid = validateValues(xString, yString, rString);

        if (isValuesValid) {
            double xValue = Double.parseDouble(xString);
            double yValue = Double.parseDouble(yString);
            double rValue = Double.parseDouble(rString);
            boolean isHit = checkHit(xValue, yValue, rValue);

            Date time = new Date();
            String currentTime = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(time);

            String executionTime = String.format(Locale.US, "%.6f", (System.nanoTime() - startTime) / 1000000000.0);
            RowsBean rows = (RowsBean) request.getSession().getAttribute("rows");
            if (rows == null) rows = new RowsBean();
            RowBean newRow = new RowBean(xValue, yValue, rValue, currentTime, executionTime, isHit);
            rows.getRows().add(0, newRow);
            request.getSession().setAttribute("rows", rows);
            HTMLRowSender.sendRow(response, newRow);
        } else {
            response.sendError(400, "Invalid values");
        }


    }

    private boolean validateX(String xString) {
        try {
            List<Double> xRange = List.of(-2.0, -1.5, -1.0, -0.5, 0d, 0.5, 1.0, 1.5, 2.0);
            double xValue = Double.parseDouble(xString);
            return xRange.contains(xValue);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateY(String yString) {
        try {
            double yValue = Double.parseDouble(yString);
            return yValue >= -3 && yValue <= 3;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateR(String rString) {
        try {
            List<Double> rRange = List.of(1.0, 1.5, 2.0, 2.5, 3.0);
            double rValue = Double.parseDouble(rString);
            return rRange.contains(rValue);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validateValues(String xString, String yString, String rString) {
        return validateX(xString) && validateY(yString) && validateR(rString);
    }

    private boolean checkTriangle(double xValue, double yValue, double rValue) {
        return xValue <= 0 && yValue <= 0 && yValue >= -2 * xValue - rValue;
    }

    private boolean checkRectangle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue >= 0 && yValue <= rValue / 2 && xValue <= rValue;
    }

    private boolean checkCircle(double xValue, double yValue, double rValue) {
        return xValue <= 0 && yValue >= 0 && Math.sqrt(xValue * xValue + yValue * yValue) <= rValue / 2;
    }

    private boolean checkHit(double xValue, double yValue, double rValue) {
        return checkTriangle(xValue, yValue, rValue) || checkRectangle(xValue, yValue, rValue) ||
                checkCircle(xValue, yValue, rValue);
    }
}
