const form = document.getElementById('form');
const rInPixels = 162.5;
var canvas = document.getElementById("canvas");
var checkedCount = 0;

renderCanvas();

function renderCanvas() {
    const ctx = canvas.getContext("2d");
    const img = new Image();

    img.src = "/web2/img/graph.png";
    img.onload = function () {
        ctx.drawImage(img, 0, 0, 400, 400);
        drawAllDots()
    }
}

function drawPoint(x, y, color) {
    const ctx = canvas.getContext("2d");
    ctx.beginPath();
    ctx.arc(x, y, 5, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
}


function drawAllDots() {
    const rows = document.getElementsByClassName("table_row");
    for (let i = 1; i < rows.length; i++) {
        const cells = rows[i].getElementsByClassName("table_cell");
        const x = parseFloat(cells[2].innerHTML);
        const y = parseFloat(cells[3].innerHTML);
        const r = parseFloat(cells[1].innerHTML);
        const result = rows[i].getAttribute("value");
        const color = (result === "true" ? "green" : "red");
        console.log(x, y, r, result, color);
        drawPoint(x * rInPixels / r + 200, 200 - y * rInPixels / r, color);
    }
}

canvas.addEventListener('click', function (event) {
    let r = getR();
    if (!validateR(r)) {
        return;
    }
    let numericR = parseFloat(r);

    const possibleX = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

    const x = event.offsetX;
    const y = event.offsetY;

    const xInArea = (x - 200) / rInPixels * numericR;
    let yInArea = (200 - y) / rInPixels * numericR;

    const closestX = possibleX.reduce(function (prev, curr) {
            return (Math.abs(curr - xInArea) < Math.abs(prev - xInArea) ? curr : prev);
        }
    );
    if (yInArea > 3) {
        yInArea = 3;
    } else if (yInArea < -3) {
        yInArea = -3;
    } else {
        yInArea = Math.round(yInArea * 1000) / 1000;
    }
    sendAndGetData(closestX, yInArea, numericR);
});

form.addEventListener('click', function (event) {
        if (event.target.type === 'checkbox') {

            if (checkedCount === 0) {
                checkedCount++;
            } else {
                if (!event.target.checked) {
                    checkedCount--;
                } else {
                    event.target.checked = false;
                }
            }
        }
    }
)


function checkRcount() {
    const x = document.getElementsByName("X-value");
    let count = 0;
    for (let i = 0; i < x.length; i++) {
        if (x[i].checked) {
            count++;
        }
    }
    if (count === 1) {
        return count;
    }

}

function getX() {
    const x = document.getElementsByName("X-value");
    for (let i = 0; i < x.length; i++) {
        if (x[i].checked) {
            return x[i].value;
        }
    }

}

function getR() {
    const r = document.getElementsByName("R-value");
    for (let i = 0; i < r.length; i++) {
        if (r[i].checked) {
            return r[i].value;
        }
    }
}

function getY() {
    return document.getElementsByName('Y-value')[0].value;
}

function validateY(y) {
    if (y === '') {
        alert("Введите значение Y!");
        return false;
    }
    if (y.length > 20) {
        alert("Слишком много цифр в поле Y!")
        return;
    }
    y = eraseFirstZeros(y);
    if (y.length > 10) {
        alert("Слишком много цифр после запятой в поле Y!")
        return;
    }
    if (isNaN(y)) {
        alert("Y должен быть числом!");
        return false;
    }
    if (y < -3 || y > 3) {
        alert("Y должен быть в диапазоне [-3;3]");
        return false;
    }
    return true;
}

function validateX(x) {
    if (x === undefined) {
        alert("Выберите значение X!");
        return false;
    }
    return true;
}

function validateR(r) {
    if (r === undefined) {
        alert("Выберите значение R!");
        return false;
    }
    return true;
}

function eraseFirstZeros(str) {
    return str.replace(',', '.').replace(/^0+(?!\.|$)/, '');
}

form.addEventListener('submit', function (event) {
        event.preventDefault();
        let x = getX();
        let y = getY();
        let r = getR();
        if (validateX(x) && validateY(y) && validateR(r)) {
            y = eraseFirstZeros(y);
            console.log("x = " + x + " y = " + y + " r = " + r);
            sendAndGetData(x, y, r);
        }
    }
)


function sendAndGetData(x, y, r) {
    const request = new XMLHttpRequest();

    request.open('GET', '?x=' + x + '&y=' + y + '&r=' + r, true);
    request.send();

    request.onload = function () {
        if (request.status === 200) {
            console.log("response received");
            document.getElementById('table').innerHTML = request.responseText + document.getElementById('table').innerHTML;
            drawAllDots();
        }
    }
}

