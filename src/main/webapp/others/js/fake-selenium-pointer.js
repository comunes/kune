// This is only used for selenium tests (remove in production)
var newx;
var newy;
var k_cur_x;
var k_cur_y;

function kmove(x, y) {
  // We adjust a litte bit to put the pointer close to the ui object but not
  // very close
  newx = x + 2;
  newy = y + 10;
  kmoveit();
}

function kmoveit() {
  for (inc = 0; inc <= 1 && !(k_cur_x == newx && k_cur_y == newy); inc = inc + 0.01) {
    k_cur_x = Math.round(k_cur_x * (1 - inc) + newx * inc);
    k_cur_y = Math.round(k_cur_y * (1 - inc) + newy * inc);
    window.setTimeout('ksetCursorPos(' + k_cur_x + ", " + k_cur_y + ')',
        2000 * inc);
  }
}

function ksetCursor(x, y) {
  k_cur_x = x;
  k_cur_y = y;
}

function ksetCursorPos(x, y) {
  document.getElementById('kcursor').style.left = x + 'px';
  document.getElementById('kcursor').style.top = y + 'px';
}

function khideCursor() {
  document.getElementById('kcursor').style.visibility = 'hidden';
}

function kshowCursor() {
  document.getElementById('kcursor').style.visibility = 'visible';
}
ksetCursor(0, 0);