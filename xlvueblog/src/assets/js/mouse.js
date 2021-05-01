const canvas = document.getElementById('mouse');
const ctx = canvas.getContext('2d');
const WIDTH = canvas.width = document.documentElement.clientWidth;
const HEIGHT = canvas.height = document.documentElement.clientHeight;

function hexColor() {
    let str = '#';
    let arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F'];
    for (let i = 0; i < 6; i++) {
        let index = Number.parseInt(Math.random() * 16);
        str += arr[index]
    }
    return str;
}

let mouseX;
let mouseY;
let arr = [];

window.onmousemove = function (e) {
    mouseX = e.clientX;
    mouseY = e.clientY;
    arr.push({
        x: mouseX,
        y: mouseY,
        r: 0.9,
        s: 1     // 控制⚪消失
    })

};

function animate() {
    ctx.clearRect(0, 0, WIDTH, HEIGHT);
    let step=0.9;
    let s=0.09;
    for (let i = 0; i < arr.length; i++) {
        ctx.beginPath();
        ctx.arc(arr[i].x, arr[i].y, arr[i].r, 0, 2 * Math.PI);
        ctx.closePath();
        ctx.fillStyle = hexColor();
        ctx.fill();
        arr[i].r += step;//⚪半径增长步长
        arr[i].s -= s;

        if (arr[i].s <= 0) {
            arr.splice(i, 1);
            i--;
        }
    }
    requestAnimationFrame(animate)
}

animate();

