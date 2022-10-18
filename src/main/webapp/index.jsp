<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="rows" class="exdo.beans.RowsBean" scope="session"/>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Lab №2</title>
    <link rel="stylesheet" href="styles/style.css">
</head>

<body>

<div id="hat">
    <div id="itmo">ИТМО</div>
    <div id="name">Зуев Никита Александрович</div>
    <div id="group">Группа: P32111</div>
    <div id="variant">Вариант: 112322</div>
</div>

<div id="interact">
    <div id="form-div">
        <form id="form">
            <div id="radio-container">
                <p>Величина X</p>
                <input type="radio" name="X-value" value="-2" id="x1">
                <label for="x1">-2</label>
                <input type="radio" name="X-value" value="-1.5" id="x2">
                <label for="x2">-1.5</label>
                <input type="radio" name="X-value" value="-1" id="x3">
                <label for="x3">-1</label>
                <input type="radio" name="X-value" value="-0.5" id="x4">
                <label for="x4">-0.5</label>
                <input type="radio" name="X-value" value="0" id="x5">
                <label for="x5">0</label>
                <input type="radio" name="X-value" value="0.5" id="x6">
                <label for="x6">0.5</label>
                <input type="radio" name="X-value" value="1" id="x7">
                <label for="x7">1</label>
                <input type="radio" name="X-value" value="1.5" id="x8">
                <label for="x8">1.5</label>
                <input type="radio" name="X-value" value="2" id="x9">
                <label for="x9">2</label>
            </div>
            <div id="text-container">
                <p>Координаты по Y</p>
                <input name="Y-value" type="text" placeholder="-3..3">
            </div>
            <div id="checkbox-container">
                <p>Координаты по R</p>
                <input class="checkbox-input" type="checkbox" name="R-value" id="r1" value="1">
                <label for="r1">1</label>
                <input class="checkbox-input" type="checkbox" name="R-value" id="r2" value="1.5">
                <label for="r2">1.5</label>
                <input class="checkbox-input" type="checkbox" name="R-value" id="r3" value="2">
                <label for="r3">2</label>
                <input class="checkbox-input" type="checkbox" name="R-value" id="r4" value="2.5">
                <label for="r4">2.5</label>
                <input class="checkbox-input" type="checkbox" name="R-value" id="r5" value="3">
                <label for="r5">3</label>
            </div>
            <input class="submit-button" type="submit" value="Отправить">
        </form>
    </div>


    <div id="picture">
        <%--        <img src="img/graph.png" width="400px" height="400px" alt="The picture must be here">--%>
        <canvas id="canvas" width="400px" height="400px">
            Aaah, canvas is not supported!
        </canvas>
    </div>


</div>

<div id="table_container">
    <div class="table_row" name="head">
        <div class="table_cell">Результат</div>
        <div class="table_cell">R</div>
        <div class="table_cell">X</div>
        <div class="table_cell">Y</div>
        <div class="table_cell">Текущее время</div>
        <div class="table_cell">Время исполнения</div>
    </div>

    <div id="table">
        <c:forEach var="row" items="${rows.rows}">
            <div class="table_row" value="${row.hitResult}">
                <div class="table_cell">${row.hitResult?"Попал(а)":"Не попал(а)"}</div>
                <div class="table_cell">${row.rValue}</div>
                <div class="table_cell">${row.xValue}</div>
                <div class="table_cell">${row.yValue}</div>
                <div class="table_cell">${row.currentTime}</div>
                <div class="table_cell">${row.executionTime}</div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
<script src="js/form_handler.js"></script>

</html>